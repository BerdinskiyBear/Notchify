package ru.berdinskiybear.notchify.mixins;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.berdinskiybear.notchify.NotchifyMod;

@Mixin(LivingEntity.class)
public abstract class CursedApplePoison extends Entity {
    public CursedApplePoison(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "applyFoodEffects", at = @At(value = "HEAD"))
    public void applyCursedApplePoison(ItemStack stack, World world, LivingEntity targetEntity, CallbackInfo callbackInfo) {
        if (!world.isClient() && NotchifyMod.getConfig().canCursedApplePoison() && stack.getItem() == Items.ENCHANTED_GOLDEN_APPLE && EnchantmentHelper.get(stack).containsKey(Enchantments.VANISHING_CURSE)) {
            targetEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 3000));
        }
    }
}
