package com.bafomdad.uniquecrops.integration.crafttweaker;

import java.util.List;

import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import com.bafomdad.uniquecrops.crafting.UCrafting;
import com.bafomdad.uniquecrops.integration.CTPlugin;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;

@ZenClass("mods.uniquecrops.SeedRecipe")
public class SeedRecipe {
	
	@ZenMethod
	public static void addRecipe(IItemStack output, IItemStack center, IItemStack corner, IItemStack edge) {
		
		ItemStack oCenter = CTPlugin.toStack(center), oCorner = CTPlugin.toStack(corner), oEdge = CTPlugin.toStack(edge);
		if (oCenter.isEmpty() || oCorner.isEmpty() || oEdge.isEmpty()) return;
		
		UCrafting r = new UCrafting(CTPlugin.toStack(output), oCorner, oEdge, oCorner, oEdge, oCenter, oEdge, oCorner, oEdge, oCorner);
		CraftTweakerAPI.apply(new Add(r));
	}
	
	@ZenMethod
	public static void addLongRecipe(IItemStack output, IItemStack[] inputs) {
		
		if (inputs.length > 9 || inputs.length < 9) return;
		
		for (IItemStack stack : inputs) {
			if (CTPlugin.toStack(stack).isEmpty()) 
				return;
		}
		UCrafting r = new UCrafting(CTPlugin.toStack(output), CTPlugin.toStack(inputs[0]), CTPlugin.toStack(inputs[1]), CTPlugin.toStack(inputs[2]), CTPlugin.toStack(inputs[3]), CTPlugin.toStack(inputs[4]), CTPlugin.toStack(inputs[5]), CTPlugin.toStack(inputs[6]), CTPlugin.toStack(inputs[7]), CTPlugin.toStack(inputs[8]));
		CraftTweakerAPI.apply(new Add(r));
	}
	
	private static class Add implements IAction {

		private final UCrafting recipe;
		
		public Add(UCrafting recipe) {
			
			this.recipe = recipe;
		}
		
		@Override
		public void apply() {

			UCrafting.recipes.add(recipe);
		}

		@Override
		public String describe() {

			return "Adding seed crafting recipe for " + recipe.getOutput().getDisplayName();
		}
	}
	
	@ZenMethod
	public static void removeRecipe(IItemStack output) {
		
		CraftTweakerAPI.apply(new Remove(CTPlugin.toStack(output)));
	}
	
	private static class Remove implements IAction {
		
		private final ItemStack output;
		List<UCrafting> removedRecipes;
		
		public Remove(ItemStack output) {
			
			this.output = output;
		}

		@Override
		public void apply() {

			removedRecipes = UCrafting.removeRecipe(output);
		}

		@Override
		public String describe() {

			return "Removing seed crafting recipe for " + output.getDisplayName();
		}
	}
}
