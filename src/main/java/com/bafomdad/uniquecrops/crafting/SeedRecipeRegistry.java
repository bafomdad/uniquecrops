package com.bafomdad.uniquecrops.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SeedRecipeRegistry {

  private List<SeedRecipe> recipeList;

  public SeedRecipeRegistry() {

    this.recipeList = new ArrayList<>();
  }

  public void addSeedRecipe(ItemStack result, ItemStack center, ItemStack corner, ItemStack edge) {

    Ingredient ingredientCenter = Ingredient.fromStacks(center);
    Ingredient ingredientCorner = Ingredient.fromStacks(corner);
    Ingredient ingredientEdge = Ingredient.fromStacks(edge);

    this.addSeedRecipe(result, ingredientCenter, ingredientCorner, ingredientEdge);
  }

  public List<SeedRecipe> getRecipeList(List<SeedRecipe> result) {

    result.addAll(this.recipeList);
    return result;
  }

  public void addSeedRecipe(ItemStack result, Ingredient center, Ingredient corner, Ingredient edge) {

    SeedRecipe recipe = new SeedRecipe(
        result,
        new Ingredient[]{
            corner, edge, corner,
            edge, center, edge,
            corner, edge, corner
        }
    );

    this.recipeList.add(recipe);
  }

  @Nullable
  public SeedRecipe findSeedRecipe(List<ItemStack> inputs) {

    for (SeedRecipe recipe : this.recipeList) {

      if (recipe.matches(inputs)) {
        return recipe;
      }
    }

    return null;
  }

}
