package ru.berdinskiybear.notchify.mixins;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
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
import java.util.Objects;
import java.util.function.BiFunction;

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

                BiFunction<CompoundTag, CompoundTag, Boolean> has = (itemTags, requiredTags) -> {
                    if (requiredTags == null) return true;
                    else if (requiredTags.isEmpty()) return true;
                    else if (itemTags == null) return false;
                    else if (itemTags.isEmpty()) return false;
                    else {
                        for (String key: requiredTags.getKeys())
                            if (!Objects.equals(itemTags.get(key), requiredTags.get(key))) return false;
                        return true;
                    }
                };

                if ( // если
                        ( // либо
                                NotchifyMod.getConfig().isSecondaryItemRequired() // второй предмет необходим
                                        && rightStack.getItem() == Registry.ITEM.get(new Identifier(NotchifyMod.getConfig().getSecondaryItemID())) // и предмет как в настройках
                                        && rightStack.getCount() == NotchifyMod.getConfig().getSecondaryItemAmount() // и предметов количество как в настройках
                                        && (!NotchifyMod.getConfig().isSecondaryItemNbtEnabled() // и если NBT необходим
                                                        || has.apply(rightStack.getTag(), NotchifyMod.getConfig().getSecondaryItemNbt())) // и теги предмета соответствуют тегам
                        ) || ( // либо
                                !NotchifyMod.getConfig().isSecondaryItemRequired() // второй предмет не нужен
                                        && rightStack.isEmpty() // и второго предмета нет
                        )
                ) {
                    ItemStack newApple = new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, 1);
                    if (leftStack.hasCustomName())
                        newApple.setCustomName(leftStack.getName());

                    this.output.setStack(0, newApple);
                    this.levelCost.set(NotchifyMod.getConfig().getAppleEnchantmentCost());
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
