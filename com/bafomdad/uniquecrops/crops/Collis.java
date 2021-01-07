package com.bafomdad.uniquecrops.crops;

import java.util.Random;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.SeedBehavior;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.core.enums.EnumCrops;
import com.bafomdad.uniquecrops.init.UCItems;

public class Collis extends BlockCropsBase {

	public Collis() {
		
		super(EnumCrops.HIGHPLANT);
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
	public boolean canPlantCrop(World world, EntityPlayer player, EnumFacing side, BlockPos pos, ItemStack stack) {
		
		return pos.getY() > 100;
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		
		if (getAge(state) < getMaxAge())
			return 0;
		
		return 6;
	}
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		if (this.canIgnoreGrowthRestrictions(world, pos)) {
			super.updateTick(world, pos, state, rand);
			return;
		}
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
	
	@Override
	public boolean canBonemeal(World world, BlockPos pos) {
		
		return pos.getY() > 100;
	}
}
