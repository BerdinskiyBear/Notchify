package ru.berdinskiybear.notchify.config;

import com.google.common.collect.Lists;
import me.shedaniel.clothconfig2.api.AbstractConfigEntry;
import me.shedaniel.clothconfig2.api.ReferenceProvider;
import me.shedaniel.clothconfig2.gui.entries.AbstractListListEntry;
import me.shedaniel.clothconfig2.gui.widget.DynamicEntryListWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Element;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.berdinskiybear.notchify.config.NotchifyNestedListListEntry.NotchifyNestedListCell;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public final class NotchifyNestedListListEntry extends AbstractListListEntry<NotchifyConfig.StatusEffectInstanceRepresentation, NotchifyNestedListCell, NotchifyNestedListListEntry> {
    private final List<ReferenceProvider<?>> referencableEntries = Lists.newArrayList();

    public NotchifyNestedListListEntry(Text fieldName, List<NotchifyConfig.StatusEffectInstanceRepresentation> value, boolean defaultExpanded, Supplier<Optional<Text[]>> tooltipSupplier, Consumer<List<NotchifyConfig.StatusEffectInstanceRepresentation>> saveConsumerArg, Supplier<List<NotchifyConfig.StatusEffectInstanceRepresentation>> defaultValue, Text resetButtonKey, boolean deleteButtonEnabled, boolean insertInFront, BiFunction<NotchifyConfig.StatusEffectInstanceRepresentation, NotchifyNestedListListEntry, NotchifyMultiElementListEntry> createNewCell) {
        super(fieldName, value, defaultExpanded, tooltipSupplier, saveConsumerArg, defaultValue, resetButtonKey, false, deleteButtonEnabled, insertInFront, (t, nestedListListEntry) -> new NotchifyNestedListCell(t, nestedListListEntry, createNewCell.apply(t, nestedListListEntry)));
        for (NotchifyNestedListCell cell : cells) {
            referencableEntries.add(cell.nestedEntry);
        }
        setReferenceProviderEntries(referencableEntries);
    }

    @Override
    public NotchifyNestedListListEntry self() {
        return this;
    }

    @Override
    public void lateRender(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (this.expanded)
            this.cells.forEach(cell -> cell.lateRender(matrices, mouseX, mouseY, delta));
    }

    @Override
    public void save() {
        for (NotchifyNestedListCell cell : cells)
            cell.save();
        super.save();
    }

    public static class NotchifyNestedListCell extends AbstractListCell<NotchifyConfig.StatusEffectInstanceRepresentation, NotchifyNestedListCell, NotchifyNestedListListEntry> implements ReferenceProvider<NotchifyConfig.StatusEffectInstanceRepresentation> {
        private final NotchifyMultiElementListEntry nestedEntry;

        public NotchifyNestedListCell(@Nullable NotchifyConfig.StatusEffectInstanceRepresentation value, NotchifyNestedListListEntry listListEntry, NotchifyMultiElementListEntry nestedEntry) {
            super(value, listListEntry);
            this.nestedEntry = nestedEntry;
        }

        @Override
        @NotNull
        public AbstractConfigEntry<NotchifyConfig.StatusEffectInstanceRepresentation> provideReferenceEntry() {
            return nestedEntry;
        }

        @Override
        public NotchifyConfig.StatusEffectInstanceRepresentation getValue() {
            return nestedEntry.getValue();
        }

        @Override
        public Optional<Text> getError() {
            return nestedEntry.getError();
        }

        @Override
        public int getCellHeight() {
            return nestedEntry.getItemHeight();
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean isSelected, float delta) {
            nestedEntry.setParent((DynamicEntryListWidget) listListEntry.getParent());
            nestedEntry.setScreen(listListEntry.getConfigScreen());
            nestedEntry.render(matrices, index, y, x, entryWidth, entryHeight, mouseX, mouseY, isSelected, delta);
        }

        @Override
        public List<? extends Element> children() {
            return Collections.singletonList(nestedEntry);
        }

        @Override
        public boolean isRequiresRestart() {
            return nestedEntry.isRequiresRestart();
        }

        @Override
        public void updateSelected(boolean isSelected) {
            this.nestedEntry.updateSelected(isSelected);
        }

        @Override
        public boolean isEdited() {
            return super.isEdited() || nestedEntry.isEdited();
        }

        @Override
        public void onAdd() {
            super.onAdd();
            listListEntry.referencableEntries.add(nestedEntry);
            listListEntry.requestReferenceRebuilding();
        }

        @Override
        public void onDelete() {
            super.onDelete();
            listListEntry.referencableEntries.remove(nestedEntry);
            listListEntry.requestReferenceRebuilding();
        }

        public void lateRender(MatrixStack matrices, int mouseX, int mouseY, float delta) {
            this.nestedEntry.lateRender(matrices, mouseX, mouseY, delta);
        }

        public void save() {
            this.nestedEntry.save();
        }
    }
}
