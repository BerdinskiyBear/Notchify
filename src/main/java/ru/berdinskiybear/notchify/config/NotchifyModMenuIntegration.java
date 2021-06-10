package ru.berdinskiybear.notchify.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.client.gui.screen.Screen;

public class NotchifyModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        /*if (FabricLoader.getInstance().isModLoaded("cloth-config2"))
            return NotchifyClothConfigConfigScreenBuilder::createConfigScreenBuilder;
        else*/
        //TODO enable that ^^^ when ClothConfig 5 works
            return (Screen screen) -> null;
    }
}
