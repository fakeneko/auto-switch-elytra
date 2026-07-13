package cn.com.fakeneko.client.Keybinds;

import cn.com.fakeneko.Constants;
import cn.com.fakeneko.commonConfig.ModConfig;
import cn.com.fakeneko.config.impl.gui.ConfigScreen;
import cn.com.fakeneko.config.impl.keybind.KeybindListener;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class FabricKeyBindings implements ClientModInitializer {
    private static final KeyMapping.Category ASE_CATEGORY = KeyMapping.Category.register(Identifier.parse(Constants.MOD_ID + ":category"));

    @Override
    public void onInitializeClient() {
        // 主激活按键：按下此键打开配置界面（默认 ALT）
        KeyMapping primaryKey = KeyMappingHelper.registerKeyMapping(
                new KeyMapping("key.auto_switch_elytra.open_config",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_RIGHT_ALT,
                        ASE_CATEGORY));

        // 修饰键：默认未绑定；绑定后需按住主激活键再按它
        KeyMapping modifierKey = KeyMappingHelper.registerKeyMapping(
                new KeyMapping("key.auto_switch_elytra.modifier",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_UNKNOWN,
                        ASE_CATEGORY));

        // 切换开关时显示 HUD 提示
        ModConfig.ENABLED_AUTO_SWITCH_ELYTRA.hotkey().keybind().addListener(
                KeybindListener.onPress(() -> ModConfig.showToggleMessage(Minecraft.getInstance(), ModConfig.ENABLED_AUTO_SWITCH_ELYTRA.get()))
        );

        ClientTickEvents.END_CLIENT_TICK.register(new ChordHandler(primaryKey, modifierKey));
    }

    private static class ChordHandler implements ClientTickEvents.EndTick {
        private final KeyMapping primaryKey;
        private final KeyMapping modifierKey;
        private boolean prevModDown = false;

        ChordHandler(KeyMapping primaryKey, KeyMapping modifierKey) {
            this.primaryKey = primaryKey;
            this.modifierKey = modifierKey;
        }

        @Override
        public void onEndTick(Minecraft client) {
            boolean modDown = modifierKey.isDown();
            boolean modEdge = !prevModDown && modDown;
            prevModDown = modDown;

            boolean shouldOpen;
            if (modifierKey.isUnbound()) {
                // 修饰键未配置 → 单按主激活键即可
                shouldOpen = primaryKey.consumeClick();
            } else {
                // 修饰键已配置 → 按住主激活键 + 按修饰键触发
                shouldOpen = primaryKey.isDown() && modEdge;
            }

            if (!shouldOpen) return;

            client.gui.setScreen(new ConfigScreen(client.gui.screen(), ModConfig.MANAGER));
        }
    }
}
