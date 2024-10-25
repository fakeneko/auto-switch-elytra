package cn.com.fakeneko.auto_switch_elytra.modmenu;

import cn.com.fakeneko.auto_switch_elytra.FabricAutoSwitchElytra;
import cn.com.fakeneko.auto_switch_elytra.config.ScreenBuilder;
import cn.com.fakeneko.auto_switch_elytra.config.ScreenBuilderYacl;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

/**
 * @author fakeneko
 * @date 2024/10/24下午8:34
 * @description
 */
public class FabricModmeunApi implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (FabricAutoSwitchElytra.istalledClothConfig()) {
            return ScreenBuilder.modScreen::makeScreen;
        }
        if (FabricAutoSwitchElytra.istalledYacl()) {
            return ScreenBuilderYacl.modScreen::makeScreen;
        }
        return null;
    }
}
