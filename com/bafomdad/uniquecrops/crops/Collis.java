package com.bafomdad.uniquecrops.crops;

import java.util.Random;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.EnumCrops;
import com.bafomdad.uniquecrops.core.EnumItems;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.init.UCItems;

public class Collis extends BlockCropsBase {

	public Collis() {
		
		super(EnumCrops.HIGHPLANT, true, UCConfig.cropcollis);
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsCollis;
	}
	
	@Override
	public Item getCrop() {
		
		return UCItems.generic;
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		
		if (getAge(state) < getMaxAge())
			return 0;
		
		return EnumItems.GOLDENRODS.ordinal();
	}
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		checkHighplant(world, pos, state, getAge(state));
	}
	
    @Override
    public void grow(World world, BlockPos pos, IBlockState state) {
    	
		checkHighplant(world, pos, state, getAge(state));
    }
    
	private void checkHighplant(World world, BlockPos pos, IBlockState state, int age) {
		
		int chanceByHeight = Math.round(pos.getY() / 16);
		
		if (world.getLightFromNeighbors(pos.up()) >= 9) {
			if (age < ((BlockCrops)state.getBlock()).getMaxAge() && world.rand.nextInt(16 - chanceByHeight) == 0) {
				world.setBlockState(pos, ((BlockCrops)state.getBlock()).withAge(age + 1));
			}
		}
	}
}
