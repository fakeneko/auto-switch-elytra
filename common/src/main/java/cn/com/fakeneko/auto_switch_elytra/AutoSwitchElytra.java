package cn.com.fakeneko.auto_switch_elytra;

import cn.com.fakeneko.auto_switch_elytra.commonConfig.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AutoSwitchElytra {
    public static final String MOD_ID = "auto_switch_elytra";
    public static final String MOD_NAME = "Auto Switch Elytra";
    // cloth config的fabric modid和neoforge modid是不一样的
    public static final String CLOTH_CONFIG_FABRIC = "cloth-config";
    public static final String CLOTH_CONFIG = "cloth_config";
    public static final String YACL = "yet_another_config_lib_v3";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {
        // Write common init code here.
        LOG.info(MOD_NAME + " is initializing...");
        ModConfig.modConfig.load();
    }
}
