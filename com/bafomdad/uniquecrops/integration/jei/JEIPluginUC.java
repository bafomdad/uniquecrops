package com.bafomdad.uniquecrops.integration.jei;

import com.bafomdad.uniquecrops.UniqueCropsAPI;

import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.ingredients.IModIngredientRegistration;

import com.bafomdad.uniquecrops.core.enums.EnumItems;
import com.bafomdad.uniquecrops.crafting.*;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;

import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

@JEIPlugin
public class JEIPluginUC implements IModPlugin {

	public static final String UID_SEED_RECIPE = "jei.uniquecrops.seedrecipe";
	public static final String UUID_HOURGLASS_RECIPE = "jei.uniquecrops.hourglassrecipe";
	public static final String UUID_HEATER_RECIPE = "jei.uniquecrops.heaterrecipe";
	public static final String UUID_ENCHANTER_RECIPE = "jei.uniquecrops.enchanterrecipe";

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {

		IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
		registry.addRecipeCategories(new UCRecipeCategory(guiHelper, UID_SEED_RECIPE));
		registry.addRecipeCategories(new UCHourglassCategory(guiHelper, UUID_HOURGLASS_RECIPE));
		registry.addRecipeCategories(new UCHeaterCategory(guiHelper, UUID_HEATER_RECIPE));
		registry.addRecipeCategories(new UCEnchanterCategory(guiHelper, UUID_ENCHANTER_RECIPE));
	}

	@Override
	public void register(IModRegistry registry) {
		
		registry.handleRecipes(SeedRecipe.class, UCRecipeWrapper::new, UID_SEED_RECIPE);
		registry.handleRecipes(HourglassRecipe.class, UCHourglassWrapper::new, UUID_HOURGLASS_RECIPE);
		registry.handleRecipes(HeaterRecipe.class, UCHeaterWrapper::new, UUID_HEATER_RECIPE);
		registry.handleRecipes(EnchantmentRecipe.class, UCEnchanterWrapper::new, UUID_ENCHANTER_RECIPE);

		registry.addRecipes(UniqueCropsAPI.SEED_RECIPE_REGISTRY.getRecipeList(new ArrayList<>()), UID_SEED_RECIPE);
		registry.addRecipes(UniqueCropsAPI.HOURGLASS_RECIPE_REGISTRY.getRecipeList(new ArrayList()), UUID_HOURGLASS_RECIPE);
		registry.addRecipes(UniqueCropsAPI.MASSHEATER_REGISTRY.getRecipeList(new ArrayList()), UUID_HEATER_RECIPE);
		registry.addRecipes(UniqueCropsAPI.ENCHANTER_REGISTRY.getRecipeList(new ArrayList()), UUID_ENCHANTER_RECIPE);
		
		registry.addRecipeCatalyst(EnumItems.DUMMYITEM.createStack(), UID_SEED_RECIPE);
		registry.addRecipeCatalyst(new ItemStack(UCBlocks.hourglass), UUID_HOURGLASS_RECIPE);
		registry.addRecipeCatalyst(new ItemStack(UCBlocks.cocito), UUID_HEATER_RECIPE);
		registry.addRecipeCatalyst(new ItemStack(Blocks.ENCHANTING_TABLE), UUID_ENCHANTER_RECIPE);

		IJeiHelpers helpers = registry.getJeiHelpers();
		IIngredientBlacklist blacklist = helpers.getIngredientBlacklist();
		blacklist.addIngredientToBlacklist(EnumItems.DUMMYITEM.createStack());
	}
}
