package com.bafomdad.uniquecrops.crafting;

import net.minecraft.item.ItemStack;

public class HeaterRecipe {

	private ItemStack input, output;
	
	public HeaterRecipe(ItemStack output, ItemStack input) {
		
		this.output = output;
		this.input = input;
	}
	
	public boolean matches(ItemStack input) {
		
		return ItemStack.areItemsEqual(input, this.input);
	}
	
	public ItemStack getInput() {
		
		return this.input;
	}
	
	public ItemStack getOutput() {
		
		return this.output;
	}
}
