package cn.com.fakeneko.commonConfig;

import cn.com.fakeneko.config.api.ConfigCategory;
import cn.com.fakeneko.config.api.ConfigManager;
import cn.com.fakeneko.config.impl.ConfigManagerImpl;
import cn.com.fakeneko.config.impl.keybind.InputKeys;
import cn.com.fakeneko.config.impl.types.BooleanConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;

public class ModConfig {
    public static final ConfigManager MANAGER = new ConfigManagerImpl(
            "auto_switch_elytra",
            Component.translatable("config.auto-switch-elytra.title")
    );

    public static final ConfigCategory GENERAL = MANAGER.createCategory(
            "general",
            Component.translatable("config.auto-switch-elytra.title")
    );

    public static final BooleanConfig ENABLED_AUTO_SWITCH_ELYTRA
            = new BooleanConfig("enabled_auto_switch_elytra", Component.translatable("config.auto-switch-elytra.enabled"), GENERAL, false)
            .withHotkey(
                    Component.translatable("config.auto-switch-elytra.enabled_hotkey"),
                    Identifier.fromNamespaceAndPath("auto_switch_elytra", "toggle"),
                    InputKeys.EMPTY
            );

    public static final BooleanConfig DISABLE_ARMOR_STAND_INTERACTIVE
            = new BooleanConfig("disable_armor_stand_interactive", Component.translatable("config.armor-stand-interactive.disable"), GENERAL, false);

    static {
        MANAGER.load();
    }

    public static void init() {
    }

    public static void showToggleMessage(Minecraft client, boolean newValue) {
        if (client.player == null) {
            return;
        }
        String langKey = newValue
                ? "message.auto_switch_elytra.enabled"
                : "message.auto_switch_elytra.disabled";
        ChatFormatting color = newValue ? ChatFormatting.GREEN : ChatFormatting.RED;
        Component message = Component.translatable(langKey).withStyle(Style.EMPTY.withColor(color));
        client.gui.hud.setOverlayMessage(message, false);
    }
}
