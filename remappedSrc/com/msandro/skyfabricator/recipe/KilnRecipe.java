package com.msandro.skyfabricator.recipe;

import com.google.common.collect.ImmutableMap;
import com.msandro.skyfabricator.Skyfabricator;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import com.msandro.skyfabricator.block.kiln.*;

import java.util.List;
import java.util.stream.Collectors;

public class KilnRecipe implements Recipe<Inventory> {
    private final Identifier id;

    private final List<Ingredient> inputs;
    private final ItemStack output;

    public KilnRecipe(Identifier id, List<Ingredient> inputs, ItemStack output) {
        this.id = id;
        this.inputs = inputs;
        this.output = output;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        RecipeMatcher recipeMatcher = new RecipeMatcher();
        int nonEmptyStacks = 0;

        for (int j = 0; j < inventory.size() - 2; ++j) {
            ItemStack itemStack = inventory.getStack(j);
            if (!itemStack.isEmpty()) {
                ++nonEmptyStacks;
                recipeMatcher.addInput(itemStack, 1);
            }
        }

        return nonEmptyStacks == this.inputs.size() && recipeMatcher.match(this, null);
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return DefaultedList.copyOf(Ingredient.EMPTY, inputs.toArray(new Ingredient[0]));
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    @Deprecated
    public ItemStack getOutput() {
        return output.copy();
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return KilnRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static record OverrideRange(int lowerBound, int upperBound) {

        public OverrideRange(int lowerBound) {
            this(lowerBound, -1);
        }

        public boolean test(int value) {
            return value >= lowerBound && (upperBound == -1 || value <= upperBound);
        }

        @Override
        public String toString() {
            var outString = String.valueOf(lowerBound);
            var chars = outString.chars().mapToObj(value -> (char) value).collect(Collectors.toList());

            if (upperBound != lowerBound) {
                if (upperBound == -1) {
                    chars.add('+');
                } else {
                    var to = " to ".chars().mapToObj(value -> (char) value).collect(Collectors.toList());
                    to.forEach(character -> chars.add(character));

                    var bound = String.valueOf(upperBound).chars().mapToObj(value -> (char) value).collect(Collectors.toList());
                    bound.forEach(character -> chars.add(character));
                }
            }

            var output = new StringBuilder();
            chars.forEach(character -> output.append(character));

            return output.toString();
        }
    }

    public static class Type implements RecipeType<KilnRecipe> {
        private Type() {}
        public static final Identifier ID = Skyfabricator.id("kiln");
        public static final Type INSTANCE = new Type();
    }
}
