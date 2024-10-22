package cn.com.fakeneko.config;

import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

/**
 * @author fakeneko
 * @date 2024/10/20下午8:16
 * @description
 */
public class ModListApi {
    public static void registerModsPage() {
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class,
                () -> (container, parent) -> ScreenBuilder.modScreen.makeScreen(parent));
    }
}
