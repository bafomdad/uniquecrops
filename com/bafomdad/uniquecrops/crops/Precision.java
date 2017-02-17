package com.bafomdad.uniquecrops.crops;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.EnumCrops;
import com.bafomdad.uniquecrops.init.UCItems;

public class Precision extends BlockCropsBase {

	public Precision() {
		
		super(EnumCrops.PRECISION, false);
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsPrecision;
	}
	
	@Override
	public Item getCrop() {
		
		return UCItems.generic;
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		
		if (this.getAge(state) == 6)
			return 7;
		
		return 0;
	}
	
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    	
    	if (this.getAge(state) == 6)
    		return this.getCrop();

    	return this.getSeed();
    }
}
