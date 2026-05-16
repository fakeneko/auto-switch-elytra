package cn.com.fakeneko;

import cn.com.fakeneko.commonConfig.ModConfig;
import cn.com.fakeneko.platform.Services;

public class CommonClass {

    public static void init() {
        Constants.LOG.info("{} is initializing...", Constants.MOD_NAME);
        ModConfig.modConfig.load();
    }

    public static boolean isClothConfigLoaded() {
        return Services.PLATFORM.isModLoaded("cloth-config") || Services.PLATFORM.isModLoaded("cloth_config");
    }

    public static boolean isYaclLoaded() {
        return Services.PLATFORM.isModLoaded("yet_another_config_lib_v3");
    }
}
