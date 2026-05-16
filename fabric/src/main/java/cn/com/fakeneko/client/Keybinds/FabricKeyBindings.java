package cn.com.fakeneko.client.Keybinds;

import cn.com.fakeneko.CommonClass;
import cn.com.fakeneko.commonConfig.ScreenBuilder;
import cn.com.fakeneko.commonConfig.ScreenBuilderYacl;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

import static net.minecraft.client.KeyMapping.Category.MISC;

public class FabricKeyBindings implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyMapping binding1 = KeyMappingHelper.registerKeyMapping(
                new KeyMapping("key.category.auto_switch_elytra.configuration",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_RIGHT_ALT,
                        MISC));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (binding1.consumeClick()) {
                if (CommonClass.isClothConfigLoaded()) {
                    client.setScreen(ScreenBuilder.modScreen.makeScreen(client.screen));
                    return;
                }
                if (CommonClass.isYaclLoaded()) {
                    client.setScreen(ScreenBuilderYacl.modScreen.makeScreen(client.screen));
                }
            }
        });
    }
}
