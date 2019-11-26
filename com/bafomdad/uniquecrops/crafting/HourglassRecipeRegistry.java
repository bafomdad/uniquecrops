package com.bafomdad.uniquecrops.crafting;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public class HourglassRecipeRegistry {

	private List<HourglassRecipe> recipeList;
	
	public HourglassRecipeRegistry() {
		
		this.recipeList = new ArrayList();
	}
	
	public void addRecipe(Block output, Block input) {

		HourglassRecipe recipe = new HourglassRecipe(output, input);
		
		this.recipeList.add(recipe);
	}
	
	public void removeRecipeByOutput(Block output) {
		
		this.recipeList.removeIf(recipe -> (recipe.getOutput() == output));
	}
	
	@Nullable
	public HourglassRecipe findRecipe(IBlockState input) {
		
		for (HourglassRecipe recipe : this.recipeList) {
			if (recipe.matches(input))
				return recipe;
		}
		return null;
	}
	
	public List<HourglassRecipe> getRecipeList(List<HourglassRecipe> result) {

		result.addAll(this.recipeList);
		return result;
	}
}
