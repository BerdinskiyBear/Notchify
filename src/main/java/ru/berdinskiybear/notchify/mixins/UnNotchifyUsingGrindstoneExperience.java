package ru.berdinskiybear.notchify.mixins;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.berdinskiybear.notchify.NotchifyMod;

@Mixin(targets = "net.minecraft.screen.GrindstoneScreenHandler$4")
public class UnNotchifyUsingGrindstoneExperience extends Slot {

    public UnNotchifyUsingGrindstoneExperience(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Inject(method = "getExperience(Lnet/minecraft/item/ItemStack;)I", at = @At(value = "HEAD"), cancellable = true)
    public void insertion(ItemStack stack, CallbackInfoReturnable<Integer> info) {
        if (stack.getItem() == Items.ENCHANTED_GOLDEN_APPLE) {
            int lvl = NotchifyMod.getConfig().getEGAppleEnchantmentCost();
            info.setReturnValue((int) (((lvl <= 16) ? (lvl * (lvl + 6)) : ((lvl <= 31) ? ((2.5D * lvl * lvl) - (40.5D * lvl) + 360) : ((4.5D * lvl * lvl) - (162.5D * lvl) + 2220))) * stack.getCount() * 1.0D));
        }
    }
}