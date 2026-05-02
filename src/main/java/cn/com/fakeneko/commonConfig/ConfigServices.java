package cn.com.fakeneko.commonConfig;

import cn.com.fakeneko.reflection.IGetFilePathHelper;

import java.util.ServiceLoader;

public class ConfigServices {
    public static final IGetFilePathHelper FILEPATH = load(IGetFilePathHelper.class);

    public static <T> T load(Class<T> clazz) {
        return ServiceLoader.load(clazz).findFirst().orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
    }
}
