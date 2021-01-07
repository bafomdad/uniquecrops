package com.bafomdad.uniquecrops.crafting;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public class HourglassRecipe {

	private Block input, output;
	private int inputMeta, outputMeta;
	
	public HourglassRecipe(Block output, int outputMeta, Block input, int inputMeta) {
		
		this.output = output;
		this.outputMeta = outputMeta;
		this.input = input;
		this.inputMeta = inputMeta;
	}
	
	public boolean matches(IBlockState input) {

		return input.getBlock() == this.input && input.getBlock().getMetaFromState(input) == this.inputMeta;
	}
	
	public int getInputMeta() {
		
		return this.inputMeta;
	}

	public int getOutputMeta() {
		
		return this.outputMeta;
	}
	
	public Block getInput() {
		
		return this.input;
	}
	
	public Block getOutput() {
		
		return this.output;
	}
}
