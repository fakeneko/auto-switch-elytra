package cn.com.fakeneko.auto_switch_elytra.modmenu;

import cn.com.fakeneko.auto_switch_elytra.neoforge.AutoSwitchElytraNeoForge;
import cn.com.fakeneko.auto_switch_elytra.commonConfig.ScreenBuilder;
import cn.com.fakeneko.auto_switch_elytra.commonConfig.ScreenBuilderYacl;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

/**
 * @author fakeneko
 * @date 2024/11/10下午3:21
 * @description
 */
public class NeoForgeModListApi {
    public static void registerModsPage() {
        // 根据存在的模组，加载不同的配置页面
        if (AutoSwitchElytraNeoForge.istalledClothConfig()) {
            ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class,
                    () -> (container, parent) -> ScreenBuilder.modScreen.makeScreen(parent));
            return;
        }
        if (AutoSwitchElytraNeoForge.istalledYacl()) {
            ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class,
                    () -> (container, parent) -> ScreenBuilderYacl.modScreen.makeScreen(parent));
        }
    }
}
