package cn.com.fakeneko;

import cn.com.fakeneko.clothconfig.ModConfigBuilder;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoSwitchElytra implements ModInitializer {
	public static final String MOD_ID = "auto-switch-elytra";
	public static final Logger logger = LoggerFactory.getLogger(MOD_ID);
	public static final ModConfigBuilder autoSwitchElytraConfig = new ModConfigBuilder();

	@Override
	public void onInitialize() {
		// 初始化config
		autoSwitchElytraConfig.load();
		logger.info("auto switch elytra initialized");
	}
}