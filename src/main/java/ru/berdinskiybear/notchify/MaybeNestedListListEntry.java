package ru.berdinskiybear.notchify;

import com.google.common.collect.Lists;
import me.shedaniel.clothconfig2.api.AbstractConfigEntry;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
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
import ru.berdinskiybear.notchify.MaybeNestedListListEntry.MaybeNestedListCell;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @param <T>     the configuration object type
 * @param <INNER> the type of the inner config entry
 */
@Environment(EnvType.CLIENT)
public final class MaybeNestedListListEntry<T, INNER extends AbstractConfigListEntry<T>> extends AbstractListListEntry<T, MaybeNestedListCell<T, INNER>, MaybeNestedListListEntry<T, INNER>> {
    private final List<ReferenceProvider<?>> referencableEntries = Lists.newArrayList();

    public MaybeNestedListListEntry(Text fieldName,
                                    List<T> value,
                                    boolean defaultExpanded,
                                    Supplier<Optional<Text[]>> tooltipSupplier,
                                    Consumer<List<T>> saveConsumerArg,
                                    Supplier<List<T>> defaultValue,
                                    Text resetButtonKey,
                                    boolean deleteButtonEnabled,
                                    boolean insertInFront,
                                    BiFunction<T, MaybeNestedListListEntry<T, INNER>, INNER> createNewCell) {
        super(fieldName, value, defaultExpanded, tooltipSupplier, saveConsumerArg, defaultValue, resetButtonKey, false, deleteButtonEnabled, insertInFront, (t, nestedListListEntry) -> new MaybeNestedListCell<>(t, nestedListListEntry, createNewCell.apply(t, nestedListListEntry)));
        for (MaybeNestedListCell<T, INNER> cell : cells) {
            referencableEntries.add(cell.nestedEntry);
        }
        setReferenceProviderEntries(referencableEntries);
    }

    @Override
    public MaybeNestedListListEntry<T, INNER> self() {
        return this;
    }

    @Override
    public void lateRender(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (this.expanded)
            this.cells.forEach(cell -> cell.lateRender(matrices, mouseX, mouseY, delta));
    }

    @Override
    public void save() {
        for (MaybeNestedListCell<T, INNER> cell : cells)
            cell.nestedEntry.save();
        super.save();
    }

    /**
     * @param <T> the configuration object type
     * @see MaybeNestedListListEntry
     */
    public static class MaybeNestedListCell<T, INNER extends AbstractConfigListEntry<T>> extends AbstractListCell<T, MaybeNestedListCell<T, INNER>, MaybeNestedListListEntry<T, INNER>> implements ReferenceProvider<T> {
        private final INNER nestedEntry;

        public MaybeNestedListCell(@Nullable T value, MaybeNestedListListEntry<T, INNER> listListEntry, INNER nestedEntry) {
            super(value, listListEntry);
            this.nestedEntry = nestedEntry;
        }

        @Override
        @NotNull
        public AbstractConfigEntry<T> provideReferenceEntry() {
            return nestedEntry;
        }

        @Override
        public T getValue() {
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
    }
}
