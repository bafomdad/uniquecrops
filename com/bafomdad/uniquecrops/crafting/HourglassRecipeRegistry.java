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
	
	public void addRecipe(Block output, int outputMeta, Block input, int inputMeta) {
		
		HourglassRecipe recipe = new HourglassRecipe(output, outputMeta, input, inputMeta);
		this.recipeList.add(recipe);
	}
	
	public void addRecipe(Block output, Block input) {

		this.addRecipe(output, 0, input, 0);
	}
	
	public void removeRecipeByOutput(Block output) {
		
		this.recipeList.removeIf(recipe -> (recipe.getOutput() == output));
	}
	
	public void removeRecipeByOutput(Block output, int outputMeta) {
		
		if (outputMeta == 0) {
			this.removeRecipeByOutput(output);
			return;
		} else {
			this.recipeList.removeIf(recipe -> (
					recipe.getOutput() == output &&
					recipe.getOutputMeta() == outputMeta
					));
		}
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
