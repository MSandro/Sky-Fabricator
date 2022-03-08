package com.msandro.skyfabricator.recipe;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.util.ArrayList;

public class KilnRecipeSerializer implements RecipeSerializer<KilnRecipe> {
    public static final KilnRecipeSerializer INSTANCE = new KilnRecipeSerializer();

    @Override
    public KilnRecipe read(Identifier id, JsonObject json) {

        final var inputs = new ArrayList<Ingredient>();

        JsonHelper.getArray(json, "inputs").forEach(jsonElement -> inputs.add(Ingredient.fromJson(jsonElement)));
        if (inputs.isEmpty()) throw new JsonSyntaxException("Inputs cannot be empty");

        final var outputStack = getItemStack(JsonHelper.getObject(json, "output"));

        return new KilnRecipe(id, inputs, outputStack);
    }

    private ItemStack getItemStack(JsonObject json) {
        final var item = JsonHelper.getItem(json, "id");
        final var count = JsonHelper.getInt(json, "count", 1);

        return new ItemStack(item, count);
    }

    @Override
    public KilnRecipe read(Identifier id, PacketByteBuf buf) {

        final var inputs = buf.readCollection(value -> new ArrayList<>(), Ingredient::fromPacket);
        final var output = buf.readItemStack();

        return new KilnRecipe(id, inputs, output);
    }

    @Override
    public void write(PacketByteBuf buf, KilnRecipe recipe) {
        buf.writeCollection(recipe.getIngredients(), (buf1, ingredient) -> ingredient.write(buf1));
        buf.writeItemStack(recipe.getOutput());
    }
}
