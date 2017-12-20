package com.bafomdad.uniquecrops.integration.craftyplants;

import java.util.List;

import com.bafomdad.uniquecrops.crafting.UCrafting;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapper;

public class UCRecipeWrapper extends BlankRecipeWrapper {
	
	public final UCrafting recipe;
	
	public UCRecipeWrapper(UCrafting recipe) {
		
		this.recipe = recipe;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		
		ingredients.setInputs(ItemStack.class, this.recipe.getInputs());
		ingredients.setOutput(ItemStack.class, this.recipe.getOutput());
	}
}
