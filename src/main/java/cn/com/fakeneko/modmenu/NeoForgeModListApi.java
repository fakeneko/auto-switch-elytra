package cn.com.fakeneko.modmenu;

import cn.com.fakeneko.AutoSwitchElytra;
import cn.com.fakeneko.commonConfig.ScreenBuilder;
import cn.com.fakeneko.commonConfig.ScreenBuilderYacl;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

/**
 * @author fakeneko
 * @date 2026/5/213:13
 * @description
 */
public class NeoForgeModListApi {
    public static void registerModsPage() {
        // 根据存在的模组，加载不同的配置页面
        if (AutoSwitchElytra.isClothConfigInstalled()) {
            ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class,
                    () -> (container, parent) -> ScreenBuilder.modScreen.makeScreen(parent));
            return;
        }
        if (AutoSwitchElytra.isYaclInstalled()) {
            ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class,
                    () -> (container, parent) -> ScreenBuilderYacl.modScreen.makeScreen(parent));
        }
    }
}