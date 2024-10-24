package cn.com.fakeneko.auto_switch_elytra.modmenu;

import cn.com.fakeneko.auto_switch_elytra.config.ScreenBuilder;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

/**
 * @author fakeneko
 * @date 2024/10/24上午12:31
 * @description
 */
public class NeoForgeModListApi {
    public static void registerModsPage() {
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class,
                () -> (container, parent) -> ScreenBuilder.modScreen.makeScreen(parent));
    }
}
