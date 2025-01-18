package cn.com.fakeneko.auto_switch_elytra.commonConfig;

import cn.com.fakeneko.auto_switch_elytra.AutoSwitchElytra;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fakeneko
 * @date 2024/11/10下午2:49
 * @description
 */
public class ModConfig {
    public static final ModConfig modConfig =  new ModConfig();

    private static final List<ConfigOption<Object>> options = new ArrayList<>();

    private final Path configFile = new File(ConfigServices.FILEPATH.getFilePath(), AutoSwitchElytra.MOD_ID + ".json").toPath();

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static ConfigOption<Boolean> enabled_auto_switch_elytra
            = registerOption(new ConfigOption<>("enabled_auto_switch_elytra", false));

    public static ConfigOption<Boolean> disable_armor_stand_interactive
            = registerOption(new ConfigOption<>("disable_armor_stand_interactive", false));

    // 加载配置
    public void load() {
        try {
            // 检查配置文件是否存在
            if (Files.notExists(configFile)) {
                save();
                return;
            }
            String data;
            // 读取配置文件
            data = Files.readString(configFile);
            Map<?, ?> map = gson.fromJson(data, Map.class);
            // 加载配置项
            for (ConfigOption<Object> option: options) {
                option.set(map.get(option.getName()));
            }
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    // 存储配置
    void save() {
        try {
            Files.deleteIfExists(configFile);
            Map<String, Object> map = new HashMap<>();
            for (ConfigOption<Object> option: options) {
                map.put(option.getName(), option.get());
            }
            Files.writeString(configFile, gson.toJson(map));
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    // 注册配置项
    private static <T> ConfigOption<T> registerOption(ConfigOption<T> option) {
        options.add((ConfigOption<Object>) option);
        return option;
    }
}
