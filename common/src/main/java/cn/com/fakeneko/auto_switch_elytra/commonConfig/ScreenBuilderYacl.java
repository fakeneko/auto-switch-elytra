package cn.com.fakeneko.auto_switch_elytra.commonConfig;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/**
 * @author fakeneko
 * @date 2024/11/10下午2:56
 * @description
 */
// yacl 配置界面构建器
public class ScreenBuilderYacl {
    public static final ScreenBuilderYacl modScreen = new ScreenBuilderYacl();

    public Screen makeScreen(Screen parent) {
        // 重构配置界面，从最底层开始定义组件

        // 定义最基本option
        // 组件类型参考https://docs.isxander.dev/yet-another-config-lib/gui-builder/controllers
        var Option_ToggleAutoSwitchElytra = Option.<Boolean>createBuilder()
                .name(Component.translatable("config.auto-switch-elytra.enabled"))
                .description(OptionDescription.of(Component.translatable("config.auto-switch-elytra.enabled.description")))
                .binding(
                        ModConfig.enabled_auto_switch_elytra.getDefault(),
                        () -> ModConfig.enabled_auto_switch_elytra.get(),
                        value -> ModConfig.enabled_auto_switch_elytra.set(value)
                )
                .controller(BooleanControllerBuilder::create)
                .build();

        var Option_ToggleDisableArmorStandInteractive = Option.<Boolean>createBuilder()
                .name(Component.translatable("config.armor-stand-interactive.disable"))
                .description(OptionDescription.of(Component.translatable("config.armor-stand-interactive.disable.description")))
                .binding(
                        ModConfig.disable_armor_stand_interactive.getDefault(),
                        () -> ModConfig.disable_armor_stand_interactive.get(),
                        value -> ModConfig.disable_armor_stand_interactive.set(value)
                )
                .controller(BooleanControllerBuilder::create)
                .build();

        // 定义category，并把option添加到category
        var Category_enable = ConfigCategory.createBuilder()
                .name(Component.translatable("config.enable.title"))
                .tooltip(Component.translatable("config.enable.title.tooltip"))
                .option(Option_ToggleAutoSwitchElytra)
                .build();

        // 定义category，并把option添加到category
        var Category_disable = ConfigCategory.createBuilder()
                .name(Component.translatable("config.disable.title"))
                .tooltip(Component.translatable("config.disable.title.tooltip"))
                .option(Option_ToggleDisableArmorStandInteractive)
                .build();

        // 构建yacl配置，并把category添加到yacl配置
        return YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("config.auto-switch-elytra.title"))
                .category(Category_enable)
                .category(Category_disable)
                .save(ModConfig.modConfig::save)
                .build()
                .generateScreen(parent);
    }
}
