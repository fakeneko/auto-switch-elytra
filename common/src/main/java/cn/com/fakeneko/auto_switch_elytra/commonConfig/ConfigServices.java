package cn.com.fakeneko.auto_switch_elytra.commonConfig;

import cn.com.fakeneko.auto_switch_elytra.reflection.IGetFilePathHelper;

import java.util.ServiceLoader;

/**
 * @author fakeneko
 * @date 2024/11/10下午2:46
 * @description
 */
public class ConfigServices {
    public static final IGetFilePathHelper FILEPATH = load(IGetFilePathHelper.class);

    public static <T> T load(Class<T> clazz) {
        return ServiceLoader.load(clazz).findFirst().orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
    }
}
