package com.bafomdad.uniquecrops;

import com.bafomdad.uniquecrops.crafting.*;

public class UniqueCropsAPI {

	public static final SeedRecipeRegistry SEED_RECIPE_REGISTRY;
	public static final HourglassRecipeRegistry HOURGLASS_RECIPE_REGISTRY;
	public static final CobbloniaDropsRegistry COBBLONIA_DROPS_REGISTRY;
	public static final HeaterRecipeRegistry MASSHEATER_REGISTRY;
	public static final EnchantmentRecipeRegistry ENCHANTER_REGISTRY;

	static {
		
		SEED_RECIPE_REGISTRY = new SeedRecipeRegistry();
		HOURGLASS_RECIPE_REGISTRY = new HourglassRecipeRegistry();
		COBBLONIA_DROPS_REGISTRY = new CobbloniaDropsRegistry();
		MASSHEATER_REGISTRY = new HeaterRecipeRegistry();
		ENCHANTER_REGISTRY = new EnchantmentRecipeRegistry();
	}

	private UniqueCropsAPI() {
		//
	}
}
