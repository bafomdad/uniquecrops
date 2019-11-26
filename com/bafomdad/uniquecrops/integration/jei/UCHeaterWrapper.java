package com.bafomdad.uniquecrops.integration.jei;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

import com.bafomdad.uniquecrops.crafting.HeaterRecipe;

public class UCHeaterWrapper implements IRecipeWrapper {
	
	private List<ItemStack> inputs, outputs;
	
	public UCHeaterWrapper(HeaterRecipe recipe) {
		
		this.inputs = new ArrayList();
		this.outputs = new ArrayList();
		
		this.inputs.add(recipe.getInput());
		this.outputs.add(recipe.getOutput());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {

		ingredients.setInput(ItemStack.class, this.inputs);
		ingredients.setOutput(ItemStack.class, this.outputs);
	}
}
