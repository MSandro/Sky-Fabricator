package com.msandro.skyfabricator.compat.rei;

import com.msandro.skyfabricator.recipe.KilnRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.*;

/*public class KilnDisplay extends BasicDisplay {
    public KilnDisplay(KilnRecipe recipe) {
        this(
                Arrays.asList(
                        EntryIngredients.ofIngredient(recipe.base),
                        EntryIngredients.ofIngredient(recipe.addition)
                ),
                Collections.singletonList(EntryIngredients.of(recipe.getResultItem())),
                Optional.ofNullable(recipe.getId())
        );
    }

    public KilnDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, Optional<ResourceLocation> location) {
        super(inputs, outputs, location);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {

    }

    public static BasicDisplay.Serializer<KilnDisplay> serializer() {
        return BasicDisplay.Serializer.ofSimple(KilnDisplay::new);
    }
}*/
