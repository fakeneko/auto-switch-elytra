package cn.com.fakeneko.auto_switch_elytra;

import cn.com.fakeneko.auto_switch_elytra.Keybinds.ForgeKeyBindings;
import cn.com.fakeneko.auto_switch_elytra.config.ModConfig;
import cn.com.fakeneko.auto_switch_elytra.modmenu.ForgeModListApi;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.moddiscovery.ModFileInfo;

@Mod(Constants.MOD_ID)
public class ForgeAutoSwitchElytra {

    public ForgeAutoSwitchElytra() {
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.

        // Use Forge to bootstrap the Common mod.
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        CommonClass.init();

        if (FMLEnvironment.dist.isClient()) {
            ModConfig.modConfig.load();
            ForgeModListApi.registerModsPage();
        }

        modEventBus.addListener(ForgeKeyBindings::register);
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
