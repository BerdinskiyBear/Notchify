package ru.berdinskiybear.notchify.mixins;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.GrindstoneScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.berdinskiybear.notchify.NotchifyMod;

@Mixin(GrindstoneScreenHandler.class)
public abstract class UnNotchifyUsingGrindstone extends ScreenHandler {
    protected UnNotchifyUsingGrindstone(ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    @Shadow @Final private Inventory input;
    @Shadow @Final private Inventory result;

    @Inject(method = "updateResult", at = @At(value = "HEAD"), cancellable = true)
    public void unnotchification(CallbackInfo callbackInfo) {
        if (NotchifyMod.getConfig().isGrindingEnabled()) {
            ItemStack itemStack1 = this.input.getStack(0);
            ItemStack itemStack2 = this.input.getStack(1);
            if ((itemStack1.isEmpty() && itemStack2.getItem() == Items.ENCHANTED_GOLDEN_APPLE) || (itemStack2.isEmpty() && itemStack1.getItem() == Items.ENCHANTED_GOLDEN_APPLE)) {
                this.result.setStack(0, new ItemStack(Items.GOLDEN_APPLE, itemStack1.isEmpty() ? itemStack2.getCount() : itemStack1.getCount()));
                this.sendContentUpdates();
                callbackInfo.cancel();
            }
        }
    }
}
