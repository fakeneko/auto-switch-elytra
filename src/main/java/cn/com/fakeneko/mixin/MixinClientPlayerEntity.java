package cn.com.fakeneko.mixin;

import cn.com.fakeneko.AutoSwitchElytra;
import com.mojang.authlib.GameProfile;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ClientPlayerEntity.class)
public abstract class MixinClientPlayerEntity extends AbstractClientPlayerEntity
{
    @Unique private static final int CHESTPLATE_INDEX = 6;
    @Unique private static int lastIndex = -1;

    public MixinClientPlayerEntity(ClientWorld world, GameProfile profile, @Nullable PlayerPublicKey publicKey) {
        super(world, profile, publicKey);
    }

    @Inject(method = "tickMovement", at = @At(
            value = "INVOKE",
            shift = At.Shift.BEFORE,
            target = "Lnet/minecraft/client/network/ClientPlayerEntity;getEquippedStack(Lnet/minecraft/entity/EquipmentSlot;)Lnet/minecraft/item/ItemStack;"))
    private void onPlayerTickMovement(CallbackInfo ci) {
        if (AutoSwitchElytra.autoSwitchElytraConfig.get_enabled_auto_switch_elytra() && notHaveTweakerooMod()) {
            if (!this.isOnGround() && !this.isFallFlying() && !this.hasStatusEffect(StatusEffects.LEVITATION)) {
                // 起飞前，自动切换鞘翅
                lastIndex = this.getElytraIndex(this);
                equipElytra(this, CHESTPLATE_INDEX, lastIndex);
            }
        }
    }

    @Inject(method = "tickMovement", at = @At(value = "TAIL"))
    private void endTickMovement(CallbackInfo ci) {
        if (AutoSwitchElytra.autoSwitchElytraConfig.get_enabled_auto_switch_elytra() && notHaveTweakerooMod()) {
            if (this.isOnGround() || this.isTouchingWater()) {
                this.checkFallFlying();
                if (lastIndex != -1) {
                    // 落地后，切回胸甲
                    equipElytra(this, CHESTPLATE_INDEX, lastIndex);
                    lastIndex = -1;
                }
            }
        }
    }

    @Unique
    private static boolean notHaveTweakerooMod() {
        return !FabricLoader.getInstance().isModLoaded("tweakeroo");
    }

    // 获取第一个鞘翅的位置，但是无法交换到副手的鞘翅
    @Unique
    private int getElytraIndex(PlayerEntity player) {
        DefaultedList<ItemStack> inv = player.getInventory().main;
        DefaultedList<ItemStack> offhand = player.getInventory().offHand;
        for (int i = 0; i < offhand.size(); i++) {
            if (offhand.get(i).getItem() == Items.ELYTRA) {
                Slot slot = player.currentScreenHandler.getSlot(i);
                return slot.id;
            }
        }
        for (int i = 0; i < inv.size(); i++) {
            if (inv.get(i).getItem() == Items.ELYTRA) {
                Slot slot = player.currentScreenHandler.getSlot(i);
                return slot.id;
            }
        }
        return -1;
    }

    // 切换鞘翅和胸甲，但是副手的鞘翅无法交换到胸甲
    @Unique
    private void equipElytra(PlayerEntity player, int slotNum1, int slotNum2) {
        MinecraftClient mc = MinecraftClient.getInstance();
        ScreenHandler container = player.currentScreenHandler;
        if (slotNum1 != -1 && slotNum2 != -1) {
            if (mc.interactionManager != null) {
                mc.interactionManager.clickSlot(container.syncId, slotNum1, slotNum2, SlotActionType.SWAP, player);
            }
        }
    }
}
