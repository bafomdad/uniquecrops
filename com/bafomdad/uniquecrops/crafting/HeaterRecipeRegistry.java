package com.bafomdad.uniquecrops.crafting;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class HeaterRecipeRegistry {

	private List<HeaterRecipe> recipeList;
	
	public HeaterRecipeRegistry() {
		
		this.recipeList = new ArrayList();
	}
	
	public void addRecipe(ItemStack output, ItemStack input) {
		
		HeaterRecipe recipe = new HeaterRecipe(output, input);
		
		this.recipeList.add(recipe);
	}
	
	public void removeRecipeByOutput(ItemStack output) {
		
		this.recipeList.removeIf(recipe -> ItemStack.areItemsEqual(recipe.getOutput(), output));
	}
	
	public HeaterRecipe findRecipe(ItemStack input) {
		
		for (HeaterRecipe recipe : this.recipeList) {
			if (recipe.matches(input))
				return recipe;
		}
		return null;
	}
	
	public List<HeaterRecipe> getRecipeList(List<HeaterRecipe> result) {
		
		result.addAll(this.recipeList);
		return result;
	}
}
