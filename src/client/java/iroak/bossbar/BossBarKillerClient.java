package iroak.bossbar;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import iroak.bossbar.config.BossBarKillerConfig;

public class BossBarKillerClient implements ClientModInitializer {
	
	private static KeyBinding toggleBossBarKey;
	
	@Override
	public void onInitializeClient() {
		// Register keybinding
		toggleBossBarKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key.bossbarkiller.toggle", // Translation key
			InputUtil.Type.KEYSYM, // Key type
			GLFW.GLFW_KEY_H, // Default key (H)
			"category.bossbarkiller.general" // Category
		));
		
		// Register tick event for handling key presses
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (toggleBossBarKey.wasPressed()) {
				if (client.player != null && BossBarKiller.getConfig() != null) {
					// Toggle the configuration
					BossBarKillerConfig config = BossBarKiller.getConfig();
					boolean previousState = config.hideBossBars;
					config.hideBossBars = !config.hideBossBars;
					config.save();
					
					// Update the main config reference
					BossBarKiller.CONFIG = config;
					
					// Show message to player if enabled
					if (config.showToggleMessage) {
						String messageKey = config.hideBossBars ? 
							"message.bossbarkiller.enabled" : "message.bossbarkiller.disabled";
						client.player.sendMessage(Text.translatable(messageKey), true);
					}
					
					// Log the change for debugging
					BossBarKiller.LOGGER.info("Boss bar visibility toggled: {} -> {}", 
						previousState, config.hideBossBars);
				}
			}
		});
	}
}