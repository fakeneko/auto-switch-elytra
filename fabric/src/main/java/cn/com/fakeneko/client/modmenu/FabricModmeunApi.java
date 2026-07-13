package cn.com.fakeneko.client.modmenu;

import cn.com.fakeneko.commonConfig.ModConfig;
import cn.com.fakeneko.config.impl.gui.ConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class FabricModmeunApi implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> new ConfigScreen(parent, ModConfig.MANAGER);
    }
}
