package cn.com.fakeneko;

import cn.com.fakeneko.config.ModConfig;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoSwitchElytra implements ModInitializer {
	public static final String MOD_ID = "auto-switch-elytra";
	public static final Logger logger = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		getConfig().load();
		logger.info("auto switch elytra initialized");
	}

	// 模组初始化时，获取config实例
	public static ModConfig getConfig() {
		return ModConfig.INSTANCE;
	}
}