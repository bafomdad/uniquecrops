package com.bafomdad.uniquecrops.integration;

import net.minecraft.item.ItemStack;

import com.bafomdad.uniquecrops.integration.crafttweaker.SeedRecipe;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IItemStack;

public class CTPlugin {

	public static void init() {
		
		CraftTweakerAPI.registerClass(SeedRecipe.class);
	}
	
	public static ItemStack toStack(IItemStack stack) {
		
		if (stack == null) return ItemStack.EMPTY;
		
		return (ItemStack)stack.getInternal();
	}
}
