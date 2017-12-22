package com.bafomdad.uniquecrops.integration.jei;

import com.bafomdad.uniquecrops.UniqueCropsAPI;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.ingredients.IModIngredientRegistration;

import com.bafomdad.uniquecrops.core.EnumItems;
import com.bafomdad.uniquecrops.crafting.SeedRecipe;
import com.bafomdad.uniquecrops.init.UCItems;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;

import java.util.ArrayList;

@JEIPlugin
public class JEIPluginUC implements IModPlugin {

	public static final String UID_SEED_RECIPE = "jei.uniquecrops.seedrecipe";

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {

		IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
		registry.addRecipeCategories(new UCRecipeCategory(guiHelper, UID_SEED_RECIPE));
	}

	@Override
	public void register(IModRegistry registry) {
		
		IJeiHelpers helpers = registry.getJeiHelpers();
		registry.handleRecipes(SeedRecipe.class, UCRecipeWrapper::new, UID_SEED_RECIPE);

		registry.addRecipes(UniqueCropsAPI.SEED_RECIPE_REGISTRY.getRecipeList(new ArrayList<>()), UID_SEED_RECIPE);
		registry.addRecipeCatalyst(UCItems.generic.createStack(EnumItems.DUMMYITEM), UID_SEED_RECIPE);

		IIngredientBlacklist blacklist = helpers.getIngredientBlacklist();
		blacklist.addIngredientToBlacklist(UCItems.generic.createStack(EnumItems.DUMMYITEM));
	}
}
