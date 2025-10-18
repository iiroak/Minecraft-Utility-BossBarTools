package iroak.bossbar.mixin.client;

import iroak.bossbar.BossBarTools;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.MinecraftClient;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Boss Bar Hiding Mixin
 * 
 * This mixin intercepts the BossBarHud render method to conditionally hide boss bars.
 * The core concept of using Mixin injection to cancel boss bar rendering was inspired
 * by the "No Render" module from Meteor Client (https://github.com/MeteorDevelopment/meteor-client).
 * 
 * This implementation extends the original concept with title-based filtering capabilities
 * and completely removes filtered boss bars from the client's rendering pipeline to prevent
 * empty spaces and ghost titles from appearing.
 */
@Mixin(BossBarHud.class)
public class BossBarHiderMixin {
	
	@Shadow @Final private MinecraftClient client;
	
	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	private void hideBossBar(DrawContext context, CallbackInfo ci) {
		// Check config before cancelling boss bar rendering
		try {
			if (BossBarTools.getConfig() == null) {
				return;
			}
			
			var config = BossBarTools.getConfig();
			
			// If boss bars are completely disabled and filtering is not enabled, cancel immediately
			if (!config.enableFiltering && config.hideBossBars) {
				ci.cancel();
				return;
			}
			
			// If filtering is enabled, continue to render but filter individual boss bars
			
		} catch (Exception e) {
			// In case of any error, don't hide boss bars to be safe
			BossBarTools.LOGGER.error("Error checking boss bar config: {}", e.getMessage());
		}
	}
	
	/**
	 * Redirect the boss bar collection to filter out unwanted boss bars completely.
	 * This approach ensures no empty spaces or ghost elements remain by removing
	 * boss bars from the source collection before any rendering calculations.
	 */
	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Ljava/util/Map;values()Ljava/util/Collection;"))
	private Collection<BossBar> filterBossBarCollection(Map<UUID, BossBar> bossBarMap) {
		try {
			Collection<BossBar> originalBossBars = bossBarMap.values();
			
			if (BossBarTools.getConfig() == null) {
				return originalBossBars;
			}
			
			var config = BossBarTools.getConfig();
			
			// If boss bar hiding is disabled, return all boss bars
			if (!config.hideBossBars) {
				return originalBossBars;
			}
			
			// If filtering is disabled but hiding is enabled, return empty collection
			if (!config.enableFiltering && config.hideBossBars) {
				return Collections.emptyList();
			}
			
			// If filtering is enabled and hiding is enabled, filter the collection
			if (config.enableFiltering && config.hideBossBars) {
				List<BossBar> filteredBossBars = new ArrayList<>();
				
				// Check if there are any filters configured
				boolean hasFilters = !config.exactMatchFilters.isEmpty() || 
								   !config.containsFilters.isEmpty() || 
								   !config.regexFilters.isEmpty();
				
				// If no filters are configured, show all boss bars
				if (!hasFilters) {
					if (config.enableDebugLogging) {
						BossBarTools.LOGGER.info("Filtering enabled but no filters configured - showing all boss bars");
					}
					return originalBossBars;
				}
				
				for (BossBar bossBar : originalBossBars) {
					try {
						Text nameText = bossBar.getName();
						String bossBarTitle = nameText.getString();
						boolean shouldHide = config.shouldHideBossBar(bossBarTitle);
						
						if (config.enableDebugLogging) {
							BossBarTools.LOGGER.info("Boss bar '{}': shouldHide = {}", bossBarTitle, shouldHide);
						}
						
						// Only add boss bars that should NOT be hidden
						if (!shouldHide) {
							filteredBossBars.add(bossBar);
						}
					} catch (Exception e) {
						BossBarTools.LOGGER.error("Error filtering individual boss bar '{}': {}", 
							bossBar.getName().getString(), e.getMessage());
						// Keep the boss bar if there's an error processing it
						filteredBossBars.add(bossBar);
					}
				}
				
				if (config.enableDebugLogging) {
					BossBarTools.LOGGER.info("Filtered {} boss bars from {} original", 
						originalBossBars.size() - filteredBossBars.size(), originalBossBars.size());
				}
				return filteredBossBars;
			}
			
			// Fallback: return original collection
			return originalBossBars;
			
		} catch (Exception e) {
			BossBarTools.LOGGER.error("Error in boss bar collection filter: {}", e.getMessage());
			return bossBarMap.values(); // Return original collection on error
		}
	}
	
	/**
	 * Additional safety measure: intercept individual boss bar rendering
	 * in case any boss bars slip through the collection filter.
	 */
	@Inject(method = "renderBossBar(Lnet/minecraft/client/gui/DrawContext;IILnet/minecraft/entity/boss/BossBar;)V", 
			at = @At("HEAD"), cancellable = true)
	private void safetyFilterIndividualBossBar(DrawContext context, int x, int y, BossBar bossBar, CallbackInfo ci) {
		try {
			if (BossBarTools.getConfig() == null) {
				return;
			}
			
			var config = BossBarTools.getConfig();
			
			// Double-check filtering for any boss bars that might have slipped through
			if (config.hideBossBars && config.enableFiltering) {
				Text nameText = bossBar.getName();
				String bossBarTitle = nameText.getString();
				
				if (config.shouldHideBossBar(bossBarTitle)) {
					ci.cancel();
				}
			}
			
		} catch (Exception e) {
			BossBarTools.LOGGER.error("Error in safety boss bar filter: {}", e.getMessage());
		}
	}
}