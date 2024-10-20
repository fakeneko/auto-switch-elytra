package cn.com.fakeneko.clothconfig;

/**
 * @author fakeneko
 * @date 2024/10/20下午7:24
 * @description
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.loading.FMLPaths;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author fakeneko
 * @date 2024/9/16下午3:25
 * @description
 */
public class ModConfigBuilder {
    public static final ModConfigBuilder INSTANCE = new ModConfigBuilder();

    // 配置文件路径
    public final Path configFile = new File(FMLPaths.CONFIGDIR.get().toFile(), "auto-switch-elytra.json").toPath();
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

    public Screen makeScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.translatable("config.auto-switch-elytra.title"))
                .setSavingRunnable(this::save);

        var category_enable = builder.getOrCreateCategory(Component.translatable("config.enable.title"));
        var category_disable = builder.getOrCreateCategory(Component.translatable("config.disable.title"));
        var entryBuilder = builder.entryBuilder();

        category_enable.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.auto-switch-elytra.enabled"), enabled_auto_switch_elytra)
                .setDefaultValue(false)
                .setSaveConsumer(value -> enabled_auto_switch_elytra = value)
                .build()
        );

        category_disable.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.armor-stand-interactive.disable"), disable_armor_stand_interactive)
                .setDefaultValue(false)
                .setSaveConsumer(value -> disable_armor_stand_interactive = value)
                .build()
        );
        return builder.build();
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
