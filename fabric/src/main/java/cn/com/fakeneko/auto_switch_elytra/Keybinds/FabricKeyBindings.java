package cn.com.fakeneko.auto_switch_elytra.Keybinds;

import cn.com.fakeneko.auto_switch_elytra.fabric.AutoSwitchElytraFabric;
import cn.com.fakeneko.auto_switch_elytra.commonConfig.ScreenBuilder;
import cn.com.fakeneko.auto_switch_elytra.commonConfig.ScreenBuilderYacl;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

/**
 * @author fakeneko
 * @date 2024/11/10下午3:15
 * @description
 */
public class FabricKeyBindings implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyMapping binding1 = KeyBindingHelper.registerKeyBinding(
                new KeyMapping("key.category.auto_switch_elytra.configuration",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_RIGHT_ALT,
                        "key.category.auto_switch_elytra"));

        // 注册按键绑定事件
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (binding1.consumeClick()) {
                if (AutoSwitchElytraFabric.istalledClothConfig()) {
                    client.setScreen(ScreenBuilder.modScreen.makeScreen(client.screen));
                }
                if (AutoSwitchElytraFabric.istalledYacl()) {
                    client.setScreen(ScreenBuilderYacl.modScreen.makeScreen(client.screen));
                }
            }
        });
    }
}
