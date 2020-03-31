package ru.berdinskiybear.notchify.mixins;

import net.minecraft.advancement.criterion.Criterions;
import net.minecraft.block.Blocks;
import net.minecraft.container.*;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.berdinskiybear.notchify.NotchificationStuffs;
import ru.berdinskiybear.notchify.NotchifyMod;

import java.util.Random;

@Mixin(EnchantingTableContainer.class)
public abstract class NotchifyUsingEnchantingTable extends Container {

    protected NotchifyUsingEnchantingTable(ContainerType<?> type, int syncId) {
        super(type, syncId);
    }

    @Shadow @Final private Inventory inventory;
    @Shadow @Final private BlockContext context;
    @Shadow @Final private Random random;
    @Shadow @Final private Property seed;
    @Shadow @Final public int[] enchantmentPower;
    @Shadow @Final public int[] enchantmentId;
    @Shadow @Final public int[] enchantmentLevel;
    @Shadow public abstract void onContentChanged(Inventory inventory);

    @Inject(method = "onContentChanged", at = @At(value = "HEAD"), cancellable = true)
    public void notchificationCalculations(Inventory inv, CallbackInfo info) {
        if (NotchifyMod.getConfig().isEnchantingTableEnabled()) {
            if (inv == this.inventory) {
                ItemStack enchantingStack = inv.getInvStack(0);
                if (enchantingStack.getItem() == Items.GOLDEN_APPLE && enchantingStack.getCount() == 1) {
                    this.context.run((world, blockPos) -> {
                        int bookshelves = 0;

                        for (int i = -1; i <= 1; ++i) {
                            for (int j = -1; j <= 1; ++j) {
                                if (!(i == 0 && j == 0) && world.isAir(blockPos.add(j, 0, i)) && world.isAir(blockPos.add(j, 1, i))) {
                                    if (world.getBlockState(blockPos.add(j * 2, 0, i * 2)).getBlock() == Blocks.BOOKSHELF) {
                                        ++bookshelves;
                                    }

                                    if (world.getBlockState(blockPos.add(j * 2, 1, i * 2)).getBlock() == Blocks.BOOKSHELF) {
                                        ++bookshelves;
                                    }

                                    if (j != 0 && i != 0) {
                                        if (world.getBlockState(blockPos.add(j * 2, 0, i)).getBlock() == Blocks.BOOKSHELF) {
                                            ++bookshelves;
                                        }

                                        if (world.getBlockState(blockPos.add(j * 2, 1, i)).getBlock() == Blocks.BOOKSHELF) {
                                            ++bookshelves;
                                        }

                                        if (world.getBlockState(blockPos.add(j, 0, i * 2)).getBlock() == Blocks.BOOKSHELF) {
                                            ++bookshelves;
                                        }

                                        if (world.getBlockState(blockPos.add(j, 1, i * 2)).getBlock() == Blocks.BOOKSHELF) {
                                            ++bookshelves;
                                        }
                                    }
                                }
                            }
                        }

                        this.random.setSeed(this.seed.get());

                        for (int i = 0; i < 3; i++) {
                            this.enchantmentPower[i] = NotchificationStuffs.calculateEnchantmentPower(this.random, i, bookshelves);
                            this.enchantmentId[i] = -1;
                            this.enchantmentLevel[i] = -1;
                            if (this.enchantmentPower[i] < i + 1) this.enchantmentPower[i] = 0;
                        }

                        for (int i = 0; i < 3; i++)
                            if (this.enchantmentPower[i] > 0) {
                                this.enchantmentId[i] = Registry.ENCHANTMENT.getRawId(Enchantments.EFFICIENCY);
                                this.enchantmentLevel[i] = i + 1;
                            }

                        this.sendContentUpdates();
                    });

                    info.cancel();
                }
            }
        }
    }

    @Inject(method = "onButtonClick", at = @At(value = "HEAD"), cancellable = true)
    public void notchification(PlayerEntity player, int id, CallbackInfoReturnable<Boolean> info) {
        if (NotchifyMod.getConfig().isEnchantingTableEnabled()) {
            ItemStack enchantingStack = this.inventory.getInvStack(0);

            if (enchantingStack.getItem() == Items.GOLDEN_APPLE && enchantingStack.getCount() == 1) {
                ItemStack lapisStack = this.inventory.getInvStack(1);
                int button = id + 1;

                if ((!lapisStack.isEmpty() && lapisStack.getCount() >= button && player.experienceLevel > button && player.experienceLevel > this.enchantmentPower[id]) || player.abilities.creativeMode) {
                    this.context.run((world, blockPos) -> {
                        this.random.setSeed((long) (this.seed.get() + id + 3));
                        float playerChance = ((float) (enchantmentPower[id]) * NotchifyMod.getConfig().getEnchantingChanceModifier()) / ((float) (NotchifyMod.getConfig().getEGAppleEnchantmentCost()) * 10.0F);
                        float choice = this.random.nextFloat();

                        //NotchifyMod.log(playerChance * 100.0F + "%");

                        player.applyEnchantmentCosts(null, button);

                        if (!player.abilities.creativeMode) {
                            lapisStack.decrement(button);
                            if (lapisStack.isEmpty())
                                this.inventory.setInvStack(1, ItemStack.EMPTY);
                        }

                        if ((choice < playerChance || (player.abilities.creativeMode && NotchifyMod.getConfig().isCreativePlayerAlwaysSuccessful())) || NotchifyMod.getConfig().isSurvivalPlayerAlwaysSuccessful()) {
                            ItemStack newApple = new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, 1);

                            if (enchantingStack.hasCustomName())
                                newApple.setCustomName(enchantingStack.getName());

                            this.inventory.setInvStack(0, newApple);

                            player.incrementStat(Stats.ENCHANT_ITEM);

                            if (player instanceof ServerPlayerEntity)
                                Criterions.ENCHANTED_ITEM.trigger((ServerPlayerEntity) player, enchantingStack, button);

                            world.playSound(null, blockPos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.1F + 0.9F);
                        } else {
                            world.playSound(null, blockPos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.1F + 2.0F);
                        }

                        this.inventory.markDirty();
                        this.seed.set(player.getEnchantmentTableSeed());
                        this.onContentChanged(this.inventory);
                    });

                    info.setReturnValue(true);
                } else {
                    info.setReturnValue(false);
                }
            }
        }
    }
}
