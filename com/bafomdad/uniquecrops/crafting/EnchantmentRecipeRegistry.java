package com.bafomdad.uniquecrops.crafting;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.items.IItemHandler;

public class EnchantmentRecipeRegistry {

	private List<EnchantmentRecipe> recipeList;
	
	public EnchantmentRecipeRegistry() {
		
		this.recipeList = new ArrayList();
	}
	
	public void addRecipe(Enchantment enchantment, int cost, ItemStack... inputs) {
		
		this.addRecipe(enchantment.getRegistryName().toString(), cost, inputs);
	}
	
	public void addRecipe(String enchantmentLocation, int cost, ItemStack... inputs) {
		
		Ingredient[] ingredients = new Ingredient[inputs.length];
		for (int i = 0; i < ingredients.length; i++) {
			ingredients[i] = Ingredient.fromStacks(inputs[i]);
		}
		this.addRecipe(enchantmentLocation, cost, ingredients);
	}
	
	public void addRecipe(String enchantmentLocation, int cost, Ingredient... ingredients) {
		
		EnchantmentRecipe recipe = new EnchantmentRecipe(enchantmentLocation, cost, ingredients);
		
		this.recipeList.add(recipe);
	}
	
	public void removeRecipesByEnchantment(String enchantment) {
		
		this.recipeList.removeIf(recipe -> recipe.matchesEnchantment(enchantment));
	}
	
	@Nullable
	public EnchantmentRecipe findRecipe(List<ItemStack> inputs) {
		
		for (EnchantmentRecipe recipe : this.recipeList) {
			if (recipe.matches(inputs))
				return recipe;
		}
		return null;
	}
	
	@Nullable
	public EnchantmentRecipe findRecipe(IItemHandler inv) {
		
		List<ItemStack> list = new ArrayList();
		for (int i = 0; i < inv.getSlots(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (!stack.isEmpty())
				list.add(stack);
		}
		return this.findRecipe(list);
	}
	
	public List<EnchantmentRecipe> getRecipeList(List<EnchantmentRecipe> result) {
		
		result.addAll(this.recipeList);
		return result;
	}
}
