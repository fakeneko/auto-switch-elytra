package cn.com.fakeneko.auto_switch_elytra;

import cn.com.fakeneko.auto_switch_elytra.config.ModConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class FabricAutoSwitchElytra implements ModInitializer {
    @Override
    public void onInitialize() {

        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        CommonClass.init();
        ModConfig.modConfig.load();
    }

    public static boolean istalledClothConfig() {
        return FabricLoader.getInstance().isModLoaded(Constants.CLOTH_CONFIG_FABRIC);
    }

    public static boolean istalledYacl() {
        return FabricLoader.getInstance().isModLoaded(Constants.YACL);
    }
}
