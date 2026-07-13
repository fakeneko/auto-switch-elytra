package cn.com.fakeneko;

import cn.com.fakeneko.commonConfig.ModConfig;

public class CommonClass {

    public static void init() {
        Constants.LOG.info("{} is initializing...", Constants.MOD_NAME);
        ModConfig.init();
    }
}
