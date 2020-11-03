package ru.berdinskiybear.notchify.mixins;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.berdinskiybear.notchify.NotchifyMod;

@Mixin(LivingEntity.class)
public class CursedApplePoison {

    @Inject(method = "applyFoodEffects", at = @At(value = "HEAD"))
    public void applyCursedApplePoison(ItemStack stack, World world, LivingEntity targetEntity, CallbackInfo callbackInfo) {
        if (!world.isClient() && NotchifyMod.getConfig().canCursedApplePoison() && stack.getItem() == Items.ENCHANTED_GOLDEN_APPLE && EnchantmentHelper.get(stack).containsKey(Enchantments.VANISHING_CURSE)) {
            for (StatusEffectInstance effect : NotchifyMod.getConfig().getStatusEffectInstances())
                targetEntity.addStatusEffect(effect);
        }
    }
}
