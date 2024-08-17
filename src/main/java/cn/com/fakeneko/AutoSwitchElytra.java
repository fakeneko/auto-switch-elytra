package cn.com.fakeneko;

import cn.com.fakeneko.block.BlockDemo;
import net.fabricmc.api.ModInitializer;
import cn.com.fakeneko.item.ModItemsDemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoSwitchElytra implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "auto-switch-elytra";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		// 初始化物品
		ModItemsDemo.onItemInitialize();
		BlockDemo.onBlockInitialize();
		LOGGER.info("Hello Fabric world!");
	}
}