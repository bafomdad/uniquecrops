package com.bafomdad.uniquecrops.integration;

import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.ingredients.IModIngredientRegistration;

import com.bafomdad.uniquecrops.core.EnumItems;
import com.bafomdad.uniquecrops.crafting.UCrafting;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.integration.craftyplants.*;

@JEIPlugin
public class JEIPluginUC implements IModPlugin {
	
	@Override
	public void register(IModRegistry registry) {
		
		IJeiHelpers helpers = registry.getJeiHelpers();
		registry.addRecipeCategories(new UCRecipeCategory(helpers.getGuiHelper()));
		registry.addRecipeHandlers(new UCRecipeHandler());
		
		registry.addRecipes(UCrafting.recipes);
		registry.addRecipeCategoryCraftingItem(UCItems.generic.createStack(EnumItems.DUMMYITEM), UCRecipeCategory.NAME);
		
		IIngredientBlacklist blacklist = helpers.getIngredientBlacklist();
		blacklist.addIngredientToBlacklist(UCItems.generic.createStack(EnumItems.DUMMYITEM));
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {}

	@Override
	public void registerIngredients(IModIngredientRegistration registry) {}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {}
}
