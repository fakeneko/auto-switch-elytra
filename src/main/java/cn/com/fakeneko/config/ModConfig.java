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
    public final Path configFile = FabricLoader.getInstance().getConfigDir().resolve("auto-switch-elytra.json");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public boolean enabled_auto_switch_elytra = false;

    public void save() {
        try {
            Files.deleteIfExists(configFile);
            JsonObject json = new JsonObject();
            json.addProperty("auto_switch_elytra", enabled_auto_switch_elytra);
            Files.writeString(configFile, gson.toJson(json));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            if (Files.notExists(configFile)) {
                save();
                return;
            }

            JsonObject json = gson.fromJson(Files.readString(configFile), JsonObject.class);

            if (json.has("auto_switch_elytra"))
                enabled_auto_switch_elytra = json.getAsJsonPrimitive("auto_switch_elytra").getAsBoolean();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Screen makeScreen(Screen parent) {
        return YetAnotherConfigLib.createBuilder()
                .title(Text.translatable("config.auto-switch-elytra.title"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("config.auto-switch-elytra.general"))
                        .tooltip(Text.translatable("config.auto-switch-elytra.general.tooltip"))
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("config.auto-switch-elytra.enabled"))
                                .description(OptionDescription.of(Text.translatable("config.auto-switch-elytra.enabled.description")))
                                .binding(
                                        false,
                                        () -> enabled_auto_switch_elytra,
                                        value -> enabled_auto_switch_elytra = value
                                )
                                .controller(BooleanControllerBuilder::create)
                                .build())
                        .build())
                .save(this::save)
                .build()
                .generateScreen(parent);
    }

    public boolean get_enabled_auto_switch_elytra() {
        return enabled_auto_switch_elytra;
    }
}
