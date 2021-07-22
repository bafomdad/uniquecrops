package com.bafomdad.uniquecrops.crafting;

import com.bafomdad.uniquecrops.api.IHeaterRecipe;
import com.bafomdad.uniquecrops.init.UCRecipes;
import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class RecipeHeater implements IHeaterRecipe {

    private final ResourceLocation id;
    private final ItemStack output, input;

    public RecipeHeater(ResourceLocation id, ItemStack output, ItemStack input) {

        this.id = id;
        this.output = output;
        this.input = input;
    }

    @Override
    public boolean matches(IInventory inv, World world) {

        return ItemStack.areItemsEqual(inv.getStackInSlot(0), input);
    }

    @Override
    public ItemStack getInput() {

        return this.input;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {

        return this.getRecipeOutput();
    }

    @Override
    public ItemStack getRecipeOutput() {

        return this.output;
    }

    @Override
    public ResourceLocation getId() {

        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {

        return UCRecipes.HEATER_SERIALIZER.get();
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<IHeaterRecipe> {

        @Override
        public IHeaterRecipe read(ResourceLocation id, JsonObject json) {

            ItemStack output = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "output"));
            ItemStack input = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "input"));

            return new RecipeHeater(id, output, input);
        }

        @Nullable
        @Override
        public IHeaterRecipe read(ResourceLocation id, PacketBuffer buf) {

            ItemStack output = buf.readItemStack();
            ItemStack input = buf.readItemStack();

            return new RecipeHeater(id, output, input);
        }

        @Override
        public void write(PacketBuffer buf, IHeaterRecipe recipe) {

            buf.writeItemStack(recipe.getRecipeOutput());
            buf.writeItemStack(recipe.getInput());
        }
    }
}
