package cn.com.fakeneko.autoSwitchElytra;

import cn.com.fakeneko.Keybinds.KeyBindings;
import cn.com.fakeneko.clothconfig.ModConfigBuilder;
import cn.com.fakeneko.clothconfig.ModMenuApiAutoSwitchElytra;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(auto_switch_elytra.MOD_ID)
public class auto_switch_elytra
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "auto_switch_elytra";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public auto_switch_elytra(IEventBus modEventBus)
    {
        if (FMLEnvironment.dist.isClient()) {
            ModConfigBuilder.INSTANCE.load();
            ModMenuApiAutoSwitchElytra.registerModsPage();
        }
        modEventBus.addListener(KeyBindings::register);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            LOGGER.info("auto switch elytra initialized");
        }
    }
}
