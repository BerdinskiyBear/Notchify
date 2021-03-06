package ru.berdinskiybear.notchify;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

public class NotchifyConfigReloader implements SimpleSynchronousResourceReloadListener {
    @Override
    public Identifier getFabricId() {
        return new Identifier(NotchifyMod.MOD_ID, "config_reloader");
    }

    @Override
    public void apply(ResourceManager manager) {
        NotchifyMod.loadConfig();
    }

    public static void register() {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new NotchifyConfigReloader());
        NotchifyMod.log("Config now can be reloaded with /reload");
    }
}