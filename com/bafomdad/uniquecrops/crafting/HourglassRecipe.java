package com.bafomdad.uniquecrops.crafting;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public class HourglassRecipe {

	private Block input, output;
	
	public HourglassRecipe(Block output, Block input) {
		
		this.output = output;
		this.input = input;
	}
	
	public boolean matches(IBlockState input) {

		return input.getBlock() == this.input;
	}
	
	public Block getInput() {
		
		return this.input;
	}
	
	public Block getOutput() {
		
		return this.output;
	}
}
