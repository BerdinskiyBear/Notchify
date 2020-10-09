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
    @SerializedName("can_golden_apple_vanish")
    private boolean canGoldenAppleVanish;
    @SerializedName("vanishing_chance")
    private float vanishingChance;
    @SerializedName("can_enchanted_golden_apple_become_cursed")
    private boolean canEGApplesBecomeCursed;
    @SerializedName("chance_of_curse")
    private float curseChance;
    @SerializedName("anvil_requires_secondary_item")
    private boolean secondaryItemRequired;
    @SerializedName("anvil_secondary_item_id")
    private String secondaryItemID;
    @SerializedName("anvil_secondary_item_amount")
    private int secondaryItemAmount;

    public NotchifyConfig() {
        enableAnvil = true;
        enableEnchantingTable = true;
        egappleEnchantmentCost = 39;
        creativePlayerAlwaysSucceeds = true;
        survivalPlayerAlwaysSucceeds = false;
        enchantingChanceModifier = 1.0F;
        canGoldenAppleVanish = true;
        vanishingChance = 0.9F;
        canEGApplesBecomeCursed = true;
        curseChance = 0.1F;
        secondaryItemRequired = true;
        secondaryItemID = "minecraft:nether_star";
        secondaryItemAmount = 1;
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

    public boolean canGoldenAppleVanish() {
        return canGoldenAppleVanish;
    }

    public float getVanishingChance() {
        return vanishingChance;
    }

    public boolean canEGApplesBecomeCursed() {
        return canEGApplesBecomeCursed;
    }

    public float getCurseChance() {
        return curseChance;
    }

    public boolean isSecondaryItemRequired() {
        return secondaryItemRequired;
    }

    public String getSecondaryItemID() {
        return secondaryItemID;
    }

    public int getSecondaryItemAmount() {
        return secondaryItemAmount;
    }
}
