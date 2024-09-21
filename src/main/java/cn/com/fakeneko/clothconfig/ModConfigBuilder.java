package cn.com.fakeneko.clothconfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author fakeneko
 * @date 2024/9/16下午3:25
 * @description
 */
public class ModConfigBuilder {
    // 配置文件路径
    private final Path configFile = FabricLoader.getInstance().getConfigDir().resolve("auto-switch-elytra.json");
    // gson
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // 配置项，自动切换鞘翅
    private boolean enabled_auto_switch_elytra = false;
    private boolean disable_armor_stand_interactive = false;

    // 保存配置
    private void save() {
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
                .setTitle(new TranslatableText("config.auto-switch-elytra.title"))
                .setSavingRunnable(this::save);

        var category_enable = builder.getOrCreateCategory(new TranslatableText("config.enable.title"));
        var category_disable = builder.getOrCreateCategory(new TranslatableText("config.disable.title"));
        var entryBuilder = builder.entryBuilder();

        category_enable.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("config.auto-switch-elytra.enabled"), enabled_auto_switch_elytra)
                .setDefaultValue(false)
                .setSaveConsumer(value -> enabled_auto_switch_elytra = value)
                .build()
        );

        category_disable.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("config.armor-stand-interactive.disable"), disable_armor_stand_interactive)
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
