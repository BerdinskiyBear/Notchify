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
    private double enchantingChanceModifier;
    @SerializedName("can_golden_apple_vanish")
    private boolean canGoldenAppleVanish;
    @SerializedName("vanishing_chance")
    private double vanishingChance;
    @SerializedName("can_enchanted_golden_apple_become_cursed")
    private boolean canApplesBecomeCursed;
    @SerializedName("chance_of_curse")
    private double curseChance;
    @SerializedName("anvil_requires_secondary_item")
    private boolean secondaryItemRequired;
    @SerializedName("anvil_secondary_item_id")
    private String secondaryItemID;
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
    private boolean cursedApplePoison;
    @SerializedName("cursed_apple_poison_effects")
    private StatusEffectInstanceRepresentation[] statusEffectInstanceRepresentations;

    private transient CompoundTag secondaryItemNbt;
    private transient StatusEffectInstance[] statusEffectInstances;

    public NotchifyConfig() {
        enableAnvil = true;
        enableEnchantingTable = true;
        egappleEnchantmentCost = 39;
        creativePlayerAlwaysSucceeds = true;
        survivalPlayerAlwaysSucceeds = false;
        enchantingChanceModifier = 1.0D;
        canGoldenAppleVanish = true;
        vanishingChance = 0.9D;
        canApplesBecomeCursed = true;
        curseChance = 0.1D;
        secondaryItemRequired = true;
        secondaryItemID = "minecraft:nether_star";
        secondaryItemAmount = 1;
        secondaryItemNbtEnabled = false;
        secondaryItemNbtString = "{}";
        secondaryItemNbt = null;
        grindingEnabled = true;
        grindingXpMultiplier = 0.1D;
        cursedApplePoison = true;
        statusEffectInstanceRepresentations = new StatusEffectInstanceRepresentation[]{new StatusEffectInstanceRepresentation(new StatusEffectInstance(StatusEffects.NAUSEA, 3000))};
        statusEffectInstances = null;
    }

    public boolean isAnvilEnabled() {
        return enableAnvil;
    }

    public boolean isEnchantingTableEnabled() {
        return enableEnchantingTable;
    }

    public int getAppleEnchantmentCost() {
        return egappleEnchantmentCost;
    }

    public boolean isCreativePlayerAlwaysSuccessful() {
        return creativePlayerAlwaysSucceeds;
    }

    public boolean isSurvivalPlayerAlwaysSuccessful() {
        return survivalPlayerAlwaysSucceeds;
    }

    public double getEnchantingChanceModifier() {
        return enchantingChanceModifier;
    }

    public boolean canGoldenAppleVanish() {
        return canGoldenAppleVanish;
    }

    public double getVanishingChance() {
        return vanishingChance;
    }

    public boolean canApplesBecomeCursed() {
        return canApplesBecomeCursed;
    }

    public double getCurseChance() {
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

    public boolean isSecondaryItemNbtEnabled() {
        return secondaryItemNbtEnabled;
    }

    public CompoundTag getSecondaryItemNbt() {
        if (secondaryItemNbt == null)
            try {
                secondaryItemNbt = StringNbtReader.parse(secondaryItemNbtString);
            } catch (CommandSyntaxException e) {
                NotchifyMod.log(Level.ERROR, "Error parsing NBT tag: " + e.getMessage());
                secondaryItemNbt = new CompoundTag();
            }
        return secondaryItemNbt;
    }

    public boolean isGrindingEnabled() {
        return grindingEnabled;
    }

    public double getGrindingXpMultiplier() {
        return grindingXpMultiplier;
    }

    public boolean canCursedApplePoison() {
        return cursedApplePoison;
    }

    public StatusEffectInstance[] getStatusEffectInstances() {
        if (statusEffectInstances == null) {
            if (statusEffectInstanceRepresentations == null || statusEffectInstanceRepresentations.length == 0) {
                NotchifyMod.log(Level.WARN, "Should cursed apple poisoning be disabled? There are no effects defined in the config file.");
                statusEffectInstances = new StatusEffectInstance[0];
                cursedApplePoison = false;
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
