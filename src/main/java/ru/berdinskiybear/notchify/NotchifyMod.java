package ru.berdinskiybear.notchify;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class NotchifyMod implements ModInitializer {

    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "notchify";
    public static final String MOD_NAME = "Notchify";
    public static final String FABRIC_ID = "fabric";

    private static NotchifyConfig currentConfig;

    @Override
    public void onInitialize() {
        loadConfig();
        if (FabricLoader.getInstance().isModLoaded(FABRIC_ID)) {
            NotchifyConfigReloader.register();
        }
    }

    public static void log(Level level, String message) {
        LOGGER.log(level, "[" + MOD_NAME + "] " + message);
    }

    public static void log(String message) {
        NotchifyMod.log(Level.INFO, message);
    }

    public static void loadConfig() {
        log("Loading config...");
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), MOD_ID + ".json");
        if (configFile.exists()) {
            try (FileReader fileReader = new FileReader(configFile)) {
                currentConfig = gson.fromJson(fileReader, NotchifyConfig.class);
            } catch (IOException exception) {
                log(Level.ERROR, "Unable to read the config file");
                log(Level.ERROR, exception.getLocalizedMessage());
                currentConfig = new NotchifyConfig();
            }
        } else {
            log(Level.WARN, "No config file found, creating new one");
            currentConfig = new NotchifyConfig();
            try (FileWriter fileWriter = new FileWriter(configFile)) {
                gson.toJson(currentConfig, fileWriter);
            } catch (IOException exception) {
                log(Level.ERROR, "Unable to create and/or write config file " + configFile.getAbsolutePath() + configFile.getName());
                log(Level.ERROR, exception.getLocalizedMessage());
            }
        }
    }

    public static NotchifyConfig getConfig() {
        return currentConfig;
    }

    public static int calculateEnchantmentPower(Random random, int number, int bookshelvesblocks) {
        int bookshelves = Math.min(bookshelvesblocks, 15);

        int power = random.nextInt(8) + 1 + (bookshelves >> 1) + random.nextInt(bookshelves + 1);
        switch (number) {
            case 0:
                return Math.max(power / 3, 1);
            case 1:
                return power * 2 / 3 + 1;
            default:
                return Math.max(power, bookshelves * 2);
        }
    }

    public static int xpLevelsToPoints(int lvl) {
        return (lvl <= 16) ? (lvl * (lvl + 6)) : (int) ((lvl <= 31) ? ((2.5D * lvl * lvl) - (40.5D * lvl) + 360) : ((4.5D * lvl * lvl) - (162.5D * lvl) + 2220));
    }

}
