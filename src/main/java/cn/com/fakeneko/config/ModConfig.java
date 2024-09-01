package cn.com.fakeneko.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import com.google.gson.GsonBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;

/**
 * @author fakeneko
 * @date 2024/8/24下午12:26
 * @description
 */

public class ModConfig {
    public static final ModConfig INSTANCE = new ModConfig();
    // 配置文件路径
    public final Path configFile = FabricLoader.getInstance().getConfigDir().resolve("auto-switch-elytra.json");
    // gson
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // 配置项，自动切换鞘翅
    public boolean enabled_auto_switch_elytra = false;
    public boolean disable_armor_stand_interactive = false;

    // 保存配置
    public void save() {
        try {
            Files.deleteIfExists(configFile);
            JsonObject json = new JsonObject();
            json.addProperty("enabled_auto_switch_elytra", enabled_auto_switch_elytra);
            json.addProperty("disable_armor_stand_interactive", disable_armor_stand_interactive);
            Files.writeString(configFile, gson.toJson(json));
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    // 加载配置
    public void load() {
        try {
            // 检查配置文件是否存在
            if (Files.notExists(configFile)) {
                save();
                return;
            }
            // 读取配置文件
            JsonObject json = gson.fromJson(Files.readString(configFile), JsonObject.class);
            // 加载配置项
            if (json.has("enabled_auto_switch_elytra")) {
                enabled_auto_switch_elytra = json.getAsJsonPrimitive("enabled_auto_switch_elytra").getAsBoolean();
            }
            if (json.has("disable_armor_stand_interactive")) {
                disable_armor_stand_interactive = json.getAsJsonPrimitive("disable_armor_stand_interactive").getAsBoolean();
            }
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }


    // 配置界面，理论上要单独写一个类。但是懒。
    public Screen makeScreen(Screen parent) {
        // 重构配置界面，从最底层开始定义组件

        // 定义最基本option
        // 组件类型参考https://docs.isxander.dev/yet-another-config-lib/gui-builder/controllers
        var Option_ToggleAutoSwitchElytra = Option.<Boolean>createBuilder()
                .name(Text.translatable("config.auto-switch-elytra.enabled"))
                .description(OptionDescription.of(Text.translatable("config.auto-switch-elytra.enabled.description")))
                .binding(
                        false,
                        () -> enabled_auto_switch_elytra,
                        value -> enabled_auto_switch_elytra = value
                )
                .controller(BooleanControllerBuilder::create)
                .build();

        var Option_ToggleDisableArmorStandInteractive = Option.<Boolean>createBuilder()
                .name(Text.translatable("config.armor-stand-interactive.disable"))
                .description(OptionDescription.of(Text.translatable("config.armor-stand-interactive.disable.description")))
                .binding(
                        false,
                        () -> disable_armor_stand_interactive,
                        value -> disable_armor_stand_interactive = value
                )
                .controller(BooleanControllerBuilder::create)
                .build();

        // 定义category，并把option添加到category
        var Category_enable = ConfigCategory.createBuilder()
                .name(Text.translatable("config.enable.title"))
                .tooltip(Text.translatable("config.enable.title.tooltip"))
                .option(Option_ToggleAutoSwitchElytra)
                .build();

        // 定义category，并把option添加到category
        var Category_disable = ConfigCategory.createBuilder()
                .name(Text.translatable("config.disable.title"))
                .tooltip(Text.translatable("config.disable.title.tooltip"))
                .option(Option_ToggleDisableArmorStandInteractive)
                .build();

        // 构建yacl配置，并把category添加到yacl配置
        return YetAnotherConfigLib.createBuilder()
                .title(Text.translatable("config.auto-switch-elytra.title"))
                .category(Category_enable)
                .category(Category_disable)
                .save(this::save)
                .build()
                .generateScreen(parent);
    }

    // 获取配置项
    public boolean get_enabled_auto_switch_elytra() {
        return enabled_auto_switch_elytra;
    }

    // 获取配置项
    public boolean get_disable_armor_stand_interactive() {
        return disable_armor_stand_interactive;
    }
}
