package iroak.bossbar.mixin.client;

import iroak.bossbar.BossBarKiller;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.BossBarHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Boss Bar Hiding Mixin
 * 
 * This mixin intercepts the BossBarHud render method to conditionally hide boss bars.
 * The core concept of using Mixin injection to cancel boss bar rendering was inspired
 * by the "No Render" module from Meteor Client (https://github.com/MeteorDevelopment/meteor-client).
 * 
 * This implementation is original and adapted for our specific configuration system.
 */
@Mixin(BossBarHud.class)
public class BossBarHiderMixin {
	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	private void hideBossBar(DrawContext context, CallbackInfo ci) {
		// Check config before cancelling boss bar rendering
		try {
			if (BossBarKiller.getConfig() != null && BossBarKiller.getConfig().hideBossBars) {
				ci.cancel();
			}
		} catch (Exception e) {
			// In case of any error, don't hide boss bars to be safe
			BossBarKiller.LOGGER.error("Error checking boss bar config: {}", e.getMessage());
		}
	}
}