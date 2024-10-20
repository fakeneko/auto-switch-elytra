package cn.com.fakeneko.mixin;

import cn.com.fakeneko.clothconfig.ModConfigBuilder;
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
 * @date 2024/10/13
 * @description 禁用盔甲架右键交互
 */
@Mixin(ArmorStand.class)
public abstract class MixinArmorStandEntity{

    @Inject(method = "interactAt", at = @At(value = "HEAD"), cancellable = true)
    private void disableArmorStandInteract(Player player, Vec3 hitPos, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (ModConfigBuilder.INSTANCE.get_disable_armor_stand_interactive()) {
            cir.setReturnValue(InteractionResult.PASS);
        }
    }
}