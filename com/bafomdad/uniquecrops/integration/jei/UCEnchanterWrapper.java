package com.bafomdad.uniquecrops.integration.jei;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.text.TextComponentTranslation;

import com.bafomdad.uniquecrops.crafting.EnchantmentRecipe;

public class UCEnchanterWrapper implements IRecipeWrapper {
	
	private final List<List<ItemStack>> inputs;
	private final EnchantmentRecipe recipe;
	private final ItemStack output = new ItemStack(Items.ENCHANTED_BOOK);
	
	public UCEnchanterWrapper(EnchantmentRecipe recipe) {

		this.inputs = new ArrayList();
		this.recipe = recipe;
		
		for (Ingredient input : recipe.getInputs())
			this.inputs.add(Arrays.asList(input.getMatchingStacks()));
			
		this.output.addEnchantment(recipe.getEnchantment(), recipe.getEnchantment().getMaxLevel());
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		
		final String text = new TextComponentTranslation(recipe.getEnchantment().getName()).getFormattedText() + " " + recipe.getEnchantment().getMaxLevel();
		int stringWidth = minecraft.fontRenderer.getStringWidth(text);
		minecraft.fontRenderer.drawString(text, 50 - stringWidth / 2, -20, Color.GRAY.getRGB());
		final String cost = "Cost: " + recipe.getCost();
		int costWidth = minecraft.fontRenderer.getStringWidth(cost);
		minecraft.fontRenderer.drawString(cost, 50 - costWidth / 2, 95, Color.GRAY.getRGB());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {

		ingredients.setInputLists(ItemStack.class, this.inputs);
		ingredients.setOutput(ItemStack.class, this.output);
	}
}
