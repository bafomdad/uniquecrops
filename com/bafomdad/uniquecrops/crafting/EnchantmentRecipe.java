package com.bafomdad.uniquecrops.crafting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class EnchantmentRecipe {

	Enchantment enchantment;
	int cost;
	List<Ingredient> inputs;
	
	public EnchantmentRecipe(Enchantment enchantment, int cost, Ingredient[] inputs) {
		
		if (enchantment == null)
			throw new IllegalStateException("Enchantment cannot be null");
		if (cost < 0) cost = 0;
		if (inputs == null || inputs.length <= 0)
			throw new IllegalStateException("Inputs cannot be empty or null");
		
		this.enchantment = enchantment;
		this.cost = cost;
		this.inputs = new ArrayList();
		this.inputs.addAll(Arrays.asList(inputs));
		
	}
	
	public EnchantmentRecipe(String enchantmentLocation, int cost, Ingredient[] inputs) {
		
		if (enchantmentLocation == null || enchantmentLocation.isEmpty())
			throw new IllegalStateException("Enchantment id cannot be empty or null");
		if (cost < 0) cost = 0;
		if (inputs == null || inputs.length <= 0)
			throw new IllegalStateException("Inputs cannot be empty or null");
		if (inputs.length > 5)
			throw new IllegalStateException("Inputs cannot exceed 5 items");
		
		this.enchantment = Enchantment.getEnchantmentByLocation(enchantmentLocation);
		this.cost = cost;
		this.inputs = new ArrayList();
		this.inputs.addAll(Arrays.asList(inputs));
	}
	
	public boolean matches(List<ItemStack> stackList) {
		
		List<Ingredient> inputsMissing = new ArrayList(this.inputs);
		for (ItemStack stack : stackList) {
			if (stack.isEmpty()) break;
			
			int stackIndex = -1;
			for (int j = 0; j < inputsMissing.size(); j++) {
				Ingredient input = inputsMissing.get(j);
				if (input.apply(stack)) {
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
	
	public boolean matchesEnchantment(String location) {
		
		return this.enchantment.getRegistryName().toString().equals(location);
	}
	
	public void applyEnchantment(ItemStack toApply) {

		toApply.addEnchantment(this.enchantment, this.enchantment.getMaxLevel());
	}
	
	public Enchantment getEnchantment() {
		
		return this.enchantment;
	}
	
	public List<Ingredient> getInputs() {
		
		return new ArrayList<>(this.inputs);
	}
	
	public int getCost() {
		
		return this.cost;
	}
}
