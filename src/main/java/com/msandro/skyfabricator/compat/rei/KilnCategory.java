package com.msandro.skyfabricator.compat.rei;

import com.google.common.collect.Lists;
import com.msandro.skyfabricator.ModRegistry;
import com.msandro.skyfabricator.Skyfabricator;
import com.msandro.skyfabricator.recipe.KilnRecipe;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.block.Blocks;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.mutable.MutableInt;


import java.util.ArrayList;
import java.util.List;

/*public class KilnCategory implements DisplayCategory<KilnDisplay> {
    @Override
    public CategoryIdentifier<? extends KilnDisplay> getCategoryIdentifier() {
        return BuiltinPlugin.SMITHING;
    }

    @Override
    public Component getTitle() {
        return new TranslatableComponent("category.rei.smithing");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModRegistry.KILN_BLOCK);
    }

    @Override
    public List<Widget> setupDisplay(KilnDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 31, bounds.getCenterY() - 13);
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 27, startPoint.y + 4)));
        widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 61, startPoint.y + 5)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 4 - 22, startPoint.y + 5)).entries(display.getInputEntries().get(0)).markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 4, startPoint.y + 5)).entries(display.getInputEntries().get(1)).markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 61, startPoint.y + 5)).entries(display.getOutputEntries().get(0)).disableBackground().markOutput());
        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 36;
    }
}*/
