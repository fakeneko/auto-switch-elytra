package cn.com.fakeneko.commonConfig;

import cn.com.fakeneko.AutoSwitchElytra;
import cn.com.fakeneko.config.NeoForgeGetFilePathHelper;
import cn.com.fakeneko.reflection.IGetFilePathHelper;

import java.util.ServiceLoader;

public class ConfigServices {
    public static final IGetFilePathHelper FILEPATH;

    static {
        IGetFilePathHelper helper = null;
        try {
            helper = ServiceLoader.load(IGetFilePathHelper.class).findFirst().orElse(null);
        } catch (Exception e) {
            AutoSwitchElytra.LOG.warn("Failed to load IGetFilePathHelper via ServiceLoader, using fallback");
        }
        if (helper == null) {
            helper = new NeoForgeGetFilePathHelper();
        }
        FILEPATH = helper;
    }
}
