package com.bafomdad.uniquecrops.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SeedRecipeRegistry {

  private List<SeedRecipe> recipeList;

  public SeedRecipeRegistry() {

    this.recipeList = new ArrayList<>();
  }

  public void addRecipe(ItemStack result, ItemStack center, ItemStack corner, ItemStack edge) {

    Ingredient ingredientCenter = Ingredient.fromStacks(center);
    Ingredient ingredientCorner = Ingredient.fromStacks(corner);
    Ingredient ingredientEdge = Ingredient.fromStacks(edge);

    this.addRecipe(result, ingredientCenter, ingredientCorner, ingredientEdge);
  }

  public List<SeedRecipe> getRecipeList(List<SeedRecipe> result) {

    result.addAll(this.recipeList);
    return result;
  }

  public void addRecipe(ItemStack output, Ingredient center, Ingredient corner, Ingredient edge) {

    SeedRecipe recipe = new SeedRecipe(
        output,
        new Ingredient[]{
            corner, edge, corner,
            edge, center, edge,
            corner, edge, corner
        }
    );

    this.recipeList.add(recipe);
  }

  public void removeRecipesByOutput(ItemStack output) {

    this.recipeList.removeIf(recipe -> recipe.matchesOutput(output));
  }

  @Nullable
  public SeedRecipe findRecipe(List<ItemStack> inputs) {

    for (SeedRecipe recipe : this.recipeList) {

      if (recipe.matches(inputs)) {
        return recipe;
      }
    }

    return null;
  }
}
