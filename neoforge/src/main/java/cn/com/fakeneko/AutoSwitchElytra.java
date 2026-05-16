package cn.com.fakeneko;

import cn.com.fakeneko.Keybinds.NeoForgeKeyBindings;
import cn.com.fakeneko.modmenu.NeoForgeModListApi;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class AutoSwitchElytra {

    public AutoSwitchElytra(IEventBus eventBus) {
        CommonClass.init();
        NeoForgeModListApi.registerModsPage();
        eventBus.addListener(NeoForgeKeyBindings::register);
    }
}
