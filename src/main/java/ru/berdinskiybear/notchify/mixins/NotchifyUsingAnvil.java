package ru.berdinskiybear.notchify.mixins;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.*;
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

@Mixin(AnvilScreenHandler.class)
public abstract class NotchifyUsingAnvil extends ForgingScreenHandler {

    public NotchifyUsingAnvil(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }


    @Shadow private String newItemName;
    @Shadow @Final private Property levelCost;

    @Inject(method = "updateResult", at = @At(value = "HEAD"), cancellable = true)
    public void notchification(CallbackInfo info) {
        if (NotchifyMod.getConfig().isAnvilEnabled()) {
            ItemStack leftStack = this.input.getStack(0);

            // если предмет слева - золотое яблоко, предмет слева один и либо поле ввода имени пустое, либо предмет слева имеет имя и оно совпадает с текстом в поле ввода
            if (leftStack.getItem() == Items.GOLDEN_APPLE && leftStack.getCount() == 1 && (StringUtils.isBlank(this.newItemName) || (leftStack.hasCustomName() && leftStack.getName().asString().equals(this.newItemName)))) {
                ItemStack rightStack = this.input.getStack(1);

                // если либо предмет справа необходим и он как в настройках и он один, либо предмет справа не нужен и предмет справа отсутствует
                if ((NotchifyMod.getConfig().isSecondaryItemRequired() && rightStack.getItem() == Registry.ITEM.get(new Identifier(NotchifyMod.getConfig().getSecondaryItemID())) && rightStack.getCount() == 1) || (!NotchifyMod.getConfig().isSecondaryItemRequired() && rightStack.isEmpty())) {
                    ItemStack newApple = new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, 1);
                    if (leftStack.hasCustomName())
                        newApple.setCustomName(leftStack.getName());

                    this.output.setStack(0, newApple);
                    this.levelCost.set(NotchifyMod.getConfig().getEGAppleEnchantmentCost());
                } else {
                    this.output.setStack(0, ItemStack.EMPTY);
                    this.levelCost.set(0);
                }

                this.sendContentUpdates();

                info.cancel();
            }
        }
    }
}
