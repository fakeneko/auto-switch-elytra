package cn.com.fakeneko.mixin;

import cn.com.fakeneko.common.util.InventoryUtils;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ClientPlayerEntity.class)
public abstract class MixinClientPlayerEntity extends AbstractClientPlayerEntity
{
    @Unique private ItemStack autoSwitchElytraChestplate = ItemStack.EMPTY;
    @Shadow private boolean falling;

    public MixinClientPlayerEntity(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "tickMovement",
            at = @At(value = "INVOKE", shift = At.Shift.BEFORE,
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;getEquippedStack(Lnet/minecraft/entity/EquipmentSlot;)Lnet/minecraft/item/ItemStack;"))
    private void onFallFlyingCheckChestSlot(CallbackInfo ci)
    {
        if (true)
        {
            // PlayerEntity#checkFallFlying
            if (!this.isOnGround() && !this.isFallFlying() && !this.isInFluid() && !this.hasStatusEffect(StatusEffects.LEVITATION))
            {
                if (!this.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.ELYTRA) ||
                        this.getEquippedStack(EquipmentSlot.CHEST).getDamage() > this.getEquippedStack(EquipmentSlot.CHEST).getMaxDamage() - 10)
                {
                    InventoryUtils.equipBestElytra(this);
                }
            }
        }
        else
        {
            // reset auto switch item if the feature is disabled.
            this.autoSwitchElytraChestplate = ItemStack.EMPTY;
        }
    }

    @Inject(method = "onTrackedDataSet", at = @At("RETURN"))
    private void onStopFlying(TrackedData<?> data, CallbackInfo ci)
    {
        if (true)
        {
            if (FLAGS.equals(data) && this.falling)
            {
                if (!this.isFallFlying() && this.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.ELYTRA))
                {
                    if (!this.autoSwitchElytraChestplate.isEmpty() && !this.autoSwitchElytraChestplate.isOf(Items.ELYTRA))
                    {
                        if (this.playerScreenHandler.getCursorStack().isEmpty())
                        {
                            int targetSlot = InventoryUtils.findSlotWithItem(this.playerScreenHandler, this.autoSwitchElytraChestplate, true, false);

                            if (targetSlot >= 0)
                            {
                                InventoryUtils.swapItemToEquipmentSlot(this, EquipmentSlot.CHEST, targetSlot);
                                this.autoSwitchElytraChestplate = ItemStack.EMPTY;
                            }
                        }
                    }
                    else
                    {
                        // if cached previous item is empty, try to swap back to the default chest plate.
                        InventoryUtils.swapElytraAndChestPlate(this);
                    }
                }
            }
        }
    }
}
