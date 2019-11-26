package com.bafomdad.uniquecrops.integration.jei;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import com.bafomdad.uniquecrops.crafting.HourglassRecipe;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;

public class UCHourglassWrapper implements IRecipeWrapper {
	
	private List<ItemStack> inputs, outputs;
	
	public UCHourglassWrapper(HourglassRecipe recipe) {
		
		this.inputs = new ArrayList();
		this.outputs = new ArrayList();
		
		this.inputs.add(new ItemStack(recipe.getInput()));
		this.outputs.add(new ItemStack(recipe.getOutput()));
	}

	@Override
	public void getIngredients(IIngredients ingredients) {

		ingredients.setInput(ItemStack.class, this.inputs);
		ingredients.setOutput(ItemStack.class, this.outputs);
	}
}
