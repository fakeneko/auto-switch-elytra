package cn.com.fakeneko.auto_switch_elytra.Keybinds;

import cn.com.fakeneko.auto_switch_elytra.AutoSwitchElytra;
import cn.com.fakeneko.auto_switch_elytra.neoforge.AutoSwitchElytraNeoForge;
import cn.com.fakeneko.auto_switch_elytra.commonConfig.ScreenBuilder;
import cn.com.fakeneko.auto_switch_elytra.commonConfig.ScreenBuilderYacl;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

/**
 * @author fakeneko
 * @date 2024/11/10下午3:23
 * @description
 */
@EventBusSubscriber(modid = AutoSwitchElytra.MOD_ID, value = Dist.CLIENT)
public class NeoForgeKeyBindings {
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
    public static void onClientTick(ClientTickEvent.Post e) {
        Minecraft client = Minecraft.getInstance();
        if (NeoForgeKeyBindings.binding1.get().consumeClick()) {
            // 根据存在的模组，加载不同的配置页面
            if (AutoSwitchElytraNeoForge.istalledClothConfig()) {
                client.setScreen(ScreenBuilder.modScreen.makeScreen(client.screen));
                return;
            }
            if (AutoSwitchElytraNeoForge.istalledYacl()) {
                client.setScreen(ScreenBuilderYacl.modScreen.makeScreen(client.screen));
            }
        }
    }
}
