package cn.com.fakeneko.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.NonNullList;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.world.item.ItemStack;

/**
 * @author fakeneko
 * @date 2024/10/13下午3:23
 * @description
 */

@Mixin(LocalPlayer.class)
public abstract class MixinClientPlayerEntity extends AbstractClientPlayer
{
    @Shadow @Final protected Minecraft minecraft;
    @Unique private static final int CHESTPLATE_INDEX = 6;
    @Unique private static int LAST_INDEX = -1;

    public MixinClientPlayerEntity(ClientLevel pClientLevel, GameProfile pGameProfile) {
        super(pClientLevel, pGameProfile);
    }

    @Inject(method = "aiStep", at = @At(
            value = "INVOKE",
            shift = At.Shift.BEFORE,
            target = "Lnet/minecraft/client/player/LocalPlayer;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;"))
    private void onPlayerTickMovement(CallbackInfo ci) {
        if (true) { // 开关
            if (!this.onGround() && !this.isFallFlying() && !this.isInWater() && !this.hasEffect(MobEffects.LEVITATION)) {
                // 起飞前，自动切换鞘翅
                LAST_INDEX = this.getElytraIndex(this);
                equipElytra(CHESTPLATE_INDEX, LAST_INDEX);
            }
        }
    }

    @Inject(method = "aiStep", at = @At(value = "TAIL"))
    private void endTickMovement(CallbackInfo ci) {
        if (true) { // 开关
            if (this.onGround() || this.isFallFlying() || this.isInWater() || this.hasEffect(MobEffects.LEVITATION)) {
                if (LAST_INDEX != -1) {
                    // 落地后，切回胸甲
                    equipElytra(CHESTPLATE_INDEX, LAST_INDEX);
                    LAST_INDEX = -1;
                }
            }
        }
    }

    // 获取第一个鞘翅的位置，但是无法交换到副手的鞘翅
    @Unique
    private int getElytraIndex(MixinClientPlayerEntity player) {
        NonNullList<ItemStack> inv = player.getInventory().items;
        NonNullList<ItemStack> offhand = player.getInventory().offhand;
        for (int i = 0; i < offhand.size(); i++) {
            if (offhand.get(i).getItem() == Items.ELYTRA) {
                Slot slot = player.getItemBySlot.getSlot(i);
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
    private void equipElytra(int slotNum1, int slotNum2) {
        NonNullList<Slot> slots = this.inventoryMenu.slots;
        int slotAMenu = -1;
        int slotBMenu = -1;
        for (int i = 5; i < slots.size(); i++) {  // Start at 5 to skip crafting grid
            if (slots.get(i).getContainerSlot() == slotNum1) slotAMenu = i;
            if (slots.get(i).getContainerSlot() == slotNum2) slotBMenu = i;
            if (slotAMenu > -1 && slotBMenu > -1) break;
        }
        assert slotAMenu > -1;
        assert slotBMenu > -1;
        assert this.minecraft.gameMode != null;
        this.minecraft.gameMode.handleInventoryMouseClick(this.inventoryMenu.containerId, slotNum1, 0, ClickType.PICKUP, this);
        this.minecraft.gameMode.handleInventoryMouseClick(this.inventoryMenu.containerId, slotNum2, 0, ClickType.PICKUP, this);
        this.minecraft.gameMode.handleInventoryMouseClick(this.inventoryMenu.containerId, slotNum1, 0, ClickType.PICKUP, this);
    }
}