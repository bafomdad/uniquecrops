package com.bafomdad.uniquecrops.integration.jei;

import com.bafomdad.uniquecrops.UniqueCropsAPI;

import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.ingredients.IModIngredientRegistration;

import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.enums.EnumItems;
import com.bafomdad.uniquecrops.crafting.*;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;

import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
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
		registry.addRecipeCatalyst(new ItemStack(UCItems.wildwoodStaff), UUID_ENCHANTER_RECIPE);
		
		addDescription(registry, UCBlocks.harvestTrap, "harvesttrap");
		addDescription(registry, UCItems.emeradicDiamond, "emeradicdiamond");
		addDescription(registry, UCItems.wildwoodStaff, "wildwoodstaff");
		addDescription(registry, UCItems.pixelBrush, "pixelbrush");
		addDescription(registry, UCItems.precisionAxe, "precisionaxe");
		addDescription(registry, UCItems.precisionPick, "precisionpick");
		addDescription(registry, UCItems.precisionShovel, "precisionshovel");
		addDescription(registry, UCItems.precisionSword, "precisionsword");
		addDescription(registry, UCItems.glasses3D, "3dglasses");
		addDescription(registry, UCItems.poncho, "poncho");
		addDescription(registry, UCItems.thunderPantz, "thunderpantz");
		addDescription(registry, UCItems.pixelGlasses, "pixelglasses");
		addDescription(registry, UCItems.ankh, "flintankh");

		IJeiHelpers helpers = registry.getJeiHelpers();
		IIngredientBlacklist blacklist = helpers.getIngredientBlacklist();
		blacklist.addIngredientToBlacklist(EnumItems.DUMMYITEM.createStack());
	}
	
	private void addDescription(IModRegistry registry, Item item, String str) {
		
		registry.addIngredientInfo(new ItemStack(item), ItemStack.class, UCStrings.INFOJEI + str);
	}
	
	private void addDescription(IModRegistry registry, Block block, String str) {
		
		registry.addIngredientInfo(new ItemStack(block), ItemStack.class, UCStrings.INFOJEI + str);
	}
}
