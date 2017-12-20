package com.bafomdad.uniquecrops.integration.craftyplants;

import com.bafomdad.uniquecrops.crafting.UCrafting;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class UCRecipeHandler implements IRecipeHandler<UCrafting> {

	@Override
	public Class getRecipeClass() {

		return UCrafting.class;
	}

	@Override
	public String getRecipeCategoryUid(UCrafting recipe) {

		return UCRecipeCategory.NAME;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(UCrafting recipe) {

		return new UCRecipeWrapper(recipe);
	}

	@Override
	public boolean isRecipeValid(UCrafting recipe) {

		return true;
	}
}
