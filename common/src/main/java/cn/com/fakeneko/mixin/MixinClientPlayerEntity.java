package cn.com.fakeneko.mixin;

import cn.com.fakeneko.commonConfig.ModConfig;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import java.util.List;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public abstract class MixinClientPlayerEntity extends AbstractClientPlayer {
    @Shadow
    @Final
    protected Minecraft minecraft;

    @Unique
    private static final int CHESTPLATE_INDEX = 38;

    @Unique
    private static int LAST_INDEX = -1;

    @Unique
    private boolean prevFallFlying = false;

    public MixinClientPlayerEntity(ClientLevel pClientLevel, GameProfile pGameProfile) {
        super(pClientLevel, pGameProfile);
    }

    @Inject(method = "aiStep", at = @At(
            value = "INVOKE",
            shift = At.Shift.BY,
            target = "Lnet/minecraft/client/player/LocalPlayer;tryToStartFallFlying()Z"))
    private void onPlayerDoubleJump(CallbackInfo ci) {
        LocalPlayer player = (LocalPlayer) (Object) this;

        if (!ModConfig.enabled_auto_switch_elytra.get()) {
            return;
        }

        ItemStack chestItemStack = player.getItemBySlot(EquipmentSlot.CHEST);
        if (chestItemStack.getItem() == Items.ELYTRA || !canStartFly(player)) {
            return;
        }

        LAST_INDEX = getElytraIndex(player);
        equipElytra(player, CHESTPLATE_INDEX, LAST_INDEX);
    }

    @Inject(method = "aiStep", at = @At(
            value = "INVOKE_ASSIGN",
            target = "Lnet/minecraft/client/player/LocalPlayer;isFallFlying()Z",
            ordinal = 0))
    private void myFallFlyingJudge(CallbackInfo ci) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        if (!ModConfig.enabled_auto_switch_elytra.get()) {
            return;
        }
        ItemStack chestItemStack = player.getItemBySlot(EquipmentSlot.CHEST);
        if (chestItemStack.getItem() != Items.ELYTRA || !prevFallFlying || player.isFallFlying()) {
            prevFallFlying = player.isFallFlying();
            return;
        }

        if (LAST_INDEX == -1) {
            return;
        }
        prevFallFlying = player.isFallFlying();
        equipElytra(player, CHESTPLATE_INDEX, LAST_INDEX);
        LAST_INDEX = -1;
    }

    @Unique
    private int getElytraIndex(LocalPlayer player) {
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.getItem() == Items.ELYTRA) {
                return slot;
            }
        }
        return -1;
    }

    @Unique
    private void equipElytra(LocalPlayer player, int slotNum1, int slotNum2) {
        if (slotNum2 < 0) {
            return;
        }
        List<Slot> slots = player.inventoryMenu.slots;
        int slotAMenu = -1;
        int slotBMenu = -1;
        for (int i = 5; i < slots.size(); i++) {
            if (slots.get(i).getContainerSlot() == slotNum1) slotAMenu = i;
            if (slots.get(i).getContainerSlot() == slotNum2) slotBMenu = i;
            if (slotAMenu > -1 && slotBMenu > -1) break;
        }
        if (slotAMenu < 0 || slotBMenu < 0) {
            return;
        }

        if (this.minecraft.gameMode == null) {
            return;
        }
        int containerId = player.inventoryMenu.containerId;
        this.minecraft.gameMode.handleInventoryMouseClick(containerId, slotAMenu, 0, ClickType.PICKUP, player);
        this.minecraft.gameMode.handleInventoryMouseClick(containerId, slotBMenu, 0, ClickType.PICKUP, player);
        this.minecraft.gameMode.handleInventoryMouseClick(containerId, slotAMenu, 0, ClickType.PICKUP, player);
    }

    @Unique
    private boolean canStartFly(LocalPlayer player) {
        return !player.onGround() &&
                !player.isFallFlying() &&
                !player.isInLiquid() &&
                !player.hasEffect(MobEffects.LEVITATION) &&
                !player.isPassenger() &&
                !player.isSleeping();
    }
}
