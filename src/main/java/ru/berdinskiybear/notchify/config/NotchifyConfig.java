package ru.berdinskiybear.notchify.config;

import com.google.gson.annotations.SerializedName;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;
import ru.berdinskiybear.notchify.NotchifyMod;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

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

    private transient Item secondaryItem;
    private transient NbtCompound secondaryItemNbt;
    //private transient StatusEffectInstance[] statusEffectInstances;
    private transient ArrayList<StatusEffectInstance> statusEffectInstances;

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
        secondaryItemId = Registry.ITEM.getId(Items.NETHER_STAR).toString();
        secondaryItem = null;
        secondaryItemAmount = 1;
        secondaryItemNbtEnabled = false;
        secondaryItemNbtString = new NbtCompound().toString();
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

    public void setSecondaryItem(Item secondaryItem) {
        this.secondaryItem = secondaryItem;
        this.secondaryItemId = Registry.ITEM.getId(secondaryItem).toString();
    }

    public Item getSecondaryItem() {
        if (secondaryItem == null) {
            if (Registry.ITEM.containsId(new Identifier(secondaryItemId)))
                secondaryItem = Registry.ITEM.get(new Identifier(secondaryItemId));
            else
                NotchifyMod.log(Level.ERROR, "There are no item \"" + secondaryItemId + "\"!");
        }
        return secondaryItem;
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

    public void setSecondaryItemNbt(@NotNull NbtCompound secondaryItemNbt) {
        this.secondaryItemNbt = secondaryItemNbt.copy();
        this.secondaryItemNbtString = this.secondaryItemNbt.toString();
    }

    public NbtCompound getSecondaryItemNbt() {
        if (secondaryItemNbt == null)
            try {
                secondaryItemNbt = StringNbtReader.parse(secondaryItemNbtString);
            } catch (CommandSyntaxException e) {
                NotchifyMod.log(Level.ERROR, "Error parsing NBT tag: " + e.getMessage());
                return new NbtCompound();
            }
        if (secondaryItemNbt.isEmpty()) {
            //NotchifyMod.log(Level.WARN, "Should secondary anvil item require NBT tag? Compound tag defined in config file is empty, thus always matches any item.");
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

    public ArrayList<StatusEffectInstance> getStatusEffectInstances() {
        if (statusEffectInstances == null) {
            statusEffectInstances = new ArrayList<>();
            if (statusEffectInstanceRepresentations == null || statusEffectInstanceRepresentations.length == 0) {
                NotchifyMod.log(Level.WARN, "Should cursed apple poisoning be disabled? There are no effects defined in the config file.");
                cursedApplePoisonous = false;
            } else {
                for (StatusEffectInstanceRepresentation representation : statusEffectInstanceRepresentations) {
                    representation.createStatusEffectInstance().ifPresent(statusEffectInstances::add);
                }
            }
        }
        return statusEffectInstances;
    }

    public void setStatusEffectInstances(ArrayList<StatusEffectInstance> statusEffectInstances) {
        this.statusEffectInstances = statusEffectInstances;
        this.statusEffectInstanceRepresentations = new StatusEffectInstanceRepresentation[this.statusEffectInstances.size()];
        for (int i = 0; i < this.statusEffectInstanceRepresentations.length; i++) {
            this.statusEffectInstanceRepresentations[i] = new StatusEffectInstanceRepresentation(this.statusEffectInstances.get(i));
        }
    }

    public static class StatusEffectInstanceRepresentation {
        private String statusEffectId;
        private int duration;
        private int amplifier;

        public StatusEffectInstanceRepresentation() {
            this(new StatusEffectInstance(StatusEffects.NAUSEA, 3000, 0));
        }

        public StatusEffectInstanceRepresentation(StatusEffectInstance instance) {
            statusEffectId = Objects.requireNonNull(Registry.STATUS_EFFECT.getId(instance.getEffectType())).toString();
            duration = instance.getDuration();
            amplifier = instance.getAmplifier();
        }

        public Optional<StatusEffectInstance> createStatusEffectInstance() {
            Identifier id = new Identifier(statusEffectId);
            if (Registry.STATUS_EFFECT.containsId(id))
                return Optional.of(new StatusEffectInstance(Registry.STATUS_EFFECT.get(id), duration, amplifier));
            else {
                NotchifyMod.log(Level.ERROR, "There are no effect with id \"" + statusEffectId + "\"");
                return Optional.empty();
            }
        }

        public static ArrayList<StatusEffectInstanceRepresentation> representationsFrom(Collection<StatusEffectInstance> instances) {
            ArrayList<StatusEffectInstanceRepresentation> representations = new ArrayList<>();
            for (StatusEffectInstance instance : instances)
                representations.add(new StatusEffectInstanceRepresentation(instance));
            return representations;
        }

        public static ArrayList<StatusEffectInstance> instancesFrom(Collection<StatusEffectInstanceRepresentation> representations) {
            ArrayList<StatusEffectInstance> instances = new ArrayList<>();
            for (StatusEffectInstanceRepresentation representation : representations)
                representation.createStatusEffectInstance().ifPresent(instances::add);
            return instances;
        }

        public String getStatusEffectId() {
            return statusEffectId;
        }

        public void setStatusEffectId(String statusEffectId) {
            this.statusEffectId = statusEffectId;
        }

        public void setStatusEffect(StatusEffect effect) {
            this.setStatusEffectId(Objects.requireNonNull(Registry.STATUS_EFFECT.getId(effect)).toString());
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public int getAmplifier() {
            return amplifier;
        }

        public void setAmplifier(int amplifier) {
            this.amplifier = amplifier;
        }

        public boolean equals(Object o) {
            if (o.getClass() != this.getClass())
                return false;
            StatusEffectInstanceRepresentation oo = ((StatusEffectInstanceRepresentation) o);
            if (oo.duration != this.duration)
                return false;
            if (oo.amplifier != this.amplifier)
                return false;
            return oo.statusEffectId.equals(this.statusEffectId);
        }
    }
}
