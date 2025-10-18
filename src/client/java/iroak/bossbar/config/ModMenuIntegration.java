package iroak.bossbar.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import iroak.bossbar.BossBarTools;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            var config = BossBarTools.getConfig();

            // Make copies of current values to avoid direct modification
            boolean currentHideBossBars = config.hideBossBars;
            boolean currentShowToggleMessage = config.showToggleMessage;
            boolean currentEnableFiltering = config.enableFiltering;
            boolean currentEnableDebugLogging = config.enableDebugLogging;
            List<String> currentExactMatchFilters = new ArrayList<>(config.exactMatchFilters);
            List<String> currentContainsFilters = new ArrayList<>(config.containsFilters);
            List<String> currentRegexFilters = new ArrayList<>(config.regexFilters);

            ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("text.autoconfig.bossbartools.title"));

            // Create categories for each tab
            ConfigCategory general = builder.getOrCreateCategory(Text.translatable("category.bossbartools.general"));
            ConfigCategory exactMatch = builder.getOrCreateCategory(Text.translatable("category.bossbartools.exactmatch"));
            ConfigCategory contains = builder.getOrCreateCategory(Text.translatable("category.bossbartools.contains"));
            ConfigCategory regex = builder.getOrCreateCategory(Text.translatable("category.bossbartools.regex"));

            ConfigEntryBuilder entryBuilder = builder.entryBuilder();

            // General tab - Hide All Boss Bars
            general.addEntry(entryBuilder.startBooleanToggle(
                Text.translatable("text.autoconfig.bossbartools.option.hideBossBars"),
                currentHideBossBars)
                .setDefaultValue(true)
                .setTooltip(Text.translatable("text.autoconfig.bossbartools.option.hideBossBars.@Tooltip"))
                .setSaveConsumer(newValue -> config.hideBossBars = newValue)
                .build());

            // General tab - Show Toggle Message
            general.addEntry(entryBuilder.startBooleanToggle(
                Text.translatable("text.autoconfig.bossbartools.option.showToggleMessage"),
                currentShowToggleMessage)
                .setDefaultValue(true)
                .setTooltip(Text.translatable("text.autoconfig.bossbartools.option.showToggleMessage.@Tooltip"))
                .setSaveConsumer(newValue -> config.showToggleMessage = newValue)
                .build());

            // General tab - Enable Filtering
            general.addEntry(entryBuilder.startBooleanToggle(
                Text.translatable("text.autoconfig.bossbartools.option.enableFiltering"),
                currentEnableFiltering)
                .setDefaultValue(false)
                .setTooltip(Text.translatable("text.autoconfig.bossbartools.option.enableFiltering.@Tooltip"))
                .setSaveConsumer(newValue -> config.enableFiltering = newValue)
                .build());

            // General tab - Enable Debug Logging
            general.addEntry(entryBuilder.startBooleanToggle(
                Text.translatable("text.autoconfig.bossbartools.option.enableDebugLogging"),
                currentEnableDebugLogging)
                .setDefaultValue(false)
                .setTooltip(Text.translatable("text.autoconfig.bossbartools.option.enableDebugLogging.@Tooltip"))
                .setSaveConsumer(newValue -> config.enableDebugLogging = newValue)
                .build());

            // General tab - Keybind info
            general.addEntry(entryBuilder.startTextDescription(
                Text.translatable("text.autoconfig.bossbartools.keybind.info"))
                .build());

            // Exact Match tab - List field
            exactMatch.addEntry(entryBuilder.startStrList(
                Text.translatable("text.autoconfig.bossbartools.option.exactMatchFilters"),
                currentExactMatchFilters)
                .setDefaultValue(new ArrayList<>())
                .setTooltip(Text.translatable("text.autoconfig.bossbartools.option.exactMatchFilters.@Tooltip"))
                .setSaveConsumer(newValue -> {
                    config.exactMatchFilters.clear();
                    config.exactMatchFilters.addAll(newValue);
                })
                .build());

            // Exact Match tab - Example text
            exactMatch.addEntry(entryBuilder.startTextDescription(
                Text.translatable("text.autoconfig.bossbartools.exactmatch.examples"))
                .build());

            // Contains tab - List field
            contains.addEntry(entryBuilder.startStrList(
                Text.translatable("text.autoconfig.bossbartools.option.containsFilters"),
                currentContainsFilters)
                .setDefaultValue(new ArrayList<>())
                .setTooltip(Text.translatable("text.autoconfig.bossbartools.option.containsFilters.@Tooltip"))
                .setSaveConsumer(newValue -> {
                    config.containsFilters.clear();
                    config.containsFilters.addAll(newValue);
                })
                .build());

            // Contains tab - Description text
            contains.addEntry(entryBuilder.startTextDescription(
                Text.translatable("text.autoconfig.bossbartools.contains.description"))
                .build());

            // Regex tab - List field
            regex.addEntry(entryBuilder.startStrList(
                Text.translatable("text.autoconfig.bossbartools.option.regexFilters"),
                currentRegexFilters)
                .setDefaultValue(new ArrayList<>())
                .setTooltip(Text.translatable("text.autoconfig.bossbartools.option.regexFilters.@Tooltip"))
                .setSaveConsumer(newValue -> {
                    config.regexFilters.clear();
                    config.regexFilters.addAll(newValue);
                })
                .build());

            // Regex tab - Description text
            regex.addEntry(entryBuilder.startTextDescription(
                Text.translatable("text.autoconfig.bossbartools.regex.description"))
                .build());

            return builder.build();
        };
    }
}
