package ru.berdinskiybear.notchify;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.FabricLoader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class NotchifyMod implements ModInitializer {

    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "notchify";
    public static final String MOD_NAME = "Notchify";

    private static NotchifyConfig currentConfig;

    @Override
    public void onInitialize() {
        loadConfig();
    }

    public static void log(String message) {
        LOGGER.log(Level.INFO, "["+MOD_NAME+"] " + message);
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

    public static void loadConfig() {
        log("Loading config...");
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        File configFile = new File(FabricLoader.INSTANCE.getConfigDirectory(), MOD_ID + ".json");
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
}