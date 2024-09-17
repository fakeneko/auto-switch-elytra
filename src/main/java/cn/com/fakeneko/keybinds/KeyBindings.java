package cn.com.fakeneko.keybinds;

import cn.com.fakeneko.clothconfig.ModConfigBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

/**
 * @author fakeneko
 * @date 2024/9/16上午11:11
 * @description 按键绑定
 */
public class KeyBindings implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // 定义按键绑定事件，先给个默认值右ALT
        KeyBinding binding1 = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.category.auto_switch_elytra.configuration",
                        InputUtil.Type.KEYSYM,
                        GLFW.GLFW_KEY_RIGHT_ALT,
                        "key.category.auto_switch_elytra"));

        // 注册按键绑定事件
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (binding1.wasPressed()) {
                client.setScreen(ModConfigBuilder.INSTANCE.makeScreen(client.currentScreen));
            }
        });
    }
}
