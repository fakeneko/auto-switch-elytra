package cn.com.fakeneko.modmenu;

import cn.com.fakeneko.AutoSwitchElytra;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;


/**
 * @author fakeneko
 * @date 2024/8/20下午11:13
 * @description
 */
public class ModMenuApiAutoSwitchElytra implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoSwitchElytra.getConfig().makeScreen(parent);
    }
}
