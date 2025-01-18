package cn.com.fakeneko.auto_switch_elytra.fabric;

import cn.com.fakeneko.auto_switch_elytra.AutoSwitchElytra;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public final class AutoSwitchElytraFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        AutoSwitchElytra.init();
    }

    public static boolean istalledClothConfig() {
        return FabricLoader.getInstance().isModLoaded(AutoSwitchElytra.CLOTH_CONFIG_FABRIC);
    }

    public static boolean istalledYacl() {
        return FabricLoader.getInstance().isModLoaded(AutoSwitchElytra.YACL);
    }
}
