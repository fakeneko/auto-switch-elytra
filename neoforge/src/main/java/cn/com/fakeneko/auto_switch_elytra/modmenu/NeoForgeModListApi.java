package cn.com.fakeneko.auto_switch_elytra.modmenu;

import cn.com.fakeneko.auto_switch_elytra.NeoForgeAutoSwitchElytra;
import cn.com.fakeneko.auto_switch_elytra.config.ScreenBuilder;
import cn.com.fakeneko.auto_switch_elytra.config.ScreenBuilderYacl;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

/**
 * @author fakeneko
 * @date 2024/10/24上午12:31
 * @description
 */
public class NeoForgeModListApi {
    public static void registerModsPage() {
        // 根据存在的模组，加载不同的配置页面
        if (NeoForgeAutoSwitchElytra.istalledClothConfig()) {
            ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class,
                    () -> (container, parent) -> ScreenBuilder.modScreen.makeScreen(parent));
            return;
        }
        if (NeoForgeAutoSwitchElytra.istalledYacl()) {
            ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class,
                    () -> (container, parent) -> ScreenBuilderYacl.modScreen.makeScreen(parent));
        }
    }
}
