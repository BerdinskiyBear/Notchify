package ru.berdinskiybear.notchify;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NotchifyMod implements ModInitializer {

    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "notchify";
    public static final String MOD_NAME = "Notchify";

    @Override
    public void onInitialize() {
        //TODO: Initializer, Configs maybe?..
    }

    public static void log(String message) {
        LOGGER.log(Level.INFO, "["+MOD_NAME+"] " + message);
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

}