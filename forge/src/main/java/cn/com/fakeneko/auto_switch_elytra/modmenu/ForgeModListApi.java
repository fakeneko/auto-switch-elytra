package cn.com.fakeneko.auto_switch_elytra.modmenu;

import cn.com.fakeneko.auto_switch_elytra.ForgeAutoSwitchElytra;
import cn.com.fakeneko.auto_switch_elytra.config.ScreenBuilder;
import cn.com.fakeneko.auto_switch_elytra.config.ScreenBuilderYacl;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;

/**
 * @author fakeneko
 * @date 2024/10/24下午8:10
 * @description
 */
public class ForgeModListApi {
    public static void registerModsPage() {
        if (ForgeAutoSwitchElytra.istalledClothConfig()) {
            ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                    () -> new ConfigScreenHandler.ConfigScreenFactory(
                            (container, parent) -> ScreenBuilder.modScreen.makeScreen(parent)
                    ));
        }
    }
}
