package cn.com.fakeneko.Keybinds;

import cn.com.fakeneko.clothconfig.ModConfigBuilder;
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

import static cn.com.fakeneko.autoSwitchElytra.auto_switch_elytra.LOGGER;
import static cn.com.fakeneko.autoSwitchElytra.auto_switch_elytra.MOD_ID;

/**
 * @author fakeneko
 * @date 2024/10/20下午8:56
 * @description
 */
@EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
public class KeyBindings {
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
        if (KeyBindings.binding1.get().consumeClick()) {
            client.setScreen(ModConfigBuilder.INSTANCE.makeScreen(client.screen));
        }
    }
}
