package iroak.bossbar.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Path;

public class BossBarKillerConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("bossbarkiller.json");
    
    public boolean hideBossBars = true;
    public boolean showToggleMessage = true;
    
    public static BossBarKillerConfig load() {
        if (!CONFIG_PATH.toFile().exists()) {
            BossBarKillerConfig config = new BossBarKillerConfig();
            config.save();
            return config;
        }
        
        try (FileReader reader = new FileReader(CONFIG_PATH.toFile())) {
            return GSON.fromJson(reader, BossBarKillerConfig.class);
        } catch (Exception e) {
            System.err.println("Error loading config: " + e.getMessage());
            return new BossBarKillerConfig();
        }
    }
    
    public void save() {
        try (FileWriter writer = new FileWriter(CONFIG_PATH.toFile())) {
            GSON.toJson(this, writer);
        } catch (Exception e) {
            System.err.println("Error saving config: " + e.getMessage());
        }
    }
}