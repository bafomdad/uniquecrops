package com.bafomdad.uniquecrops.crops;

import java.util.Random;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.EnumCrops;
import com.bafomdad.uniquecrops.core.EnumItems;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.init.UCItems;

public class Millennium extends BlockCropsBase {

	public Millennium() {
		
		super(EnumCrops.FOREVERPLANT, true, UCConfig.cropMillennium);
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsMillennium;
	}
	
	@Override
	public Item getCrop() {
		
		return UCItems.generic;
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		
		if (getAge(state) < getMaxAge())
			return 0;
		
		return EnumItems.MILLENNIUMEYE.ordinal();
	}
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		if (getAge(state) < ((BlockCrops)state.getBlock()).getMaxAge()) {
			Random rand1 = new Random(getAge(state) + rand.nextInt());
			if (rand1.nextInt(100 * (getAge(state) + 1)) == 0 && world.rand.nextInt(2) == 0) {
				world.setBlockState(pos, ((BlockCrops)state.getBlock()).withAge(getAge(state) + 1));
			}
		}
	}
}
