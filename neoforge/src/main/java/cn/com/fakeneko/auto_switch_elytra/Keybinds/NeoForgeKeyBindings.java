package cn.com.fakeneko.auto_switch_elytra.Keybinds;

import cn.com.fakeneko.auto_switch_elytra.Constants;
import cn.com.fakeneko.auto_switch_elytra.config.ScreenBuilder;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

/**
 * @author fakeneko
 * @date 2024/10/24下午9:49
 * @description
 */

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class NeoForgeKeyBindings {
    public static Lazy<KeyMapping> binding1 = null;

    public static void register(final RegisterKeyMappingsEvent event) {
        binding1  = Lazy.of(() -> new KeyMapping(
                "key.category.auto_switch_elytra.configuration",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_ALT,
                "key.category.auto_switch_elytra"
        ));

        event.register(binding1.get());
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post e) {
        Minecraft client = Minecraft.getInstance();
        if (NeoForgeKeyBindings.binding1.get().consumeClick()) {
            client.setScreen(ScreenBuilder.modScreen.makeScreen(client.screen));
        }
    }
}
