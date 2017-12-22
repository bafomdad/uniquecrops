package com.bafomdad.uniquecrops.integration.jei;

import com.bafomdad.uniquecrops.crafting.SeedRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UCRecipeWrapper
    implements IRecipeWrapper {

  private List<List<ItemStack>> inputs;
  private List<ItemStack> outputs;

  public UCRecipeWrapper(SeedRecipe recipe) {

    this.inputs = new ArrayList<>();

    // Here we get a matching ItemStack list for each ingredient
    for (Ingredient input : recipe.getInputs()) {
      this.inputs.add(Arrays.asList(input.getMatchingStacks()));
    }

    this.outputs = new ArrayList<>();
    this.outputs.add(recipe.getOutput());
  }

  @Override
  public void getIngredients(IIngredients ingredients) {

    ingredients.setInputLists(ItemStack.class, this.inputs);
    ingredients.setOutput(ItemStack.class, this.outputs);
  }
}
