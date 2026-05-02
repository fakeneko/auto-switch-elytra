package cn.com.fakeneko;

import cn.com.fakeneko.commonConfig.ModConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoSwitchElytra implements ModInitializer {
    public static final String MOD_ID = "auto_switch_elytra";
    public static final String MOD_NAME = "Auto Switch Elytra";
    public static final String CLOTH_CONFIG_FABRIC = "cloth-config";
    public static final String CLOTH_CONFIG = "cloth_config";
    public static final String YACL = "yet_another_config_lib_v3";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    @Override
    public void onInitialize() {
        LOG.info(MOD_NAME + " is initializing...");
        ModConfig.modConfig.load();
    }

    public static boolean istalledClothConfig() {
        return FabricLoader.getInstance().isModLoaded(CLOTH_CONFIG_FABRIC);
    }

    public static boolean istalledYacl() {
        return FabricLoader.getInstance().isModLoaded(YACL);
    }
}
