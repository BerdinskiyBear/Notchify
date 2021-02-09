package ru.berdinskiybear.notchify.config;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;

public class NotchifyModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (FabricLoader.getInstance().isModLoaded("cloth-config2"))
            return NotchifyClothConfigConfigScreenBuilder::createConfigScreenBuilder;
        else
            return (Screen screen) -> null;
    }
}
