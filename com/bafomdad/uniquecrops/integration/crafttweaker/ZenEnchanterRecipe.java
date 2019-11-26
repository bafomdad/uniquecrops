package com.bafomdad.uniquecrops.integration.crafttweaker;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import net.minecraft.item.crafting.Ingredient;

import com.bafomdad.uniquecrops.UniqueCropsAPI;
import com.bafomdad.uniquecrops.integration.crafttweaker.mtlib.BaseUndoable;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;

@ZenRegister
@ZenClass(ZenEnchanterRecipe.NAME)
public class ZenEnchanterRecipe {

	public static final String NAME = "mods.uniquecrops.Enchantment";
	
	@ZenMethod
	public static void addRecipe(String enchantmentLocation, int cost, IIngredient[] inputs) {
		
		Ingredient[] ingredients = new Ingredient[inputs.length];
		for (int i = 0; i < ingredients.length; i++)
			ingredients[i] = IngredientHelper.toIngredient(inputs[i]);
			
		CraftTweakerPlugin.LATE_ADDITIONS.add(new Add(enchantmentLocation, cost, ingredients));
	}
	
	private static class Add extends BaseUndoable {
		
		private final String enchantmentLocation;
		private final int cost;
		private final Ingredient[] inputs;

		protected Add(String enchantmentLocation, int cost, Ingredient[] inputs) {
			
			super(NAME);
			this.enchantmentLocation = enchantmentLocation;
			this.cost = cost;
			this.inputs = inputs;
		}

		@Override
		public void apply() {

			UniqueCropsAPI.ENCHANTER_REGISTRY.addRecipe(enchantmentLocation, cost, inputs);
		}
	}
	
	@ZenMethod
	public static void removeEnchantmentRecipe(String enchantmentLocation) {
		
		CraftTweakerPlugin.LATE_REMOVALS.add(new Remove(enchantmentLocation));
	}
	
	private static class Remove extends BaseUndoable {
		
		private final String enchantmentLocation;
		
		protected Remove(String enchantmentLocation) {
			
			super(NAME);
			this.enchantmentLocation = enchantmentLocation;
		}
		
		@Override
		public void apply() {
			
			UniqueCropsAPI.ENCHANTER_REGISTRY.removeRecipesByEnchantment(enchantmentLocation);
		}
	}
}
