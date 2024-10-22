package cn.com.fakeneko.config;

import org.jetbrains.annotations.Nullable;

/**
 * @author fakeneko
 * @date 2024/10/22下午10:57
 * @description
 */
public class ConfigOption<T> {
    @Nullable
    private T value;
    private final String name;
    private final T defaultValue;

    public ConfigOption(String name, T defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    public T get() {
        if (value == null) return defaultValue;
        else return value;
    }

    public void set(T value) {
        this.value = value;
    }

    public T getDefault() {
        return defaultValue;
    }

}
