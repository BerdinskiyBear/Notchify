package ru.berdinskiybear.notchify.mixins;

import net.minecraft.container.AnvilContainer;
import net.minecraft.container.Property;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilContainer.class)
public class NotchifyUsingAnvil {

    @Shadow @Final private Inventory inventory;
    @Shadow @Final private Inventory result;
    @Shadow private String newItemName;
    @Shadow @Final private Property levelCost;

    @Inject(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/container/AnvilContainer;sendContentUpdates()V"), cancellable = true)
    public void notchification(CallbackInfo info) {
        ItemStack leftStack = this.inventory.getInvStack(0);
        ItemStack rightStack = this.inventory.getInvStack(1);
        ItemStack egapples = new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, 1);

        if (leftStack.getItem() == Items.GOLDEN_APPLE && leftStack.getCount() == 1 && rightStack.isEmpty())
            if (StringUtils.isBlank(this.newItemName)) {
                this.result.setInvStack(0, egapples);
                this.levelCost.set(39);
            } else if (leftStack.hasCustomName() && leftStack.getName().asString().equals(this.newItemName)) {
                this.result.setInvStack(0, egapples.setCustomName(leftStack.getName()));
                this.levelCost.set(39);
            }
    }
}
