package cn.com.fakeneko.auto_switch_elytra.neoforge;

import cn.com.fakeneko.auto_switch_elytra.AutoSwitchElytra;
import cn.com.fakeneko.auto_switch_elytra.Keybinds.NeoForgeKeyBindings;
import cn.com.fakeneko.auto_switch_elytra.commonConfig.ModConfig;
import cn.com.fakeneko.auto_switch_elytra.modmenu.NeoForgeModListApi;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.moddiscovery.ModFileInfo;

@Mod(AutoSwitchElytra.MOD_ID)
public final class AutoSwitchElytraNeoForge {
    public AutoSwitchElytraNeoForge(IEventBus eventBus) {
        // Run our common setup.
        AutoSwitchElytra.init();
        if (FMLEnvironment.dist.isClient()) {
            ModConfig.modConfig.load();
            NeoForgeModListApi.registerModsPage();
        }
        eventBus.addListener(NeoForgeKeyBindings::register);
    }

    public static boolean istalledClothConfig() {
        ModFileInfo modFileInfo =  FMLLoader.getLoadingModList().getModFileById(AutoSwitchElytra.CLOTH_CONFIG);
        return modFileInfo != null;
    }

    public static boolean istalledYacl() {
        ModFileInfo modFileInfo =  FMLLoader.getLoadingModList().getModFileById(AutoSwitchElytra.YACL);
        return modFileInfo != null;
    }
}
