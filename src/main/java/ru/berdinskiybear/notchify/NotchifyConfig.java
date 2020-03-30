package ru.berdinskiybear.notchify;

import com.google.gson.annotations.SerializedName;

public class NotchifyConfig {

    @SerializedName("enchanted_golden_apple_cost")
    private int egappleEnchantmentCost;
    @SerializedName("creative_player_always_succeeds")
    private boolean creativePlayerAlwaysSucceeds;
    @SerializedName("survival_player_always_succeeds")
    private boolean survivalPlayerAlwaysSucceeds;
    @SerializedName("enchanting_chance_modifier")
    private float enchantingChanceModifier;

    public NotchifyConfig() {
        egappleEnchantmentCost = 39;
        creativePlayerAlwaysSucceeds = true;
        survivalPlayerAlwaysSucceeds = false;
        enchantingChanceModifier = 1.0F;
    }

    public int getEGAppleEnchantmentCost() {
        return egappleEnchantmentCost;
    }

    public boolean isCreativePlayerAlwaysSucceeds() {
        return creativePlayerAlwaysSucceeds;
    }

    public boolean isSurvivalPlayerAlwaysSucceeds() {
        return survivalPlayerAlwaysSucceeds;
    }

    public float getEnchantingChanceModifier() {
        return enchantingChanceModifier;
    }
}
