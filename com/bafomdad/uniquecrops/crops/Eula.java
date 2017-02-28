package com.bafomdad.uniquecrops.crops;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.EnumCrops;
import com.bafomdad.uniquecrops.init.UCItems;

public class Eula extends BlockCropsBase {

	public Eula() {
		
		super(EnumCrops.EULA, false);
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsEula;
	}
	
	@Override
	public Item getCrop() {
		
		return UCItems.generic;
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		
		if (getAge(state) < getMaxAge())
			return 0;
		
		return 23;
	}
}
