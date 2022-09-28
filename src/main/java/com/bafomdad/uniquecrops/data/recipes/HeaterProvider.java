package com.bafomdad.uniquecrops.data.recipes;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.JsonUtils;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.init.UCRecipes;
import com.google.gson.JsonObject;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class HeaterProvider extends RecipeProvider {

    public HeaterProvider(DataGenerator gen) {

        super(gen);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {

        consumer.accept(create(Blocks.ICE, Blocks.PACKED_ICE));
        consumer.accept(create(UCItems.TERIYAKI.get(), Items.COOKED_CHICKEN));
        consumer.accept(create(UCBlocks.ROSEWOOD_PLANKS.get(), UCBlocks.FLYWOOD_PLANKS.get()));
    }

    private static RecipeFinished create(ItemLike output, ItemLike input) {

        ResourceLocation id = new ResourceLocation(UniqueCrops.MOD_ID, "heater/" + output.asItem().getRegistryName().getPath());
        return new RecipeFinished(id, new ItemStack(output.asItem()), new ItemStack(input.asItem()));
    }

    public static class RecipeFinished implements FinishedRecipe {

        private final ResourceLocation id;
        private final ItemStack output, input;

        public RecipeFinished(ResourceLocation id, ItemStack output, ItemStack input) {

            this.id = id;
            this.output = output;
            this.input = input;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {

            json.add("output", JsonUtils.serializeStack(output));
            json.add("input", JsonUtils.serializeStack(input));
        }

        @Override
        public ResourceLocation getId() {

            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {

            return UCRecipes.HEATER_SERIALIZER.get();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {

            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {

            return null;
        }
    }
}
