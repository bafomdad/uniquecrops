package com.bafomdad.uniquecrops.data.recipes;

import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;

import java.util.function.Consumer;

public class SmeltingProvider extends RecipeProvider {

    public SmeltingProvider(DataGenerator gen) {

        super(gen);
    }

    @Override
    public void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {

        CookingRecipeBuilder.smelting(Ingredient.of(UCItems.UNCOOKEDWAFFLE.get()), UCItems.UNCOOKEDWAFFLE.get(), 0.35F, 200)
                .unlockedBy("hasItem", has(UCItems.UNCOOKEDWAFFLE.get()))
                .save(consumer, "uniquecrops:smelting/waffles");
        CookingRecipeBuilder.smelting(Ingredient.of(Items.MILK_BUCKET), UCItems.BOILED_MILK.get(), 0.1F, 200)
                .unlockedBy("hasItem", has(Items.MILK_BUCKET))
                .save(consumer, "uniquecrops:smelting/boiled_mlk");
        CookingRecipeBuilder.cooking(Ingredient.of(UCItems.UNCOOKEDWAFFLE.get()), UCItems.UNCOOKEDWAFFLE.get(), 0.35F, 200, IRecipeSerializer.CAMPFIRE_COOKING_RECIPE)
                .unlockedBy("hasItem", has(UCItems.UNCOOKEDWAFFLE.get()))
                .save(consumer, "uniquecrops:campfire/waffles");
        CookingRecipeBuilder.cooking(Ingredient.of(Items.MILK_BUCKET), UCItems.BOILED_MILK.get(), 0.1F, 200, IRecipeSerializer.CAMPFIRE_COOKING_RECIPE)
                .unlockedBy("hasItem", has(Items.MILK_BUCKET))
                .save(consumer, "uniquecrops:campfire/boiled_mlk");
    }

    @Override
    public String getName() {

        return "Unique Crops cooking recipes";
    }
}
