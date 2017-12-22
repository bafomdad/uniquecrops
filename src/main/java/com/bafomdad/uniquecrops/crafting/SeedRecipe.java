package com.bafomdad.uniquecrops.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SeedRecipe {

  ItemStack output;
  List<Ingredient> inputs;

  public SeedRecipe(ItemStack output, Ingredient[] inputs) {

    this.output = output;
    this.inputs = new ArrayList<>();
    this.inputs.addAll(Arrays.asList(inputs));
  }

  public boolean matches(List<ItemStack> stackList) {

    List<Ingredient> inputsMissing = new ArrayList<>(this.inputs);

    for (ItemStack stack : stackList) {

      if (stack.isEmpty()) {
        break;
      }

      int stackIndex = -1;

      for (int j = 0; j < inputsMissing.size(); j++) {
        Ingredient input = inputsMissing.get(j);

        if (input.apply(stack)) {
          stackIndex = j;
          break;
        }
      }

      if (stackIndex >= 0) {
        inputsMissing.remove(stackIndex);

      } else {
        return false;
      }
    }

    return inputsMissing.isEmpty();
  }

  public List<Ingredient> getInputs() {

    return new ArrayList<>(this.inputs);
  }

  public ItemStack getOutput() {

    return this.output.copy();
  }

}
