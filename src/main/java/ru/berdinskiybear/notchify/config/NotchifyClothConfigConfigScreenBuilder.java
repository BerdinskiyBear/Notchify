package ru.berdinskiybear.notchify.config;

import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.DropdownBoxEntry;
import me.shedaniel.clothconfig2.impl.builders.DropdownMenuBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.Level;
import ru.berdinskiybear.notchify.NotchifyMod;
import java.util.*;
import java.util.stream.Collectors;

public class NotchifyClothConfigConfigScreenBuilder {
    private static NotchifyConfig temporaryConfig;

    public static Screen createConfigScreenBuilder(Screen parentScreen) {
        NotchifyConfig defaultConfig = new NotchifyConfig();

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parentScreen)
                .setTitle(new TranslatableText("notchify.configScreen.title"))
                .setSavingRunnable(() -> {
                    NotchifyMod.setCurrentConfig(temporaryConfig);
                    temporaryConfig = null;
                    NotchifyMod.saveConfigFile();
                })
                .setShouldListSmoothScroll(false)
                .setShouldTabsSmoothScroll(false)
                .setAfterInitConsumer(screenIllNeverUse -> temporaryConfig = new NotchifyConfig());

        ConfigCategory enchanting = builder.getOrCreateCategory(new TranslatableText("notchify.configScreen.category.enchanting"));
        ConfigCategory anvil = builder.getOrCreateCategory(new TranslatableText("notchify.configScreen.category.anvil"));
        ConfigCategory grinding = builder.getOrCreateCategory(new TranslatableText("notchify.configScreen.category.grinding"));
        ConfigCategory general = builder.getOrCreateCategory(new TranslatableText("notchify.configScreen.category.general"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        anvil.addEntry(entryBuilder
                .startBooleanToggle(new TranslatableText("notchify.configScreen.setting.anvilEnabled.name"), NotchifyMod.getCurrentConfig().isAnvilEnabled())
                .setDefaultValue(defaultConfig.isAnvilEnabled())
                .setSaveConsumer(value -> temporaryConfig.setAnvilEnabled(value))
                .build()
        ).addEntry(entryBuilder.startTextDescription(new TranslatableText("notchify.configScreen.setting.anvilEnabled.description").formatted(Formatting.GRAY)).build());

        enchanting.addEntry(entryBuilder
                .startBooleanToggle(new TranslatableText("notchify.configScreen.setting.enchantingTableEnabled.name"), NotchifyMod.getCurrentConfig().isEnchantingTableEnabled())
                .setDefaultValue(defaultConfig.isEnchantingTableEnabled())
                .setSaveConsumer(value -> temporaryConfig.setEnchantingTableEnabled(value))
                .build()
        ).addEntry(entryBuilder.startTextDescription(new TranslatableText("notchify.configScreen.setting.enchantingTableEnabled.description").formatted(Formatting.GRAY)).build());

        general.addEntry(entryBuilder
                .startIntField(new TranslatableText("notchify.configScreen.setting.appleEnchantmentCost.name"), NotchifyMod.getCurrentConfig().getAppleEnchantmentCost())
                .setMin(1)
                .setMax(255)
                .setDefaultValue(defaultConfig.getAppleEnchantmentCost())
                .setSaveConsumer(value -> temporaryConfig.setAppleEnchantmentCost(value))
                .build()
        ).addEntry(entryBuilder.startTextDescription(new TranslatableText("notchify.configScreen.setting.appleEnchantmentCost.description").formatted(Formatting.GRAY)).build());

        enchanting.addEntry(entryBuilder
                .startBooleanToggle(new TranslatableText("notchify.configScreen.setting.creativePlayerAlwaysSuccessful.name"), NotchifyMod.getCurrentConfig().isCreativePlayerAlwaysSuccessful())
                .setDefaultValue(defaultConfig.isCreativePlayerAlwaysSuccessful())
                .setSaveConsumer(value -> temporaryConfig.setCreativePlayerAlwaysSuccessful(value))
                .build()
        ).addEntry(entryBuilder.startTextDescription(new TranslatableText("notchify.configScreen.setting.creativePlayerAlwaysSuccessful.description").formatted(Formatting.GRAY)).build());

        enchanting.addEntry(entryBuilder
                .startBooleanToggle(new TranslatableText("notchify.configScreen.setting.survivalPlayerAlwaysSuccessful.name"), NotchifyMod.getCurrentConfig().isSurvivalPlayerAlwaysSuccessful())
                .setDefaultValue(defaultConfig.isSurvivalPlayerAlwaysSuccessful())
                .setSaveConsumer(value -> temporaryConfig.setSurvivalPlayerAlwaysSuccessful(value))
                .build()
        ).addEntry(entryBuilder.startTextDescription(new TranslatableText("notchify.configScreen.setting.survivalPlayerAlwaysSuccessful.description").formatted(Formatting.GRAY)).build());

        enchanting.addEntry(entryBuilder
                .startDoubleField(new TranslatableText("notchify.configScreen.setting.enchantingChanceModifier.name"), NotchifyMod.getCurrentConfig().getEnchantingChanceModifier())
                .setMin(0.0D)
                .setDefaultValue(defaultConfig.getEnchantingChanceModifier())
                .setSaveConsumer(value -> temporaryConfig.setEnchantingChanceModifier(value))
                .build()
        ).addEntry(entryBuilder.startTextDescription(new TranslatableText("notchify.configScreen.setting.enchantingChanceModifier.description").formatted(Formatting.GRAY)).build());

        enchanting.addEntry(entryBuilder
                .startBooleanToggle(new TranslatableText("notchify.configScreen.setting.goldenAppleDisappearing.name"), NotchifyMod.getCurrentConfig().isGoldenAppleDisappearing())
                .setDefaultValue(defaultConfig.isGoldenAppleDisappearing())
                .setSaveConsumer(value -> temporaryConfig.setGoldenAppleDisappearing(value))
                .build()
        ).addEntry(entryBuilder.startTextDescription(new TranslatableText("notchify.configScreen.setting.goldenAppleDisappearing.description").formatted(Formatting.GRAY)).build());

        enchanting.addEntry(entryBuilder
                .startDoubleField(new TranslatableText("notchify.configScreen.setting.vanishingChance.name"), NotchifyMod.getCurrentConfig().getVanishingChance())
                .setMin(0.0D)
                .setMax(1.0D)
                .setDefaultValue(defaultConfig.getVanishingChance())
                .setSaveConsumer(value -> temporaryConfig.setVanishingChance(value))
                .build()
        ).addEntry(entryBuilder.startTextDescription(new TranslatableText("notchify.configScreen.setting.vanishingChance.description").formatted(Formatting.GRAY)).build());

        enchanting.addEntry(entryBuilder
                .startBooleanToggle(new TranslatableText("notchify.configScreen.setting.appleBecomingCursed.name"), NotchifyMod.getCurrentConfig().isAppleBecomingCursed())
                .setDefaultValue(defaultConfig.isAppleBecomingCursed())
                .setSaveConsumer(value -> temporaryConfig.setAppleBecomingCursed(value))
                .build()
        ).addEntry(entryBuilder.startTextDescription(new TranslatableText("notchify.configScreen.setting.appleBecomingCursed.description").formatted(Formatting.GRAY)).build());

        enchanting.addEntry(entryBuilder
                .startDoubleField(new TranslatableText("notchify.configScreen.setting.curseChance.name"), NotchifyMod.getCurrentConfig().getCurseChance())
                .setMin(0.0D)
                .setMax(1.0D)
                .setDefaultValue(defaultConfig.getCurseChance())
                .setSaveConsumer(value -> temporaryConfig.setCurseChance(value))
                .build()
        ).addEntry(entryBuilder.startTextDescription(new TranslatableText("notchify.configScreen.setting.curseChance.description").formatted(Formatting.GRAY)).build());

        anvil.addEntry(entryBuilder
                .startBooleanToggle(new TranslatableText("notchify.configScreen.setting.secondaryItemRequired.name"), NotchifyMod.getCurrentConfig().isSecondaryItemRequired())
                .setDefaultValue(defaultConfig.isSecondaryItemRequired())
                .setSaveConsumer(value -> temporaryConfig.setSecondaryItemRequired(value))
                .build()
        ).addEntry(entryBuilder.startTextDescription(new TranslatableText("notchify.configScreen.setting.secondaryItemRequired.description").formatted(Formatting.GRAY)).build());


        anvil.addEntry(entryBuilder
                .startDropdownMenu(new TranslatableText("notchify.configScreen.setting.secondaryItem.name"), DropdownMenuBuilder.TopCellElementBuilder.ofItemObject(NotchifyMod.getCurrentConfig().getSecondaryItem()), DropdownMenuBuilder.CellCreatorBuilder.ofItemObject())
                .setDefaultValue(defaultConfig.getSecondaryItem())
                .setSelections(Registry.ITEM.stream().sorted(Comparator.comparing(Item::toString)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll))
                .setSaveConsumer(value -> temporaryConfig.setSecondaryItem(value))
                .build()
        ).addEntry(entryBuilder.startTextDescription(new TranslatableText("notchify.configScreen.setting.secondaryItem.description").formatted(Formatting.GRAY)).build());

        anvil.addEntry(entryBuilder
                .startIntField(new TranslatableText("notchify.configScreen.setting.secondaryItemAmount.name"), NotchifyMod.getCurrentConfig().getSecondaryItemAmount())
                .setMin(1)
                .setMax(64)
                .setDefaultValue(defaultConfig.getSecondaryItemAmount())
                .setSaveConsumer(value -> temporaryConfig.setSecondaryItemAmount(value))
                .build()
        ).addEntry(entryBuilder.startTextDescription(new TranslatableText("notchify.configScreen.setting.secondaryItemAmount.description").formatted(Formatting.GRAY)).build());

        anvil.addEntry(entryBuilder
                .startBooleanToggle(new TranslatableText("notchify.configScreen.setting.secondaryItemNbtEnabled.name"), NotchifyMod.getCurrentConfig().isSecondaryItemNbtEnabled())
                .setDefaultValue(defaultConfig.isSecondaryItemNbtEnabled())
                .setSaveConsumer(value -> temporaryConfig.setSecondaryItemNbtEnabled(value))
                .build()
        ).addEntry(entryBuilder.startTextDescription(new TranslatableText("notchify.configScreen.setting.secondaryItemNbtEnabled.description").formatted(Formatting.GRAY)).build());

        anvil.addEntry(entryBuilder
                .startTextField(new TranslatableText("notchify.configScreen.setting.secondaryItemNbt.name"), NotchifyMod.getCurrentConfig().getSecondaryItemNbt().toString())
                .setDefaultValue(defaultConfig.getSecondaryItemNbt().toString())
                .setErrorSupplier((value) -> {
                    try {
                        StringNbtReader.parse(value);
                    } catch (CommandSyntaxException e) {
                        return Optional.of(Text.of(e.getMessage()));
                    }
                    return Optional.empty();
                })
                .setSaveConsumer(value -> {
                    try {
                        temporaryConfig.setSecondaryItemNbt(StringNbtReader.parse(value));
                    } catch (CommandSyntaxException e) {
                        NotchifyMod.log(Level.ERROR, "HOW!?");
                        NotchifyMod.log(Level.ERROR, e.getMessage());
                        temporaryConfig.setSecondaryItemNbt(new CompoundTag());
                    }
                })
                .build()
        ).addEntry(entryBuilder.startTextDescription(new TranslatableText("notchify.configScreen.setting.secondaryItemNbt.description").formatted(Formatting.GRAY)).build());

        grinding.addEntry(entryBuilder
                .startBooleanToggle(new TranslatableText("notchify.configScreen.setting.grindingEnabled.name"), NotchifyMod.getCurrentConfig().isGrindingEnabled())
                .setDefaultValue(defaultConfig.isGrindingEnabled())
                .setSaveConsumer(value -> temporaryConfig.setGrindingEnabled(value))
                .build()
        ).addEntry(entryBuilder.startTextDescription(new TranslatableText("notchify.configScreen.setting.grindingEnabled.description").formatted(Formatting.GRAY)).build());

        grinding.addEntry(entryBuilder
                .startDoubleField(new TranslatableText("notchify.configScreen.setting.grindingXpMultiplier.name"), NotchifyMod.getCurrentConfig().getGrindingXpMultiplier())
                .setMin(0.0D)
                .setMax(2.0D)
                .setDefaultValue(defaultConfig.getGrindingXpMultiplier())
                .setSaveConsumer(value -> temporaryConfig.setGrindingXpMultiplier(value))
                .build()
        ).addEntry(entryBuilder.startTextDescription(new TranslatableText("notchify.configScreen.setting.grindingXpMultiplier.description").formatted(Formatting.GRAY)).build());

        general.addEntry(entryBuilder
                .startBooleanToggle(new TranslatableText("notchify.configScreen.setting.cursedApplePoisonous.name"), NotchifyMod.getCurrentConfig().isCursedApplePoisonous())
                .setDefaultValue(defaultConfig.isCursedApplePoisonous())
                .setSaveConsumer(value -> temporaryConfig.setCursedApplePoisonous(value))
                .build()
        ).addEntry(entryBuilder.startTextDescription(new TranslatableText("notchify.configScreen.setting.cursedApplePoisonous.description").formatted(Formatting.GRAY)).build());

        general
                .addEntry(entryBuilder.startTextDescription(new TranslatableText("notchify.configScreen.setting.statusEffectInstances.description").formatted(Formatting.GRAY)).build())
                .addEntry(
                        new NotchifyNestedListListEntry(
                                new TranslatableText("notchify.configScreen.setting.statusEffectInstances.name"),
                                NotchifyConfig.StatusEffectInstanceRepresentation.representationsFrom(NotchifyMod.getCurrentConfig().getStatusEffectInstances()),
                                false,
                                Optional::empty,
                                (List<NotchifyConfig.StatusEffectInstanceRepresentation> list) -> temporaryConfig.setStatusEffectInstances(NotchifyConfig.StatusEffectInstanceRepresentation.instancesFrom(list)),
                                () -> NotchifyConfig.StatusEffectInstanceRepresentation.representationsFrom(defaultConfig.getStatusEffectInstances()),
                                entryBuilder.getResetButtonKey(),
                                true,
                                false,
                                (NotchifyConfig.StatusEffectInstanceRepresentation representation, NotchifyNestedListListEntry nestedListListEntry) -> {
                                    if (representation == null)
                                        representation = new NotchifyConfig.StatusEffectInstanceRepresentation();
                                    Optional<StatusEffectInstance> optionalStatusEffectInstance = representation.createStatusEffectInstance();
                                    if (!optionalStatusEffectInstance.isPresent())
                                        throw new IllegalStateException();
                                    else
                                        //noinspection ConstantConditions
                                        return new NotchifyMultiElementListEntry(new TranslatableText("notchify.configScreen.setting.statusEffectInstances.effect.name"),
                                                representation,
                                                Lists.newArrayList(
                                                        entryBuilder
                                                                .startDropdownMenu(new TranslatableText("notchify.configScreen.setting.statusEffectInstances.effect.id.name"),
                                                                        new DropdownBoxEntry.DefaultSelectionTopCellElement<StatusEffect>(optionalStatusEffectInstance.get().getEffectType(),
                                                                                NotchifyClothConfigConfigScreenBuilder::stringToStatusEffect,
                                                                                NotchifyClothConfigConfigScreenBuilder::statusEffectToText
                                                                        ) {
                                                                            @Override
                                                                            public void render(MatrixStack matrices, int mouseX, int mouseY, int x, int y, int width, int height, float delta) {
                                                                                textFieldWidget.x = x + 4;
                                                                                textFieldWidget.y = y + 6;
                                                                                textFieldWidget.setWidth(width - 4 - 20);
                                                                                textFieldWidget.setEditable(getParent().isEditable());
                                                                                textFieldWidget.setEditableColor(getPreferredTextColor());
                                                                                textFieldWidget.render(matrices, mouseX, mouseY, delta);
                                                                                if (hasConfigError()) {
                                                                                    MinecraftClient.getInstance().getItemRenderer().renderGuiItemIcon(Items.BARRIER.getDefaultStack(), x + width - 18, y + 2);
                                                                                } else {
                                                                                    Sprite sprite = MinecraftClient.getInstance().getStatusEffectSpriteManager().getSprite(getValue());
                                                                                    MinecraftClient.getInstance().getTextureManager().bindTexture(sprite.getAtlas().getId());
                                                                                    drawSprite(matrices, x + width - 19, y + 1, 200, 18, 18, sprite);
                                                                                }
                                                                            }
                                                                        },
                                                                        new DropdownBoxEntry.DefaultSelectionCellCreator<StatusEffect>(NotchifyClothConfigConfigScreenBuilder::statusEffectToText) {
                                                                            @Override
                                                                            public DropdownBoxEntry.SelectionCellElement<StatusEffect> create(StatusEffect selection) {
                                                                                return new DropdownBoxEntry.DefaultSelectionCellElement<StatusEffect>(selection, this.toTextFunction) {
                                                                                    @Override
                                                                                    public void render(MatrixStack matrices, int mouseX, int mouseY, int x, int y, int width, int height, float delta) {
                                                                                        this.rendering = true;
                                                                                        this.x = x;
                                                                                        this.y = y;
                                                                                        this.width = width;
                                                                                        this.height = height;
                                                                                        boolean b = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
                                                                                        if (b)
                                                                                            fill(matrices, x + 1, y + 1, x + width - 1, y + height - 1, -15132391);
                                                                                        MinecraftClient.getInstance().textRenderer.drawWithShadow(matrices, this.toTextFunction.apply(this.r).asOrderedText(), x + 6 + 18, y + 6, b ? 16777215 : 8947848);
                                                                                        Sprite sprite = MinecraftClient.getInstance().getStatusEffectSpriteManager().getSprite(selection);
                                                                                        MinecraftClient.getInstance().getTextureManager().bindTexture(sprite.getAtlas().getId());
                                                                                        drawSprite(matrices, x + 3, y + 1, 200, 18, 18, sprite);
                                                                                    }

                                                                                    @Override
                                                                                    public void dontRender(MatrixStack matrixStack, float delta) {
                                                                                        this.rendering = false;
                                                                                    }
                                                                                };
                                                                            }

                                                                            @Override
                                                                            public int getCellHeight() {
                                                                                return 20;
                                                                            }

                                                                            @Override
                                                                            public int getCellWidth() {
                                                                                return 146;
                                                                            }

                                                                            @Override
                                                                            public int getDropBoxMaxHeight() {
                                                                                return getCellHeight() * 7;
                                                                            }
                                                                        }
                                                                )
                                                                .setTooltip(new TranslatableText("notchify.configScreen.setting.statusEffectInstances.effect.id.description"))
                                                                .setDefaultValue(Registry.STATUS_EFFECT.get(new Identifier(new NotchifyConfig.StatusEffectInstanceRepresentation().getStatusEffectId())))
                                                                .setSelections(Registry.STATUS_EFFECT.stream().sorted(Comparator.comparing(Registry.STATUS_EFFECT::getId)).collect(Collectors.toCollection(LinkedHashSet::new)))
                                                                .setSaveConsumer(representation::setStatusEffect)
                                                                .build(),
                                                        entryBuilder
                                                                .startIntField(new TranslatableText("notchify.configScreen.setting.statusEffectInstances.effect.duration.name"), representation.getDuration())
                                                                .setTooltip(new TranslatableText("notchify.configScreen.setting.statusEffectInstances.effect.duration.description"))
                                                                .setDefaultValue(new NotchifyConfig.StatusEffectInstanceRepresentation().getDuration())
                                                                .setSaveConsumer(representation::setDuration)
                                                                .build(),
                                                        entryBuilder
                                                                .startIntField(new TranslatableText("notchify.configScreen.setting.statusEffectInstances.effect.amplifier.name"), representation.getAmplifier())
                                                                .setTooltip(new TranslatableText("notchify.configScreen.setting.statusEffectInstances.effect.amplifier.description"))
                                                                .setDefaultValue(new NotchifyConfig.StatusEffectInstanceRepresentation().getAmplifier())
                                                                .setSaveConsumer(representation::setAmplifier)
                                                                .build()
                                                ),
                                                true
                                        );
                                }
                        )
                );

        return builder.build();
    }

    private static Text statusEffectToText(StatusEffect effect) {
        return Text.of(Objects.requireNonNull(Registry.STATUS_EFFECT.getId(effect)).toString());
    }

    private static StatusEffect stringToStatusEffect(String str) {
        return Registry.STATUS_EFFECT.get(new Identifier(str));
    }
}
