package ru.berdinskiybear.notchify.mixins;

import net.minecraft.container.AnvilContainer;
import net.minecraft.container.Container;
import net.minecraft.container.ContainerType;
import net.minecraft.container.Property;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.berdinskiybear.notchify.NotchifyMod;

@Mixin(AnvilContainer.class)
public abstract class NotchifyUsingAnvil extends Container {

    protected NotchifyUsingAnvil(ContainerType<?> type, int syncId) {
        super(type, syncId);
    }

    @Shadow @Final private Inventory inventory;
    @Shadow @Final private Inventory result;
    @Shadow private String newItemName;
    @Shadow @Final private Property levelCost;

    @Inject(method = "updateResult", at = @At(value = "HEAD"), cancellable = true)
    public void notchification(CallbackInfo info) {
        if (NotchifyMod.getConfig().isAnvilEnabled()) {
            ItemStack leftStack = this.inventory.getInvStack(0);

            // если предмет слева - золотое яблоко, предмет слева один и либо поле ввода имени пустое, либо предмет слева имеет имя и оно совпадает с текстом в поле ввода
            if (leftStack.getItem() == Items.GOLDEN_APPLE && leftStack.getCount() == 1 && (StringUtils.isBlank(this.newItemName) || (leftStack.hasCustomName() && leftStack.getName().asString().equals(this.newItemName)))) {
                ItemStack rightStack = this.inventory.getInvStack(1);

                // если либо предмет справа необходим и он как в настройках и он один, либо предмет справа не нужен и предмет справа отсутствует
                if ((NotchifyMod.getConfig().isSecondaryItemRequired() && rightStack.getItem() == Registry.ITEM.get(new Identifier(NotchifyMod.getConfig().getSecondaryItemID())) && rightStack.getCount() == 1) || (!NotchifyMod.getConfig().isSecondaryItemRequired() && rightStack.isEmpty())) {
                    ItemStack newApple = new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, 1);
                    if (leftStack.hasCustomName())
                        newApple.setCustomName(leftStack.getName());

                    this.result.setInvStack(0, newApple);
                    this.levelCost.set(NotchifyMod.getConfig().getEGAppleEnchantmentCost());
                } else {
                    this.result.setInvStack(0, ItemStack.EMPTY);
                    this.levelCost.set(0);
                }

                this.sendContentUpdates();

                info.cancel();
            }
        }
    }
}
