package cn.com.fakeneko;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoSwitchElytra implements ModInitializer {
	public static final String MOD_ID = "auto-switch-elytra";
	public static final Logger logger = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		logger.info("auto switch elytra initialized");
	}
}