package ru.berdinskiybear.notchify;

import com.google.gson.annotations.SerializedName;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;

public class NotchifyConfig {

    @SerializedName("enable_enchanting_using_anvil")
    private boolean anvilEnabled;
    @SerializedName("enable_enchanting_using_enchanting_table")
    private boolean enchantingTableEnabled;
    @SerializedName("enchanted_golden_apple_cost")
    private int appleEnchantmentCost;
    @SerializedName("creative_player_always_succeeds")
    private boolean creativePlayerAlwaysSuccessful;
    @SerializedName("survival_player_always_succeeds")
    private boolean survivalPlayerAlwaysSuccessful;
    @SerializedName("enchanting_chance_modifier")
    private double enchantingChanceModifier;
    @SerializedName("can_golden_apple_vanish")
    private boolean goldenAppleDisappearing;
    @SerializedName("vanishing_chance")
    private double vanishingChance;
    @SerializedName("can_enchanted_golden_apple_become_cursed")
    private boolean appleBecomingCursed;
    @SerializedName("chance_of_curse")
    private double curseChance;
    @SerializedName("anvil_requires_secondary_item")
    private boolean secondaryItemRequired;
    @SerializedName("anvil_secondary_item_id")
    private String secondaryItemId;
    @SerializedName("anvil_secondary_item_amount")
    private int secondaryItemAmount;
    @SerializedName("anvil_secondary_item_nbt_enabled")
    private boolean secondaryItemNbtEnabled;
    @SerializedName("anvil_secondary_item_nbt")
    private String secondaryItemNbtString;
    @SerializedName("can_grindstone_remove_enchantment")
    private boolean grindingEnabled;
    @SerializedName("grinding_xp_multiplier")
    private double grindingXpMultiplier;
    @SerializedName("will_cursed_apple_poison")
    private boolean cursedApplePoisonous;
    @SerializedName("cursed_apple_poison_effects")
    private StatusEffectInstanceRepresentation[] statusEffectInstanceRepresentations;

    private transient CompoundTag secondaryItemNbt;
    private transient StatusEffectInstance[] statusEffectInstances;

    public NotchifyConfig() {
        anvilEnabled = true;
        enchantingTableEnabled = true;
        appleEnchantmentCost = 39;
        creativePlayerAlwaysSuccessful = true;
        survivalPlayerAlwaysSuccessful = false;
        enchantingChanceModifier = 1.0D;
        goldenAppleDisappearing = true;
        vanishingChance = 0.9D;
        appleBecomingCursed = true;
        curseChance = 0.1D;
        secondaryItemRequired = true;
        secondaryItemId = "minecraft:nether_star";
        secondaryItemAmount = 1;
        secondaryItemNbtEnabled = false;
        secondaryItemNbtString = "{}";
        secondaryItemNbt = null;
        grindingEnabled = true;
        grindingXpMultiplier = 0.1D;
        cursedApplePoisonous = true;
        statusEffectInstanceRepresentations = new StatusEffectInstanceRepresentation[]{new StatusEffectInstanceRepresentation(new StatusEffectInstance(StatusEffects.NAUSEA, 3000))};
        statusEffectInstances = null;
    }

    public void setAnvilEnabled(boolean anvilEnabled) {
        this.anvilEnabled = anvilEnabled;
    }

    public boolean isAnvilEnabled() {
        return anvilEnabled;
    }

    public void setEnchantingTableEnabled(boolean enchantingTableEnabled) {
        this.enchantingTableEnabled = enchantingTableEnabled;
    }

    public boolean isEnchantingTableEnabled() {
        return enchantingTableEnabled;
    }

    public void setAppleEnchantmentCost(int appleEnchantmentCost) {
        this.appleEnchantmentCost = appleEnchantmentCost;
    }

    public int getAppleEnchantmentCost() {
        return appleEnchantmentCost;
    }

    public void setCreativePlayerAlwaysSuccessful(boolean creativePlayerAlwaysSuccessful) {
        this.creativePlayerAlwaysSuccessful = creativePlayerAlwaysSuccessful;
    }

    public boolean isCreativePlayerAlwaysSuccessful() {
        return creativePlayerAlwaysSuccessful;
    }

    public void setSurvivalPlayerAlwaysSuccessful(boolean survivalPlayerAlwaysSuccessful) {
        this.survivalPlayerAlwaysSuccessful = survivalPlayerAlwaysSuccessful;
    }

    public boolean isSurvivalPlayerAlwaysSuccessful() {
        return survivalPlayerAlwaysSuccessful;
    }

    public void setEnchantingChanceModifier(double enchantingChanceModifier) {
        this.enchantingChanceModifier = enchantingChanceModifier;
    }

    public double getEnchantingChanceModifier() {
        return enchantingChanceModifier;
    }

    public void setGoldenAppleDisappearing(boolean goldenAppleDisappearing) {
        this.goldenAppleDisappearing = goldenAppleDisappearing;
    }

    public boolean isGoldenAppleDisappearing() {
        return goldenAppleDisappearing;
    }

    public void setVanishingChance(double vanishingChance) {
        this.vanishingChance = vanishingChance;
    }

    public double getVanishingChance() {
        return vanishingChance;
    }

    public void setAppleBecomingCursed(boolean appleBecomingCursed) {
        this.appleBecomingCursed = appleBecomingCursed;
    }

    public boolean isAppleBecomingCursed() {
        return appleBecomingCursed;
    }

    public void setCurseChance(double curseChance) {
        this.curseChance = curseChance;
    }

    public double getCurseChance() {
        return curseChance;
    }

    public void setSecondaryItemRequired(boolean secondaryItemRequired) {
        this.secondaryItemRequired = secondaryItemRequired;
    }

    public boolean isSecondaryItemRequired() {
        return secondaryItemRequired;
    }

    public void setSecondaryItemId(String secondaryItemId) {
        this.secondaryItemId = secondaryItemId;
    }

    public String getSecondaryItemId() {
        return secondaryItemId;
    }

    public void setSecondaryItemAmount(int secondaryItemAmount) {
        this.secondaryItemAmount = secondaryItemAmount;
    }

    public int getSecondaryItemAmount() {
        return secondaryItemAmount;
    }

    public void setSecondaryItemNbtEnabled(boolean secondaryItemNbtEnabled) {
        this.secondaryItemNbtEnabled = secondaryItemNbtEnabled;
    }

    public boolean isSecondaryItemNbtEnabled() {
        return secondaryItemNbtEnabled;
    }

    public void setSecondaryItemNbt(@NotNull CompoundTag secondaryItemNbt) {
        this.secondaryItemNbt = secondaryItemNbt.copy();
        this.secondaryItemNbtString = this.secondaryItemNbt.toString();
    }

    public void setSecondaryItemNbt(String secondaryItemNbtString) {
        this.secondaryItemNbtString = secondaryItemNbtString;
        this.secondaryItemNbt = null;
    }

    public CompoundTag getSecondaryItemNbt() {
        if (secondaryItemNbt == null)
            try {
                secondaryItemNbt = StringNbtReader.parse(secondaryItemNbtString);
            } catch (CommandSyntaxException e) {
                NotchifyMod.log(Level.ERROR, "Error parsing NBT tag: " + e.getMessage());
                return new CompoundTag();
            }
        if (secondaryItemNbt.isEmpty()) {
            NotchifyMod.log(Level.WARN, "Should secondary anvil item require NBT tag? Compound tag defined in config file is empty, thus always matches any item.");
            secondaryItemNbtEnabled = false;
        }
        return secondaryItemNbt;
    }

    public void setGrindingEnabled(boolean grindingEnabled) {
        this.grindingEnabled = grindingEnabled;
    }

    public boolean isGrindingEnabled() {
        return grindingEnabled;
    }

    public void setGrindingXpMultiplier(double grindingXpMultiplier) {
        this.grindingXpMultiplier = grindingXpMultiplier;
    }

    public double getGrindingXpMultiplier() {
        return grindingXpMultiplier;
    }

    public void setCursedApplePoisonous(boolean cursedApplePoisonous) {
        this.cursedApplePoisonous = cursedApplePoisonous;
    }

    public boolean isCursedApplePoisonous() {
        return cursedApplePoisonous;
    }

    public StatusEffectInstance[] getStatusEffectInstances() {
        if (statusEffectInstances == null) {
            if (statusEffectInstanceRepresentations == null || statusEffectInstanceRepresentations.length == 0) {
                NotchifyMod.log(Level.WARN, "Should cursed apple poisoning be disabled? There are no effects defined in the config file.");
                statusEffectInstances = new StatusEffectInstance[0];
                cursedApplePoisonous = false;
            } else {
                statusEffectInstances = new StatusEffectInstance[statusEffectInstanceRepresentations.length];
                for (int i = 0; i < statusEffectInstanceRepresentations.length; i++) {
                    statusEffectInstances[i] = statusEffectInstanceRepresentations[i].createStatusEffectInstance();
                }
            }
        }
        return statusEffectInstances;
    }

    public static class StatusEffectInstanceRepresentation {
        private String statusEffectId;
        private int duration;
        private int amplifier;

        public StatusEffectInstanceRepresentation() {
            statusEffectId = "";
            duration = 0;
            amplifier = 0;
        }

        public StatusEffectInstanceRepresentation(StatusEffectInstance instance) {
            statusEffectId = Registry.STATUS_EFFECT.getId(instance.getEffectType()).toString();
            duration = instance.getDuration();
            amplifier = instance.getAmplifier();
        }

        public StatusEffectInstance createStatusEffectInstance() {
            Identifier id = new Identifier(statusEffectId);
            if (Registry.STATUS_EFFECT.containsId(id))
                return new StatusEffectInstance(Registry.STATUS_EFFECT.get(id), duration, amplifier);
            else {
                NotchifyMod.log(Level.ERROR, "There are no effect with id \"" + statusEffectId + "\"");
                return new StatusEffectInstance(StatusEffects.SLOWNESS, 0, 0, false, false);
            }
        }
    }
}
