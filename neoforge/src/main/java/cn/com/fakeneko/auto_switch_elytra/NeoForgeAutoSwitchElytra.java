package cn.com.fakeneko.auto_switch_elytra;


import cn.com.fakeneko.auto_switch_elytra.Keybinds.NeoForgeKeyBindings;
import cn.com.fakeneko.auto_switch_elytra.config.ModConfig;
import cn.com.fakeneko.auto_switch_elytra.modmenu.NeoForgeModListApi;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.moddiscovery.ModFileInfo;

@Mod(Constants.MOD_ID)
public class NeoForgeAutoSwitchElytra {

    public NeoForgeAutoSwitchElytra(IEventBus eventBus) {
        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.

        // Use NeoForge to bootstrap the Common mod.
        CommonClass.init();
        if (FMLEnvironment.dist.isClient()) {
            ModConfig.modConfig.load();
            NeoForgeModListApi.registerModsPage();
        }
        eventBus.addListener(NeoForgeKeyBindings::register);
    }

    public static boolean istalledClothConfig() {
        ModFileInfo modFileInfo =  FMLLoader.getLoadingModList().getModFileById(Constants.CLOTH_CONFIG);
        return modFileInfo != null;
    }

    public static boolean istalledYacl() {
        ModFileInfo modFileInfo =  FMLLoader.getLoadingModList().getModFileById(Constants.YACL);
        return modFileInfo != null;
    }
}
