package iroak.bossbar.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.BossBarHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BossBarHud.class)
public class ExampleClientMixin {
	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	private void hideBossBar(DrawContext context, CallbackInfo ci) {
		// Cancel the boss bar rendering completely
		ci.cancel();
	}
}