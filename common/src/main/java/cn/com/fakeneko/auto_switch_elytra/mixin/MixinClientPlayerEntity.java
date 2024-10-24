package cn.com.fakeneko.auto_switch_elytra.mixin;

import cn.com.fakeneko.auto_switch_elytra.config.ModConfig;
import com.google.common.collect.ImmutableList;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.NonNullList;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.world.item.ItemStack;
import java.util.ArrayList;
import java.util.List;
/**
 * @author fakeneko
 * @date 2024/10/24上午12:04
 * @description
 */
@Mixin(LocalPlayer.class)
public class MixinClientPlayerEntity extends AbstractClientPlayer {
    @Shadow
    @Final
    protected Minecraft minecraft;

    @Unique
    private static final int CHESTPLATE_INDEX = EquipmentSlot.CHEST.getIndex(Inventory.INVENTORY_SIZE);

    @Unique
    private static int LAST_INDEX = -1;
    private boolean prevFallFlying = false;

    public MixinClientPlayerEntity(ClientLevel pClientLevel, GameProfile pGameProfile) {
        super(pClientLevel, pGameProfile);
    }

    @Inject(method = "aiStep", at = @At(
            value = "INVOKE",
            shift = At.Shift.BEFORE,
            target = "Lnet/minecraft/client/player/LocalPlayer;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;"))
    private void onPlayerDoubleJump(CallbackInfo ci) {

        LocalPlayer player = (LocalPlayer) (Object) this;

        // 配置开关
        if (!ModConfig.enabled_auto_switch_elytra.get()) {
            return;
        }

        ItemStack chestItemStack = player.getItemBySlot(EquipmentSlot.CHEST);
        if (chestItemStack.is(Items.ELYTRA) || !canStartFly(player)) {
            return;
        }

        // 起飞前，自动切换鞘翅
        List<ItemStack> inventory = getCombinedInventory(player);
        LAST_INDEX = getElytraIndex(inventory);
        equipElytra(player, CHESTPLATE_INDEX, LAST_INDEX);
    }

    @Inject(method = "aiStep", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/player/LocalPlayer;isFallFlying()Z", ordinal = 0))
    private void myFallFlyingJudge(CallbackInfo ci) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        if (!ModConfig.enabled_auto_switch_elytra.get()) {
            return;
        }
        ItemStack chestItemStack = player.getItemBySlot(EquipmentSlot.CHEST);
        if (!chestItemStack.is(Items.ELYTRA) || !prevFallFlying || player.isFallFlying()) {
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

    // 获取第一个鞘翅的位置，但是无法交换到副手的鞘翅
    private int getElytraIndex(List<ItemStack> inventory) {
        for (int slot = 0; slot < inventory.size(); slot++) {
            ItemStack stack = inventory.get(slot);

            if (stack.getItem() instanceof ElytraItem) {
                return slot;
            }
        }
        return -1;
    }

    // 切换鞘翅和胸甲，但是副手的鞘翅无法交换到胸甲
    private void equipElytra(LocalPlayer player, int slotNum1, int slotNum2) {
        NonNullList<Slot> slots = player.inventoryMenu.slots;
        int slotAMenu = -1;
        int slotBMenu = -1;
        for (int i = 5; i < slots.size(); i++) {  // Start at 5 to skip crafting grid
            if (slots.get(i).getContainerSlot() == slotNum1) slotAMenu = i;
            if (slots.get(i).getContainerSlot() == slotNum2) slotBMenu = i;
            if (slotAMenu > -1 && slotBMenu > -1) break;
        }
        assert slotAMenu > -1;
        assert slotBMenu > -1;

        if (this.minecraft.gameMode == null) {
            return;
        }
        this.minecraft.gameMode.handleInventoryMouseClick(player.inventoryMenu.containerId, slotAMenu, 0, ClickType.PICKUP, player);
        this.minecraft.gameMode.handleInventoryMouseClick(player.inventoryMenu.containerId, slotBMenu, 0, ClickType.PICKUP, player);
        this.minecraft.gameMode.handleInventoryMouseClick(player.inventoryMenu.containerId, slotAMenu, 0, ClickType.PICKUP, player);
    }

    private List<ItemStack> getCombinedInventory(LocalPlayer player) {
        Inventory inventory = player.getInventory();
        List<ItemStack> result = new ArrayList<>();
        for (NonNullList<ItemStack> compartment : ImmutableList.of(inventory.items, inventory.armor, inventory.offhand)) {
            result.addAll(compartment);
        }

        return result;
    }

    // 判断是否可以起飞
    private boolean canStartFly(LocalPlayer player) {
        return !player.onGround() && !player.isFallFlying() && !player.isInWater() && !player.hasEffect(MobEffects.LEVITATION);
    }
}
