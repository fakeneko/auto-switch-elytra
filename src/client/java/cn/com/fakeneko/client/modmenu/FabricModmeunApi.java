package cn.com.fakeneko.client.modmenu;

import cn.com.fakeneko.AutoSwitchElytra;
import cn.com.fakeneko.commonConfig.ScreenBuilder;
import cn.com.fakeneko.commonConfig.ScreenBuilderYacl;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class FabricModmeunApi implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (AutoSwitchElytra.istalledClothConfig()) {
            return ScreenBuilder.modScreen::makeScreen;
        }
        if (AutoSwitchElytra.istalledYacl()) {
            return ScreenBuilderYacl.modScreen::makeScreen;
        }
        return null;
    }
}
