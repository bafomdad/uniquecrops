package com.bafomdad.uniquecrops;

import com.bafomdad.uniquecrops.crafting.SeedRecipeRegistry;

public class UniqueCropsAPI {

  public static final SeedRecipeRegistry SEED_RECIPE_REGISTRY;

  static {
    SEED_RECIPE_REGISTRY = new SeedRecipeRegistry();
  }

  private UniqueCropsAPI() {
    //
  }

}
