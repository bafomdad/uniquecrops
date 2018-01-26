package com.bafomdad.uniquecrops.integration.crafttweaker;

import com.bafomdad.uniquecrops.integration.crafttweaker.mtlib.InputHelper;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class IngredientHelper {

  public static Ingredient toIngredient(IIngredient ingredient) {

    return new IngredientWrapper(ingredient);
  }

  public static class IngredientWrapper
      extends Ingredient {

    private IIngredient ingredient;

    public IngredientWrapper(IIngredient ingredient) {

      this.ingredient = ingredient;
    }

    public int getAmount() {

      return this.ingredient.getAmount();
    }

    @Override
    public ItemStack[] getMatchingStacks() {

      List<IItemStack> stacks = this.ingredient != null ? this.ingredient.getItems() : Collections.emptyList();
      return InputHelper.toStacks(stacks.toArray(new IItemStack[stacks.size()]));
    }

    @Override
    public boolean apply(@Nullable ItemStack itemStack) {

      if (this.ingredient == null) {
        return itemStack == null || itemStack.isEmpty();
      }

      if (itemStack == null) {
        return false;
      }

      return this.ingredient.matches(InputHelper.toIItemStack(itemStack));
    }
  }
}
