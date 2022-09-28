package com.bafomdad.uniquecrops.data.recipes;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;

import java.util.function.Consumer;

public class StoneCuttingProvider extends RecipeProvider {

    public StoneCuttingProvider(DataGenerator gen) {

        super(gen);
    }

    @Override
    public void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {

        consumer.accept(stonecutting(UCBlocks.FLYWOOD_PLANKS.get(), UCBlocks.FLYWOOD_STAIRS.get(), 1));
        consumer.accept(stonecutting(UCBlocks.ROSEWOOD_PLANKS.get(), UCBlocks.ROSEWOOD_STAIRS.get(), 1));
        consumer.accept(stonecutting(UCBlocks.RUINEDBRICKS.get(), UCBlocks.RUINEDBRICKS_STAIRS.get(), 1));
        consumer.accept(stonecutting(UCBlocks.ROSEWOOD_PLANKS.get(), UCBlocks.ROSEWOOD_SLAB.get(), 2));
        consumer.accept(stonecutting(UCBlocks.FLYWOOD_PLANKS.get(), UCBlocks.FLYWOOD_SLAB.get(), 2));
        consumer.accept(stonecutting(UCBlocks.RUINEDBRICKS.get(), UCBlocks.RUINEDBRICKS_SLAB.get(), 2));
        consumer.accept(stonecutting(UCBlocks.RUINEDBRICKSCARVED.get(), UCBlocks.RUINEDBRICKSCARVED_SLAB.get(), 2));
    }

    @Override
    public String getName() {

        return "Unique Crops stonecutting recipes";
    }

    private static ResourceLocation idFor(ItemLike a, ItemLike b) {

        ResourceLocation id1 = Registry.ITEM.getKey(a.asItem());
        ResourceLocation id2 = Registry.ITEM.getKey(b.asItem());
        return new ResourceLocation(UniqueCrops.MOD_ID, "stonecutting/" + id1.getPath() + "_to_" + id2.getPath());
    }

    private static FinishedRecipe stonecutting(ItemLike input, ItemLike output, int count) {

        return new Result(idFor(input, output), RecipeSerializer.STONECUTTER, Ingredient.of(input), output.asItem(), count);
    }

    private static class Result extends SingleItemRecipeBuilder.Result {

        public Result(ResourceLocation id, RecipeSerializer<?> serializer, Ingredient input, Item result, int countIn) {

            super(id, serializer, "", input, result, countIn, null, null);
        }

        @Override
        public JsonObject serializeAdvancement() {

            return null;
        }

        @Override
        public ResourceLocation getAdvancementId() {

            return null;
        }
    }
}
