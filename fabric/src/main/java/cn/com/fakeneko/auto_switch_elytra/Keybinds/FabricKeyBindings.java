package cn.com.fakeneko.auto_switch_elytra.Keybinds;

import cn.com.fakeneko.auto_switch_elytra.config.ScreenBuilder;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
/**
 * @author fakeneko
 * @date 2024/10/24下午9:22
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
                client.setScreen(ScreenBuilder.modScreen.makeScreen(client.screen));
            }
        });
    }
}
