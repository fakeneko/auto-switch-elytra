package cn.com.fakeneko.auto_switch_elytra.commonConfig;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import static cn.com.fakeneko.auto_switch_elytra.commonConfig.ModConfig.modConfig;

/**
 * @author fakeneko
 * @date 2024/11/10下午2:55
 * @description
 */
public class ScreenBuilder {
    public static final ScreenBuilder modScreen = new ScreenBuilder();

    public Screen makeScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.translatable("config.auto-switch-elytra.title"))
                .setSavingRunnable(modConfig::save);

        var category_enable = builder.getOrCreateCategory(Component.translatable("config.enable.title"));
        var category_disable = builder.getOrCreateCategory(Component.translatable("config.disable.title"));
        var entryBuilder = builder.entryBuilder();

        category_enable.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.auto-switch-elytra.enabled"), ModConfig.enabled_auto_switch_elytra.get())
                .setDefaultValue(ModConfig.enabled_auto_switch_elytra.getDefault())
                .setSaveConsumer(ModConfig.enabled_auto_switch_elytra::set)
                .build()
        );

        category_disable.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.armor-stand-interactive.disable"), ModConfig.disable_armor_stand_interactive.get())
                .setDefaultValue(ModConfig.disable_armor_stand_interactive.getDefault())
                .setSaveConsumer(ModConfig.disable_armor_stand_interactive::set)
                .build()
        );
        return builder.build();
    }
}
