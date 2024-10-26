package cn.com.fakeneko.auto_switch_elytra.Keybinds;

import cn.com.fakeneko.auto_switch_elytra.Constants;
import cn.com.fakeneko.auto_switch_elytra.ForgeAutoSwitchElytra;
import cn.com.fakeneko.auto_switch_elytra.config.ScreenBuilder;
import cn.com.fakeneko.auto_switch_elytra.config.ScreenBuilderYacl;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

/**
 * @author fakeneko
 * @date 2024/10/26下午5:47
 * @description
 */
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeKeyBindings {
    public static Lazy<KeyMapping> binding1 = null;

    // 注册快捷键
    public static void register(final RegisterKeyMappingsEvent event) {
        binding1  = Lazy.of(() -> new KeyMapping(
                "key.category.auto_switch_elytra.configuration",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_ALT,
                "key.category.auto_switch_elytra"
        ));

        event.register(binding1.get());
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent e) {
        Minecraft client = Minecraft.getInstance();
        if (ForgeKeyBindings.binding1.get().consumeClick()) {
            // 根据存在的模组，加载不同的配置页面
            if (ForgeAutoSwitchElytra.istalledClothConfig()) {
                client.setScreen(ScreenBuilder.modScreen.makeScreen(client.screen));
                return;
            }
            if (ForgeAutoSwitchElytra.istalledYacl()) {
                client.setScreen(ScreenBuilderYacl.modScreen.makeScreen(client.screen));
            }
        }
    }
}
