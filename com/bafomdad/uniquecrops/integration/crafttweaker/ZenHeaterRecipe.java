package com.bafomdad.uniquecrops.integration.crafttweaker;

import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import com.bafomdad.uniquecrops.UniqueCropsAPI;
import com.bafomdad.uniquecrops.integration.crafttweaker.mtlib.BaseUndoable;
import com.bafomdad.uniquecrops.integration.crafttweaker.mtlib.InputHelper;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;

@ZenRegister
@ZenClass(ZenHeaterRecipe.NAME)
public class ZenHeaterRecipe {

	public static final String NAME = "mods.uniquecrops.MassHeaterRecipe";
	
	@ZenMethod
	public static void addRecipe(IItemStack output, IItemStack input) {
		
		CraftTweakerPlugin.LATE_ADDITIONS.add(new Add(InputHelper.toStack(output), InputHelper.toStack(input)));
	}
	
	@ZenMethod
	public static void removeRecipe(IItemStack output) {
		
		CraftTweakerPlugin.LATE_REMOVALS.add(new Remove(InputHelper.toStack(output)));
	}
	
	private static class Add extends BaseUndoable {
		
		private final ItemStack output, input;
		
		protected Add(ItemStack output, ItemStack input) {
			
			super(NAME);
			this.output = output;
			this.input = input;
		}
		
		@Override
		public void apply() {
			
			UniqueCropsAPI.MASSHEATER_REGISTRY.addRecipe(this.output, this.input);
		}
	}
	
	private static class Remove extends BaseUndoable {
		
		private final ItemStack output;
		
		protected Remove(ItemStack output) {
			
			super(NAME);
			this.output = output;
		}
		
		@Override
		public void apply() {
			
			UniqueCropsAPI.MASSHEATER_REGISTRY.removeRecipeByOutput(this.output);
		}
	}
}
