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
                .setTitle(Text.of("Notchify Mod Config"))
                .setSavingRunnable(() -> {
                    NotchifyMod.setCurrentConfig(temporaryConfig);
                    temporaryConfig = null;
                    NotchifyMod.saveConfigFile();
                })
                .setShouldListSmoothScroll(false)
                .setShouldTabsSmoothScroll(false)
                .setAfterInitConsumer(screenIllNeverUse -> temporaryConfig = new NotchifyConfig());

        ConfigCategory general = builder.getOrCreateCategory(Text.of("General"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        general.addEntry(entryBuilder
                .startBooleanToggle(Text.of("Notchification via anvil"), NotchifyMod.getCurrentConfig().isAnvilEnabled())
                .setDefaultValue(defaultConfig.isAnvilEnabled())
                .setSaveConsumer(value -> temporaryConfig.setAnvilEnabled(value))
                .build()
        );
        general.addEntry(entryBuilder.startTextDescription(Text.of("Allows to create enchanted golden apples on anvil using xp and an optional item.").copy().formatted(Formatting.GRAY)).build());

        general.addEntry(entryBuilder
                .startBooleanToggle(Text.of("Notchification via enchanting table"), NotchifyMod.getCurrentConfig().isEnchantingTableEnabled())
                .setDefaultValue(defaultConfig.isEnchantingTableEnabled())
                .setSaveConsumer(value -> temporaryConfig.setEnchantingTableEnabled(value))
                .build()
        );
        general.addEntry(entryBuilder.startTextDescription(Text.of("Allows to enchant golden apples on enchanting table same way as tools, weapons and armor").copy().formatted(Formatting.GRAY)).build());

        general.addEntry(entryBuilder
                .startIntField(Text.of("Enchantment XP cost"), NotchifyMod.getCurrentConfig().getAppleEnchantmentCost())
                .setMin(1)
                .setMax(255)
                .setDefaultValue(defaultConfig.getAppleEnchantmentCost())
                .setSaveConsumer(value -> temporaryConfig.setAppleEnchantmentCost(value))
                .build()
        );
        general.addEntry(entryBuilder.startTextDescription(Text.of("Amount of XP levels that player will need to create enchanted golden apple on anvil. This value also influences chance of enchantment when using enchantment table. Be aware that setting this value over 39 will make it impossible to make enchanted golden apples due to vanilla limitation. To circumvent this you can use Anvil Fix mod.").copy().formatted(Formatting.GRAY)).build());

        general.addEntry(entryBuilder
                .startBooleanToggle(Text.of("Creative player always succeeds"), NotchifyMod.getCurrentConfig().isCreativePlayerAlwaysSuccessful())
                .setDefaultValue(defaultConfig.isCreativePlayerAlwaysSuccessful())
                .setSaveConsumer(value -> temporaryConfig.setCreativePlayerAlwaysSuccessful(value))
                .build()
        );
        general.addEntry(entryBuilder.startTextDescription(Text.of("If enabled, players in creative mode always can enchant with 100% chance.").copy().formatted(Formatting.GRAY)).build());

        general.addEntry(entryBuilder
                .startBooleanToggle(Text.of("All players succeed"), NotchifyMod.getCurrentConfig().isSurvivalPlayerAlwaysSuccessful())
                .setDefaultValue(defaultConfig.isSurvivalPlayerAlwaysSuccessful())
                .setSaveConsumer(value -> temporaryConfig.setSurvivalPlayerAlwaysSuccessful(value))
                .build()
        );
        general.addEntry(entryBuilder.startTextDescription(Text.of("If enabled, all players can enchant with 100%").copy().formatted(Formatting.GRAY)).build());

        general.addEntry(entryBuilder
                .startDoubleField(Text.of("Enchanting chance modifier"), NotchifyMod.getCurrentConfig().getEnchantingChanceModifier())
                .setMin(0.0D)
                .setDefaultValue(defaultConfig.getEnchantingChanceModifier())
                .setSaveConsumer(value -> temporaryConfig.setEnchantingChanceModifier(value))
                .build()
        );
        general.addEntry(entryBuilder.startTextDescription(Text.of("Modifies the chance of enchanting a golden apple.").copy().formatted(Formatting.GRAY)).build());

        general.addEntry(entryBuilder
                .startBooleanToggle(Text.of("Golden apple disappearing"), NotchifyMod.getCurrentConfig().isGoldenAppleDisappearing())
                .setDefaultValue(defaultConfig.isGoldenAppleDisappearing())
                .setSaveConsumer(value -> temporaryConfig.setGoldenAppleDisappearing(value))
                .build()
        );
        general.addEntry(entryBuilder.startTextDescription(Text.of("If enabled, golden apple can disappear when golden apple enchanting fails.").copy().formatted(Formatting.GRAY)).build());

        general.addEntry(entryBuilder
                .startDoubleField(Text.of("Disappearing chance"), NotchifyMod.getCurrentConfig().getVanishingChance())
                .setMin(0.0D)
                .setMax(1.0D)
                .setDefaultValue(defaultConfig.getVanishingChance())
                .setSaveConsumer(value -> temporaryConfig.setVanishingChance(value))
                .build()
        );
        general.addEntry(entryBuilder.startTextDescription(Text.of("If enchanting fails golden apple disappears with this chance.").copy().formatted(Formatting.GRAY)).build());

        general.addEntry(entryBuilder
                .startBooleanToggle(Text.of("Enchanted apple curse"), NotchifyMod.getCurrentConfig().isAppleBecomingCursed())
                .setDefaultValue(defaultConfig.isAppleBecomingCursed())
                .setSaveConsumer(value -> temporaryConfig.setAppleBecomingCursed(value))
                .build()
        );
        general.addEntry(entryBuilder.startTextDescription(Text.of("If enchanting succeeds, enchanted golden apple can come out cursed.").copy().formatted(Formatting.GRAY)).build());

        general.addEntry(entryBuilder
                .startDoubleField(Text.of("Chance of curse"), NotchifyMod.getCurrentConfig().getCurseChance())
                .setMin(0.0D)
                .setMax(1.0D)
                .setDefaultValue(defaultConfig.getCurseChance())
                .setSaveConsumer(value -> temporaryConfig.setCurseChance(value))
                .build()
        );
        general.addEntry(entryBuilder.startTextDescription(Text.of("If enchanting succeeds, enchanted golden apple can come out cursed with this chance.").copy().formatted(Formatting.GRAY)).build());

        general.addEntry(entryBuilder
                .startBooleanToggle(Text.of("Second item required"), NotchifyMod.getCurrentConfig().isSecondaryItemRequired())
                .setDefaultValue(defaultConfig.isSecondaryItemRequired())
                .setSaveConsumer(value -> temporaryConfig.setSecondaryItemRequired(value))
                .build()
        );
        general.addEntry(entryBuilder.startTextDescription(Text.of("If enabled, creating an enchanted golden apple on anvil requires second item.").copy().formatted(Formatting.GRAY)).build());


        general.addEntry(entryBuilder
                .startDropdownMenu(Text.of("Second item"), DropdownMenuBuilder.TopCellElementBuilder.ofItemObject(NotchifyMod.getCurrentConfig().getSecondaryItem()), DropdownMenuBuilder.CellCreatorBuilder.ofItemObject())
                .setDefaultValue(defaultConfig.getSecondaryItem())
                .setSelections(Registry.ITEM.stream().sorted(Comparator.comparing(Item::toString)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll))
                .setSaveConsumer(value -> temporaryConfig.setSecondaryItem(value))
                .build()
        );
        general.addEntry(entryBuilder.startTextDescription(Text.of("Item that player has to combine golden apple with to get enchanted golden apple.").copy().formatted(Formatting.GRAY)).build());

        general.addEntry(entryBuilder
                .startIntField(Text.of("Second item amount"), NotchifyMod.getCurrentConfig().getSecondaryItemAmount())
                .setMin(1)
                .setMax(64)
                .setDefaultValue(defaultConfig.getSecondaryItemAmount())
                .setSaveConsumer(value -> temporaryConfig.setSecondaryItemAmount(value))
                .build()
        );
        general.addEntry(entryBuilder.startTextDescription(Text.of("The amount of second item player has to combine to create enchanted golden apple.").copy().formatted(Formatting.GRAY)).build());

        general.addEntry(entryBuilder
                .startBooleanToggle(Text.of("Second item NBT enabled"), NotchifyMod.getCurrentConfig().isSecondaryItemNbtEnabled())
                .setDefaultValue(defaultConfig.isSecondaryItemNbtEnabled())
                .setSaveConsumer(value -> temporaryConfig.setSecondaryItemNbtEnabled(value))
                .build()
        );
        general.addEntry(entryBuilder.startTextDescription(Text.of("If enabled, NBT tags for second item can be specified").copy().formatted(Formatting.GRAY)).build());

        general.addEntry(entryBuilder
                .startTextField(Text.of("Second item NBT tags"), NotchifyMod.getCurrentConfig().getSecondaryItemNbt().toString())
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
        );
        general.addEntry(entryBuilder.startTextDescription(Text.of("NBT tags that second item has to have.").copy().formatted(Formatting.GRAY)).build());

        general.addEntry(entryBuilder
                .startBooleanToggle(Text.of("Grindstone un-enchanting"), NotchifyMod.getCurrentConfig().isGrindingEnabled())
                .setDefaultValue(defaultConfig.isGrindingEnabled())
                .setSaveConsumer(value -> temporaryConfig.setGrindingEnabled(value))
                .build()
        );
        general.addEntry(entryBuilder.startTextDescription(Text.of("If enabled, enchanted golden apple can be converted back into golden apple").copy().formatted(Formatting.GRAY)).build());

        general.addEntry(entryBuilder
                .startDoubleField(Text.of("XP multiplier"), NotchifyMod.getCurrentConfig().getGrindingXpMultiplier())
                .setMin(0.0D)
                .setMax(2.0D)
                .setDefaultValue(defaultConfig.getGrindingXpMultiplier())
                .setSaveConsumer(value -> temporaryConfig.setGrindingXpMultiplier(value))
                .build()
        );
        general.addEntry(entryBuilder.startTextDescription(Text.of("Multiplier for the amount of xp points player gets un-enchanted.").copy().formatted(Formatting.GRAY)).build());

        general.addEntry(entryBuilder
                .startBooleanToggle(Text.of("Cursed apples poisoning"), NotchifyMod.getCurrentConfig().isCursedApplePoisonous())
                .setDefaultValue(defaultConfig.isCursedApplePoisonous())
                .setSaveConsumer(value -> temporaryConfig.setCursedApplePoisonous(value))
                .build()
        );
        general.addEntry(entryBuilder.startTextDescription(Text.of("If enabled, cursed enchanted golden apples can give side effects.").copy().formatted(Formatting.GRAY)).build());

        general.addEntry(entryBuilder.startTextDescription(Text.of("Effects below will occur if cursed apple is consumed.").copy().formatted(Formatting.GRAY)).build());
        general.addEntry(new NotchifyNestedListListEntry(
                Text.of("Cursed apple poisoning effects"),
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
                        return new NotchifyMultiElementListEntry(Text.of("Effect"),
                                representation,
                                Lists.newArrayList(
                                        entryBuilder
                                                .startDropdownMenu(Text.of("Status effect ID"),
                                                        new DropdownBoxEntry.DefaultSelectionTopCellElement<StatusEffect>(optionalStatusEffectInstance.get().getEffectType(),
                                                                NotchifyClothConfigConfigScreenBuilder::stringToStatusEffect,
                                                                NotchifyClothConfigConfigScreenBuilder::statusEffectToText) {
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
                                                .setTooltip(Text.of("Status effect ID."))
                                                .setDefaultValue(Registry.STATUS_EFFECT.get(new Identifier(new NotchifyConfig.StatusEffectInstanceRepresentation().getStatusEffectId())))
                                                .setSelections(Registry.STATUS_EFFECT.stream().sorted(Comparator.comparing(Registry.STATUS_EFFECT::getId)).collect(Collectors.toCollection(LinkedHashSet::new)))
                                                .setSaveConsumer(representation::setStatusEffect)
                                                .build(),
                                        entryBuilder
                                                .startIntField(Text.of("Duration"), representation.getDuration())
                                                .setTooltip(Text.of("Duration of this effect in ticks."))
                                                .setDefaultValue(new NotchifyConfig.StatusEffectInstanceRepresentation().getDuration())
                                                .setSaveConsumer(representation::setDuration)
                                                .build(),
                                        entryBuilder
                                                .startIntField(Text.of("Amplifier"), representation.getAmplifier())
                                                .setTooltip(Text.of("Amplifier is one less than the level of this effect."))
                                                .setDefaultValue(new NotchifyConfig.StatusEffectInstanceRepresentation().getAmplifier())
                                                .setSaveConsumer(representation::setAmplifier)
                                                .build()
                                ),
                                true
                        );
                })
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
