package cn.com.fakeneko.mixin;

import cn.com.fakeneko.clothconfig.ModConfigBuilder;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author fakeneko
 * @date 2024/9/1下午10:10
 * @description 禁用盔甲架右键交互
 */
@Mixin(ArmorStandEntity.class)
public abstract class MixinArmorStandEntity{

    @Inject(method = "interactAt", at = @At(value = "HEAD"), cancellable = true)
    private void disableArmorStandInteract(PlayerEntity player, Vec3d hitPos, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (ModConfigBuilder.INSTANCE.get_disable_armor_stand_interactive()) {
            cir.setReturnValue(ActionResult.PASS);
        }
    }
}
