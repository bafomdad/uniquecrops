package com.bafomdad.uniquecrops.crafting;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class UCrafting {

	ItemStack output;
	List<ItemStack> inputs;
	
	public static List<UCrafting> recipes = new ArrayList<UCrafting>();
	
	public UCrafting(ItemStack output, ItemStack... inputs) {
		
		this.output = output;
		
		List<ItemStack> inputsToSet = new ArrayList();
		for (ItemStack is : inputs)
			inputsToSet.add(is);
		
		this.inputs = inputsToSet;
	}
	
	public boolean matches(List<ItemStack> stackList) {
		
		List<ItemStack> inputsMissing = new ArrayList(inputs);
		
		for (int i = 0; i < stackList.size(); i++) {
			ItemStack stack = stackList.get(i);
			if (stack.isEmpty())
				break;
			
			int stackIndex = -1;
			
			for (int j = 0; j < inputsMissing.size(); j++) {
				ItemStack input = inputsMissing.get(j);
				
				if (ItemStack.areItemsEqual(input, stack)) {
					stackIndex = j;
					break;
				}
			}
			if (stackIndex >= 0)
				inputsMissing.remove(stackIndex);
			else return false;
		}
		return inputsMissing.isEmpty();
	}
	
	public List<ItemStack> getInputs() {
		
		return new ArrayList(inputs);
	}
	
	public ItemStack getOutput() {
		
		return output;
	}
	
	public static UCrafting addRecipe(ItemStack output, ItemStack... inputs) {
		
		UCrafting recipe = new UCrafting(output, inputs);
		recipes.add(recipe);
		
		return recipe;
	}
}
