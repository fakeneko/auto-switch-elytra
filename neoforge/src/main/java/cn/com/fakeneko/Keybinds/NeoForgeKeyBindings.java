package cn.com.fakeneko.Keybinds;

import cn.com.fakeneko.CommonClass;
import cn.com.fakeneko.Constants;
import cn.com.fakeneko.commonConfig.ScreenBuilder;
import cn.com.fakeneko.commonConfig.ScreenBuilderYacl;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class NeoForgeKeyBindings {
    private static final KeyMapping.Category ASE_CATEGORY = KeyMapping.Category.register(Identifier.parse(Constants.MOD_ID + ":category"));
    // 主激活按键：按下此键打开配置界面（默认 ALT）
    private static KeyMapping primaryKey;
    // 修饰键：默认未绑定；绑定后需按住主激活键再按它
    private static KeyMapping modifierKey;
    private static boolean prevModDown = false;

    public static void register(final RegisterKeyMappingsEvent event) {
        primaryKey = new KeyMapping(
                "key.auto_switch_elytra.open_config",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_ALT,
                ASE_CATEGORY
        );
        modifierKey = new KeyMapping(
                "key.auto_switch_elytra.modifier",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                ASE_CATEGORY
        );
        event.register(primaryKey);
        event.register(modifierKey);
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post e) {
        boolean modDown = modifierKey.isDown();
        boolean modEdge = !prevModDown && modDown;
        prevModDown = modDown;

        boolean shouldOpen;
        if (modifierKey.isUnbound()) {
            shouldOpen = primaryKey.consumeClick();
        } else {
            shouldOpen = primaryKey.isDown() && modEdge;
        }

        if (!shouldOpen) return;

        Minecraft client = Minecraft.getInstance();
        if (CommonClass.isClothConfigLoaded()) {
            client.gui.setScreen(ScreenBuilder.modScreen.makeScreen(client.gui.screen()));
        } else if (CommonClass.isYaclLoaded()) {
            client.gui.setScreen(ScreenBuilderYacl.modScreen.makeScreen(client.gui.screen()));
        }
    }
}
