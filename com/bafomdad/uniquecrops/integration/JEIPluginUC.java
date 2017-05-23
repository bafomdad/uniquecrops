package com.bafomdad.uniquecrops.integration;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.bafomdad.uniquecrops.core.EnumItems;
import com.bafomdad.uniquecrops.crafting.UCrafting;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.integration.craftyplants.*;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.ingredients.IModIngredientRegistration;

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
		blacklist.addIngredientToBlacklist(new ItemStack(UCBlocks.cropCinderbella));
		blacklist.addIngredientToBlacklist(new ItemStack(UCBlocks.cropCollis));
		blacklist.addIngredientToBlacklist(new ItemStack(UCBlocks.cropDirigible));
		blacklist.addIngredientToBlacklist(new ItemStack(UCBlocks.cropEnderlily));
		blacklist.addIngredientToBlacklist(new ItemStack(UCBlocks.cropFeroxia));
		blacklist.addIngredientToBlacklist(new ItemStack(UCBlocks.cropInvisibilia));
		blacklist.addIngredientToBlacklist(new ItemStack(UCBlocks.cropKnowledge));
		blacklist.addIngredientToBlacklist(new ItemStack(UCBlocks.cropMaryjane));
		blacklist.addIngredientToBlacklist(new ItemStack(UCBlocks.cropMerlinia));
		blacklist.addIngredientToBlacklist(new ItemStack(UCBlocks.cropMillennium));
		blacklist.addIngredientToBlacklist(new ItemStack(UCBlocks.cropMusica));
		blacklist.addIngredientToBlacklist(new ItemStack(UCBlocks.cropNormal));
		blacklist.addIngredientToBlacklist(new ItemStack(UCBlocks.cropPrecision));
		blacklist.addIngredientToBlacklist(new ItemStack(UCBlocks.cropWeepingbells));
		blacklist.addIngredientToBlacklist(new ItemStack(UCBlocks.cropEula));
		blacklist.addIngredientToBlacklist(new ItemStack(UCBlocks.cropCobblonia));
		blacklist.addIngredientToBlacklist(new ItemStack(UCBlocks.cropDyeius));
		blacklist.addIngredientToBlacklist(new ItemStack(UCBlocks.cropAbstract));
		blacklist.addIngredientToBlacklist(new ItemStack(UCBlocks.cropWafflonia));
		blacklist.addIngredientToBlacklist(new ItemStack(UCBlocks.cropPixelsius));
		blacklist.addIngredientToBlacklist(new ItemStack(UCBlocks.cropArtisia));
		blacklist.addIngredientToBlacklist(new ItemStack(UCBlocks.cropDevilsnare));
		blacklist.addIngredientToBlacklist(UCItems.generic.createStack(EnumItems.DUMMYITEM));
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {}

	@Override
	public void registerIngredients(IModIngredientRegistration registry) {}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {}
}
