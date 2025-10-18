package iroak.bossbar.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import iroak.bossbar.BossBarKiller;
import iroak.bossbar.BossBarKillerClient;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.Text;

public class ModMenuIntegration implements ModMenuApi {
    
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("text.autoconfig.bossbarkiller.title"));
            
            ConfigCategory general = builder.getOrCreateCategory(Text.translatable("category.bossbarkiller.general"));
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();
            
            BossBarKillerConfig config = BossBarKiller.getConfig();
            
            // Make copies of current values to avoid direct modification
            boolean currentHideBossBars = config.hideBossBars;
            boolean currentShowToggleMessage = config.showToggleMessage;
            
            general.addEntry(entryBuilder.startBooleanToggle(
                Text.translatable("text.autoconfig.bossbarkiller.option.hideBossBars"), 
                currentHideBossBars)
                .setDefaultValue(true)
                .setTooltip(Text.translatable("text.autoconfig.bossbarkiller.option.hideBossBars.@Tooltip"))
                .setSaveConsumer(newValue -> config.hideBossBars = newValue)
                .build());
            
            general.addEntry(entryBuilder.startBooleanToggle(
                Text.translatable("text.autoconfig.bossbarkiller.option.showToggleMessage"), 
                currentShowToggleMessage)
                .setDefaultValue(true)
                .setTooltip(Text.translatable("text.autoconfig.bossbarkiller.option.showToggleMessage.@Tooltip"))
                .setSaveConsumer(newValue -> config.showToggleMessage = newValue)
                .build());
            
            // Add keybinding information
            general.addEntry(entryBuilder.startTextDescription(
                Text.translatable("text.autoconfig.bossbarkiller.keybind.info"))
                .build());
            
            builder.setSavingRunnable(() -> {
                config.save();
                BossBarKiller.CONFIG = config;
                BossBarKiller.LOGGER.info("Configuration saved from menu");
            });
            
            return builder.build();
        };
    }
}