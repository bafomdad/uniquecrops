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
		
		CraftTweakerPlugin.LATE_ADDITIONS.add(new Add(InputHelper.toBlock(output), output.getMeta(), InputHelper.toBlock(input), input.getMeta()));
	}
	
	@ZenMethod
	public static void removeRecipe(IBlock output) {
		
		CraftTweakerPlugin.LATE_REMOVALS.add(new Remove(InputHelper.toBlock(output), output.getMeta()));
	}
	
	private static class Add extends BaseUndoable {
		
		private final Block output, input;
		private final int outputMeta, inputMeta;

		protected Add(Block output, int outputMeta, Block input, int inputMeta) {
			
			super(NAME);
			this.output = output;
			this.outputMeta = outputMeta;
			this.input = input;
			this.inputMeta = inputMeta;
		}

		@Override
		public void apply() {

			UniqueCropsAPI.HOURGLASS_RECIPE_REGISTRY.addRecipe(this.output, this.outputMeta, this.input, this.inputMeta);
		}
	}
	
	private static class Remove extends BaseUndoable {
		
		private final Block output;
		private final int outputMeta;
		
		protected Remove(Block output, int outputMeta) {
			
			super(NAME);
			this.output = output;
			this.outputMeta = outputMeta;
		}
		
		@Override
		public void apply() {
			
			UniqueCropsAPI.HOURGLASS_RECIPE_REGISTRY.removeRecipeByOutput(this.output, this.outputMeta);
		}
	}
}
