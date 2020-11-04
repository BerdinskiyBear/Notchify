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

@Mixin(targets = {"net.minecraft.screen.GrindstoneScreenHandler$2", "net.minecraft.screen.GrindstoneScreenHandler$3"})
public class UnNotchifyUsingGrindstoneInsertion extends Slot {

    public UnNotchifyUsingGrindstoneInsertion(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Inject(method = "canInsert(Lnet/minecraft/item/ItemStack;)Z", at = @At(value = "HEAD"), cancellable = true)
    public void insertion(ItemStack stack, CallbackInfoReturnable<Boolean> info) {
        if (stack.getItem() == Items.ENCHANTED_GOLDEN_APPLE)
            if (NotchifyMod.getCurrentConfig().isGrindingEnabled())
                info.setReturnValue(true);
            else
                info.setReturnValue(false);
    }
}
