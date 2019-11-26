package com.bafomdad.uniquecrops.integration.crafttweaker;

import net.minecraft.block.Block;

import com.bafomdad.uniquecrops.UniqueCropsAPI;
import com.bafomdad.uniquecrops.integration.crafttweaker.mtlib.BaseUndoable;
import com.bafomdad.uniquecrops.integration.crafttweaker.mtlib.InputHelper;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlock;

@ZenRegister
@ZenClass(ZenHourglassRecipe.NAME)
public class ZenHourglassRecipe {

	public static final String NAME = "mods.uniquecrops.HourglassConversion";
	
	@ZenMethod
	public static void addRecipe(IBlock output, IBlock input) {
		
		CraftTweakerPlugin.LATE_ADDITIONS.add(new Add(InputHelper.toBlock(output), InputHelper.toBlock(input)));
	}
	
	@ZenMethod
	public static void removeRecipe(IBlock output) {
		
		CraftTweakerPlugin.LATE_REMOVALS.add(new Remove(InputHelper.toBlock(output)));
	}
	
	private static class Add extends BaseUndoable {
		
		private final Block output, input;

		protected Add(Block output, Block input) {
			
			super(NAME);
			this.output = output;
			this.input = input;
		}

		@Override
		public void apply() {

			UniqueCropsAPI.HOURGLASS_RECIPE_REGISTRY.addRecipe(this.output, this.input);
		}
	}
	
	private static class Remove extends BaseUndoable {
		
		private final Block output;
		
		protected Remove(Block output) {
			
			super(NAME);
			this.output = output;
		}
		
		@Override
		public void apply() {
			
			UniqueCropsAPI.HOURGLASS_RECIPE_REGISTRY.removeRecipeByOutput(this.output);
		}
	}
}
