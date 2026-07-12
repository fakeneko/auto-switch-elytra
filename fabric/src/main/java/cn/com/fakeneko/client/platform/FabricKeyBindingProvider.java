package cn.com.fakeneko.client.platform;

import cn.com.fakeneko.client.Keybinds.FabricKeyBindings;
import cn.com.fakeneko.platform.services.IKeyBindingProvider;
import net.minecraft.client.KeyMapping;

public class FabricKeyBindingProvider implements IKeyBindingProvider {
    @Override
    public KeyMapping getToggleKey() {
        return FabricKeyBindings.toggleKey;
    }
}
