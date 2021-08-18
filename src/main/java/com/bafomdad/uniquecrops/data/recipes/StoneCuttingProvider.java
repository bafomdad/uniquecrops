package com.bafomdad.uniquecrops.data.recipes;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.SingleItemRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.util.function.Consumer;

public class StoneCuttingProvider extends RecipeProvider {

    public StoneCuttingProvider(DataGenerator gen) {

        super(gen);
    }

    @Override
    public void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {

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

    private static ResourceLocation idFor(IItemProvider a, IItemProvider b) {

        ResourceLocation id1 = Registry.ITEM.getKey(a.asItem());
        ResourceLocation id2 = Registry.ITEM.getKey(b.asItem());
        return new ResourceLocation(UniqueCrops.MOD_ID, "stonecutting/" + id1.getPath() + "_to_" + id2.getPath());
    }

    private static IFinishedRecipe stonecutting(IItemProvider input, IItemProvider output, int count) {

        return new Result(idFor(input, output), IRecipeSerializer.STONECUTTER, Ingredient.of(input), output.asItem(), count);
    }

    private static class Result extends SingleItemRecipeBuilder.Result {

        public Result(ResourceLocation id, IRecipeSerializer<?> serializer, Ingredient input, Item result, int countIn) {

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
