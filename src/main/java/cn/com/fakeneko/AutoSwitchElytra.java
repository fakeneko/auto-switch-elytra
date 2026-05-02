package cn.com.fakeneko;

import cn.com.fakeneko.commonConfig.ModConfig;
import cn.com.fakeneko.Keybinds.NeoForgeKeyBindings;
import cn.com.fakeneko.modmenu.NeoForgeModListApi;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(AutoSwitchElytra.MOD_ID)
public class AutoSwitchElytra {
    public static final String MOD_ID = "auto_switch_elytra";
    public static final String MOD_NAME = "Auto Switch Elytra";
    public static final String CLOTH_CONFIG = "cloth_config";
    public static final String YACL = "yet_another_config_lib_v3";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public AutoSwitchElytra(IEventBus modEventBus) {
        LOG.info(MOD_NAME + " is initializing...");
        ModConfig.modConfig.load();
        NeoForgeModListApi.registerModsPage();
        modEventBus.addListener(NeoForgeKeyBindings::register);
    }

    public static boolean isClothConfigInstalled() {
        return ModList.get().isLoaded(CLOTH_CONFIG);
    }

    public static boolean isYaclInstalled() {
        return ModList.get().isLoaded(YACL);
    }
}
