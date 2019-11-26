package com.bafomdad.uniquecrops.integration.jei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import com.bafomdad.uniquecrops.crafting.EnchantmentRecipe;

public class UCEnchanterWrapper implements IRecipeWrapper {
	
	private List<List<ItemStack>> inputs;
	private ItemStack output = new ItemStack(Items.ENCHANTED_BOOK);
	
	public UCEnchanterWrapper(EnchantmentRecipe recipe) {

		this.inputs = new ArrayList();
		
		for (Ingredient input : recipe.getInputs())
			this.inputs.add(Arrays.asList(input.getMatchingStacks()));
			
		this.output.addEnchantment(recipe.getEnchantment(), recipe.getEnchantment().getMaxLevel());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {

		ingredients.setInputLists(ItemStack.class, this.inputs);
		ingredients.setOutput(ItemStack.class, this.output);
	}
}
