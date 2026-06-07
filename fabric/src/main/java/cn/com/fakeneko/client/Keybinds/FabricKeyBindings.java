package cn.com.fakeneko.client.Keybinds;

import cn.com.fakeneko.CommonClass;
import cn.com.fakeneko.Constants;
import cn.com.fakeneko.commonConfig.ScreenBuilder;
import cn.com.fakeneko.commonConfig.ScreenBuilderYacl;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.glfw.GLFW;

public class FabricKeyBindings implements ClientModInitializer {
    private static final KeyMapping.Category ASE_CATEGORY = KeyMapping.Category.register(ResourceLocation.parse(Constants.MOD_ID + ":category"));

    @Override
    public void onInitializeClient() {
        // 主激活按键：按下此键打开配置界面（默认 ALT）
        KeyMapping primaryKey = KeyBindingHelper.registerKeyBinding(
                new KeyMapping("key.auto_switch_elytra.open_config",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_RIGHT_ALT,
                        ASE_CATEGORY));

        // 修饰键：默认未绑定；绑定后需按住主激活键再按它
        KeyMapping modifierKey = KeyBindingHelper.registerKeyBinding(
                new KeyMapping("key.auto_switch_elytra.modifier",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_UNKNOWN,
                        ASE_CATEGORY));

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

            if (CommonClass.isClothConfigLoaded()) {
                client.setScreen(ScreenBuilder.modScreen.makeScreen(client.screen));
            } else if (CommonClass.isYaclLoaded()) {
                client.setScreen(ScreenBuilderYacl.modScreen.makeScreen(client.screen));
            }
        }
    }
}
