package cn.com.fakeneko.auto_switch_elytra.mixin;

import cn.com.fakeneko.auto_switch_elytra.commonConfig.ModConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author fakeneko
 * @date 2024/11/10下午2:57
 * @description
 */
@Mixin(ArmorStand.class)
public abstract class MixinArmorStandEntity{

    @Inject(method = "interactAt", at = @At(value = "HEAD"), cancellable = true)
    private void disableArmorStandInteract(Player player, Vec3 hitPos, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (ModConfig.disable_armor_stand_interactive.get()) {
            cir.setReturnValue(InteractionResult.PASS);
        }
    }
}
