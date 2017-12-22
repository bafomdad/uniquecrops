package com.bafomdad.uniquecrops.integration.jei;

import com.bafomdad.uniquecrops.crafting.SeedRecipe;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class UCRecipeHandler implements IRecipeHandler<SeedRecipe> {

	@Override
	public Class getRecipeClass() {

		return SeedRecipe.class;
	}

	@Override
	public String getRecipeCategoryUid(SeedRecipe recipe) {

		return UCRecipeCategory.NAME;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(SeedRecipe recipe) {

		return new UCRecipeWrapper(recipe);
	}

	@Override
	public boolean isRecipeValid(SeedRecipe recipe) {

		return true;
	}
}
