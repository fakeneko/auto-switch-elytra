package cn.com.fakeneko;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;

import cn.com.fakeneko.common.conf.Configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoSwitchElytra implements ModInitializer
{
//	public static final int CHEST_SLOT = EquipmentSlot.CHEST.getIndex(Inventory.INVENTORY_SIZE);
	private static final MinecraftClient client = MinecraftClient.getInstance();
	public static final String MOD_ID = "auto-switch-elytra";
	public static final Logger logger = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

	}

//	public static void initialise(String mod_name, String mod_version, String platform) {
//		logger.info("{} v{} for {} successfully enabled!", mod_name, mod_version, platform);
//		Configuration.load();
//		Keybinds.setup();
//	}
//
//	public static void sendMessage(Component component) {
//		if (client.player == null) return;
//		Component message = Component.literal("[AutoElytra] ").append(component);
//		client.player.displayClientMessage(message, false);
//	}
}