package iroak.bossbar;

import net.fabricmc.api.ModInitializer;
import iroak.bossbar.config.BossBarToolsConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BossBarTools implements ModInitializer {
	public static final String MOD_ID = "bossbartools";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	
	public static BossBarToolsConfig CONFIG;

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		// Initialize configuration
		CONFIG = BossBarToolsConfig.load();

		if (CONFIG.enableDebugLogging) {
			LOGGER.info("Boss Bar Tools mod loaded! Configuration: hideBossBars={}, enableFiltering={}, exactFilters={}, containsFilters={}, regexFilters={}", 
				CONFIG.hideBossBars, CONFIG.enableFiltering, 
				CONFIG.exactMatchFilters.size(), CONFIG.containsFilters.size(), CONFIG.regexFilters.size());
		} else {
			LOGGER.info("Boss Bar Tools mod loaded!");
		}
	}
	
	public static BossBarToolsConfig getConfig() {
		return CONFIG;
	}
}