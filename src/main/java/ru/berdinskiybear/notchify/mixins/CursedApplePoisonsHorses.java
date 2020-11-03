package ru.berdinskiybear.notchify.mixins;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.berdinskiybear.notchify.NotchifyMod;

@Mixin(HorseBaseEntity.class)
public abstract class CursedApplePoisonsHorses extends AnimalEntity {
    protected CursedApplePoisonsHorses(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "receiveFood", at = @At(value = "HEAD"))
    public void applyCursedApplePoison(PlayerEntity player, ItemStack item, CallbackInfoReturnable<Boolean> cir) {
        if (!this.world.isClient() && NotchifyMod.getConfig().canCursedApplePoison() && item.getItem() == Items.ENCHANTED_GOLDEN_APPLE && EnchantmentHelper.get(item).containsKey(Enchantments.VANISHING_CURSE)) {
            for (StatusEffectInstance effect : NotchifyMod.getConfig().getStatusEffectInstances())
                this.addStatusEffect(new StatusEffectInstance(effect));
        }
    }
}
