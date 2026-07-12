package cn.com.fakeneko.commonConfig;

import cn.com.fakeneko.platform.services.IKeyBindingProvider;
import cn.com.fakeneko.reflection.IGetFilePathHelper;

import java.util.ServiceLoader;

public class ConfigServices {
    public static final IGetFilePathHelper FILEPATH = load(IGetFilePathHelper.class);
    public static final IKeyBindingProvider KEYBINDING = load(IKeyBindingProvider.class);

    public static <T> T load(Class<T> clazz) {
        return ServiceLoader.load(clazz, ConfigServices.class.getClassLoader())
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
    }
}
