package cn.com.fakeneko.client.modmenu;

import cn.com.fakeneko.CommonClass;
import cn.com.fakeneko.commonConfig.ScreenBuilder;
import cn.com.fakeneko.commonConfig.ScreenBuilderYacl;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class FabricModmeunApi implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (CommonClass.isClothConfigLoaded()) {
            return ScreenBuilder.modScreen::makeScreen;
        }
        if (CommonClass.isYaclLoaded()) {
            return ScreenBuilderYacl.modScreen::makeScreen;
        }
        return null;
    }
}
