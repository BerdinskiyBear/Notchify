package ru.berdinskiybear.notchify;

import com.google.gson.annotations.SerializedName;

public class NotchifyConfig {

    @SerializedName("enable_enchanting_using_anvil")
    private boolean enableAnvil;
    @SerializedName("enable_enchanting_using_enchanting_table")
    private boolean enableEnchantingTable;
    @SerializedName("enchanted_golden_apple_cost")
    private int egappleEnchantmentCost;
    @SerializedName("creative_player_always_succeeds")
    private boolean creativePlayerAlwaysSucceeds;
    @SerializedName("survival_player_always_succeeds")
    private boolean survivalPlayerAlwaysSucceeds;
    @SerializedName("enchanting_chance_modifier")
    private float enchantingChanceModifier;

    public NotchifyConfig() {
        enableAnvil = true;
        enableEnchantingTable = true;
        egappleEnchantmentCost = 39;
        creativePlayerAlwaysSucceeds = true;
        survivalPlayerAlwaysSucceeds = false;
        enchantingChanceModifier = 1.0F;
    }

    public boolean isAnvilEnabled() {
        return enableAnvil;
    }

    public boolean isEnchantingTableEnabled() {
        return enableEnchantingTable;
    }

    public int getEGAppleEnchantmentCost() {
        return egappleEnchantmentCost;
    }

    public boolean isCreativePlayerAlwaysSuccessful() {
        return creativePlayerAlwaysSucceeds;
    }

    public boolean isSurvivalPlayerAlwaysSuccessful() {
        return survivalPlayerAlwaysSucceeds;
    }

    public float getEnchantingChanceModifier() {
        return enchantingChanceModifier;
    }
}
