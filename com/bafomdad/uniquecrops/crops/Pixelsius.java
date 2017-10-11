package com.bafomdad.uniquecrops.crops;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.EnumCrops;
import com.bafomdad.uniquecrops.core.EnumItems;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.init.UCItems;

public class Pixelsius extends BlockCropsBase {

	public Pixelsius() {
		
		super(EnumCrops.PIXELS, false, UCConfig.cropPixelsius);
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsPixelsius;
	}
	
	@Override
	public Item getCrop() {
		
		return UCItems.generic;
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		
		if (getAge(state) < getMaxAge())
			return 0;
		
		return EnumItems.PIXELS.ordinal();
	}
}
