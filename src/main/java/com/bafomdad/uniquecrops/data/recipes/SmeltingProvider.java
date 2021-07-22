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
    public void registerRecipes(Consumer<IFinishedRecipe> consumer) {

        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(UCItems.UNCOOKEDWAFFLE.get()), UCItems.UNCOOKEDWAFFLE.get(), 0.35F, 200)
                .addCriterion("hasItem", hasItem(UCItems.UNCOOKEDWAFFLE.get()))
                .build(consumer, "uniquecrops:smelting/waffles");
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.MILK_BUCKET), UCItems.BOILED_MILK.get(), 0.1F, 200)
                .addCriterion("hasItem", hasItem(Items.MILK_BUCKET))
                .build(consumer, "uniquecrops:smelting/boiled_mlk");
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(UCItems.UNCOOKEDWAFFLE.get()), UCItems.UNCOOKEDWAFFLE.get(), 0.35F, 200, IRecipeSerializer.CAMPFIRE_COOKING)
                .addCriterion("hasItem", hasItem(UCItems.UNCOOKEDWAFFLE.get()))
                .build(consumer, "uniquecrops:campfire/waffles");
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.MILK_BUCKET), UCItems.BOILED_MILK.get(), 0.1F, 200, IRecipeSerializer.CAMPFIRE_COOKING)
                .addCriterion("hasItem", hasItem(Items.MILK_BUCKET))
                .build(consumer, "uniquecrops:campfire/boiled_mlk");
    }

    @Override
    public String getName() {

        return "Unique Crops cooking recipes";
    }
}
