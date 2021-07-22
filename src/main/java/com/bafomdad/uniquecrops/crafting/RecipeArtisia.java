package com.bafomdad.uniquecrops.crafting;

import com.bafomdad.uniquecrops.api.IArtisiaRecipe;
import com.bafomdad.uniquecrops.init.UCRecipes;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class RecipeArtisia implements IArtisiaRecipe {

    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> inputs;

    public RecipeArtisia(ResourceLocation id, ItemStack output, Ingredient... inputs) {

        this.id = id;
        this.output = output;
        this.inputs = NonNullList.from(Ingredient.EMPTY, inputs);
        if (inputs.length > 9)
            throw new IllegalStateException("Inputs cannot be more than 9");
    }

    @Override
    public boolean matches(IInventory inv, World world) {

        List<Ingredient> ingredientsMissing = new ArrayList<>(inputs);

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack input = inv.getStackInSlot(i);
            if (input.isEmpty()) {
                break;
            }
            int stackIndex = -1;
            for (int j = 0; j < ingredientsMissing.size(); j++) {
                Ingredient ingr = ingredientsMissing.get(j);
                if (ingr.test(input)) {
                    stackIndex = j;
                    break;
                }
            }
            if (stackIndex != -1) {
                ingredientsMissing.remove(stackIndex);
            } else {
                return false;
            }
        }
        return ingredientsMissing.isEmpty();
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {

        return getRecipeOutput().copy();
    }

    @Override
    public ItemStack getRecipeOutput() {

        return output;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {

        return this.inputs;
    }

    @Override
    public ResourceLocation getId() {

        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {

        return UCRecipes.ARTISIA_SERIALIZER.get();
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<RecipeArtisia> {

        @Override
        public RecipeArtisia read(ResourceLocation id, JsonObject json) {

            ItemStack output = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "output"));
            JsonArray ingredients = JSONUtils.getJsonArray(json, "ingredients");
            List<Ingredient> inputs = new ArrayList<>();
            for (JsonElement e : ingredients)
                inputs.add(Ingredient.deserialize(e));

            return new RecipeArtisia(id, output, inputs.toArray(new Ingredient[0]));
        }

        @Nullable
        @Override
        public RecipeArtisia read(ResourceLocation id, PacketBuffer buf) {

            Ingredient[] inputs = new Ingredient[buf.readVarInt()];
            for (int i = 0; i < inputs.length; i++)
                inputs[i] = Ingredient.read(buf);

            ItemStack output = buf.readItemStack();
            return new RecipeArtisia(id, output, inputs);
        }

        @Override
        public void write(PacketBuffer buf, RecipeArtisia recipe) {

            buf.writeVarInt(recipe.getIngredients().size());
            for (Ingredient input : recipe.getIngredients())
                input.write(buf);

            buf.writeItemStack(recipe.getRecipeOutput(), false);
        }
    }
}
