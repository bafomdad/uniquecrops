package com.bafomdad.uniquecrops.crafting;

import com.bafomdad.uniquecrops.api.IHeaterRecipe;
import com.bafomdad.uniquecrops.init.UCRecipes;
import com.google.gson.JsonObject;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
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
    public boolean matches(Container inv, Level world) {

        return ItemStack.isSame(inv.getItem(0), input);
    }

    @Override
    public ItemStack getInput() {

        return this.input;
    }

    @Override
    public ItemStack assemble(Container inv) {

        return this.getResultItem();
    }

    @Override
    public ItemStack getResultItem() {

        return this.output;
    }

    @Override
    public ResourceLocation getId() {

        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {

        return UCRecipes.HEATER_SERIALIZER.get();
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<IHeaterRecipe> {

        @Override
        public IHeaterRecipe fromJson(ResourceLocation id, JsonObject json) {

            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            ItemStack input = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "input"));

            return new RecipeHeater(id, output, input);
        }

        @Nullable
        @Override
        public IHeaterRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {

            ItemStack output = buf.readItem();
            ItemStack input = buf.readItem();

            return new RecipeHeater(id, output, input);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, IHeaterRecipe recipe) {

            buf.writeItem(recipe.getResultItem());
            buf.writeItem(recipe.getInput());
        }
    }
}
