package cn.com.fakeneko.auto_switch_elytra.config;

import cn.com.fakeneko.auto_switch_elytra.config.services.IGetFilePathHelper;
import java.util.ServiceLoader;

/**
 * @author fakeneko
 * @date 2024/10/23下午11:58
 * @description
 */
public class ConfigServices {
    public static final IGetFilePathHelper FILEPATH = load(IGetFilePathHelper.class);

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz).findFirst().orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        return loadedService;
    }
}
