package cn.com.fakeneko.modmenu;

import cn.com.fakeneko.commonConfig.ModConfig;
import cn.com.fakeneko.config.impl.gui.ConfigScreen;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public class NeoForgeModListApi {
    public static void registerModsPage() {
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class,
                () -> (container, parent) -> new ConfigScreen(parent, ModConfig.MANAGER));
    }
}
