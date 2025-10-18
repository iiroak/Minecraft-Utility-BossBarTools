package iroak.bossbar.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BossBarToolsConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("bossbartools.json");
    
    public boolean hideBossBars = true;
    public boolean showToggleMessage = true;
    public boolean enableDebugLogging = false;
    
    // New filtering options - separate lists for each type
    public boolean enableFiltering = false;
    public List<String> exactMatchFilters = new ArrayList<>();
    public List<String> containsFilters = new ArrayList<>();
    public List<String> regexFilters = new ArrayList<>();
    
    public static BossBarToolsConfig load() {
        if (!CONFIG_PATH.toFile().exists()) {
            BossBarToolsConfig config = new BossBarToolsConfig();
            config.save();
            return config;
        }
        
        try (FileReader reader = new FileReader(CONFIG_PATH.toFile())) {
            BossBarToolsConfig config = GSON.fromJson(reader, BossBarToolsConfig.class);
            if (config.exactMatchFilters == null) {
                config.exactMatchFilters = new ArrayList<>();
            }
            if (config.containsFilters == null) {
                config.containsFilters = new ArrayList<>();
            }
            if (config.regexFilters == null) {
                config.regexFilters = new ArrayList<>();
            }
            return config;
        } catch (Exception e) {
            System.err.println("Error loading config: " + e.getMessage());
            return new BossBarToolsConfig();
        }
    }
    
    public void save() {
        try (FileWriter writer = new FileWriter(CONFIG_PATH.toFile())) {
            GSON.toJson(this, writer);
        } catch (Exception e) {
            System.err.println("Error saving config: " + e.getMessage());
        }
    }
    
    public boolean shouldHideBossBar(String bossBarTitle) {
        // If filtering is disabled, use the global hideBossBars setting
        if (!enableFiltering) {
            return hideBossBars;
        }
        
        // If filtering is enabled but hideBossBars is false, don't hide anything
        if (!hideBossBars) {
            return false;
        }
        
        // If filtering is enabled but no filters are configured, don't hide anything
        if (exactMatchFilters.isEmpty() && containsFilters.isEmpty() && regexFilters.isEmpty()) {
            if (enableDebugLogging) {
                System.out.println("[BossBarTools] No filters configured - not hiding boss bar: " + bossBarTitle);
            }
            return false;
        }
        
        String title = bossBarTitle.toLowerCase();
        if (enableDebugLogging) {
            System.out.println("[BossBarTools] Checking boss bar: '" + bossBarTitle + "' against " + 
                exactMatchFilters.size() + " exact, " + containsFilters.size() + " contains, " + 
                regexFilters.size() + " regex filters");
        }
        
        // Check exact match filters
        for (String filter : exactMatchFilters) {
            if (title.equals(filter.toLowerCase())) {
                return true;
            }
        }
        
        // Check contains filters
        for (String filter : containsFilters) {
            if (title.contains(filter.toLowerCase())) {
                return true;
            }
        }
        
        // Check regex filters
        for (String filter : regexFilters) {
            try {
                if (bossBarTitle.matches(filter)) {
                    return true;
                }
            } catch (Exception e) {
                // Invalid regex, skip this filter
                System.err.println("Invalid regex filter: " + filter + " - " + e.getMessage());
            }
        }
        
        if (enableDebugLogging) {
            System.out.println("[BossBarTools] Boss bar '" + bossBarTitle + "' did not match any filters - not hiding");
        }
        return false;
    }
}