package com.bafomdad.uniquecrops.integration;

import net.minecraft.item.ItemStack;

import com.bafomdad.uniquecrops.init.UCBlocks;

import mezz.jei.api.IItemBlacklist;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IModIngredientRegistration;

@JEIPlugin
public class JEIPluginUC implements IModPlugin {
	
	@Override
	public void register(IModRegistry registry) {
		
		IJeiHelpers helpers = registry.getJeiHelpers();
		IItemBlacklist blacklist = helpers.getItemBlacklist();
		blacklist.addItemToBlacklist(new ItemStack(UCBlocks.cropCinderbella));
		blacklist.addItemToBlacklist(new ItemStack(UCBlocks.cropCollis));
		blacklist.addItemToBlacklist(new ItemStack(UCBlocks.cropDirigible));
		blacklist.addItemToBlacklist(new ItemStack(UCBlocks.cropEnderlily));
		blacklist.addItemToBlacklist(new ItemStack(UCBlocks.cropFeroxia));
		blacklist.addItemToBlacklist(new ItemStack(UCBlocks.cropInvisibilia));
		blacklist.addItemToBlacklist(new ItemStack(UCBlocks.cropKnowledge));
		blacklist.addItemToBlacklist(new ItemStack(UCBlocks.cropMaryjane));
		blacklist.addItemToBlacklist(new ItemStack(UCBlocks.cropMerlinia));
		blacklist.addItemToBlacklist(new ItemStack(UCBlocks.cropMillennium));
		blacklist.addItemToBlacklist(new ItemStack(UCBlocks.cropMusica));
		blacklist.addItemToBlacklist(new ItemStack(UCBlocks.cropNormal));
		blacklist.addItemToBlacklist(new ItemStack(UCBlocks.cropPrecision));
		blacklist.addItemToBlacklist(new ItemStack(UCBlocks.cropWeepingbells));
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {}

	@Override
	public void registerIngredients(IModIngredientRegistration registry) {}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {}
}
