package cn.com.fakeneko.modmenu;

import cn.com.fakeneko.CommonClass;
import cn.com.fakeneko.commonConfig.ScreenBuilder;
import cn.com.fakeneko.commonConfig.ScreenBuilderYacl;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public class NeoForgeModListApi {
    public static void registerModsPage() {
        if (CommonClass.isClothConfigLoaded()) {
            ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class,
                    () -> (container, parent) -> ScreenBuilder.modScreen.makeScreen(parent));
            return;
        }
        if (CommonClass.isYaclLoaded()) {
            ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class,
                    () -> (container, parent) -> ScreenBuilderYacl.modScreen.makeScreen(parent));
        }
    }
}
