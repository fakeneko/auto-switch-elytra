package cn.com.fakeneko.Keybinds;

import cn.com.fakeneko.CommonClass;
import cn.com.fakeneko.Constants;
import cn.com.fakeneko.commonConfig.ScreenBuilder;
import cn.com.fakeneko.commonConfig.ScreenBuilderYacl;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class NeoForgeKeyBindings {
    // 主激活按键：按下此键打开配置界面（默认 ALT）
    private static KeyMapping primaryKey;
    // 修饰键：默认未绑定；绑定后需按住主激活键再按它
    private static KeyMapping modifierKey;
    private static boolean prevModDown = false;

    public static void register(final RegisterKeyMappingsEvent event) {
        KeyMapping.Category category = createCategory();
        if (category == null) {
            category = KeyMapping.Category.MISC;
        }

        primaryKey = new KeyMapping(
                "key.auto_switch_elytra.open_config",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_ALT,
                category
        );
        modifierKey = new KeyMapping(
                "key.auto_switch_elytra.modifier",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                category
        );

        // 若创建的是自定义分类，尝试通过反射调用 registerCategory（兼容不同 NeoForge 版本）
        if (category != KeyMapping.Category.MISC) {
            try {
                java.lang.reflect.Method registerCategory = RegisterKeyMappingsEvent.class.getMethod("registerCategory", KeyMapping.Category.class);
                registerCategory.invoke(event, category);
            } catch (Exception ignored) {
                // 1.21.9 可能无此 API，忽略即可
            }
        }

        event.register(primaryKey);
        event.register(modifierKey);
    }

    private static KeyMapping.Category createCategory() {
        try {
            java.lang.reflect.Constructor<?> ctor = null;
            for (java.lang.reflect.Constructor<?> c : KeyMapping.Category.class.getDeclaredConstructors()) {
                if (c.getParameterCount() == 1) {
                    ctor = c;
                    break;
                }
            }
            if (ctor == null) return null;

            Class<?> paramType = ctor.getParameterTypes()[0];
            Object arg;
            if (paramType == String.class) {
                arg = Constants.MOD_ID + ":category";
            } else {
                java.lang.reflect.Method parse = paramType.getMethod("parse", String.class);
                arg = parse.invoke(null, Constants.MOD_ID + ":category");
            }

            return (KeyMapping.Category) ctor.newInstance(arg);
        } catch (Exception e) {
            return null;
        }
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
            client.setScreen(ScreenBuilder.modScreen.makeScreen(client.screen));
        } else if (CommonClass.isYaclLoaded()) {
            client.setScreen(ScreenBuilderYacl.modScreen.makeScreen(client.screen));
        }
    }
}
