package cn.com.fakeneko.auto_switch_elytra.modmenu;

import cn.com.fakeneko.auto_switch_elytra.fabric.AutoSwitchElytraFabric;
import cn.com.fakeneko.auto_switch_elytra.commonConfig.ScreenBuilder;
import cn.com.fakeneko.auto_switch_elytra.commonConfig.ScreenBuilderYacl;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

/**
 * @author fakeneko
 * @date 2024/11/10下午3:11
 * @description
 */
public class FabricModmeunApi implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (AutoSwitchElytraFabric.istalledClothConfig()) {
            return ScreenBuilder.modScreen::makeScreen;
        }
        if (AutoSwitchElytraFabric.istalledYacl()) {
            return ScreenBuilderYacl.modScreen::makeScreen;
        }
        return null;
    }
}