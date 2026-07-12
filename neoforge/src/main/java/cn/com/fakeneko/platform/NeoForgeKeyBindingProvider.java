package cn.com.fakeneko.platform;

import cn.com.fakeneko.Keybinds.NeoForgeKeyBindings;
import cn.com.fakeneko.platform.services.IKeyBindingProvider;
import net.minecraft.client.KeyMapping;

public class NeoForgeKeyBindingProvider implements IKeyBindingProvider {
    @Override
    public KeyMapping getToggleKey() {
        return NeoForgeKeyBindings.toggleKey;
    }
}
