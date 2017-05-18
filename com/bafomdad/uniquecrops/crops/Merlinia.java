package com.bafomdad.uniquecrops.crops;

import java.util.List;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.EnumCrops;
import com.bafomdad.uniquecrops.core.EnumItems;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.init.UCItems;

public class Merlinia extends BlockCropsBase {
	
	public Merlinia() {
		
		super(EnumCrops.BACKWARDSPLANT, true, UCConfig.cropmerlinia);
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsMerlinia;
	}
	
	@Override
	public Item getCrop() {
		
		return UCItems.generic;
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		
		if (getAge(state) < getMaxAge())
			return 0;
		
		return EnumItems.TIMEDUST.ordinal();
	}
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		if (world.rand.nextInt(3) == 0) {
			world.setBlockState(pos, this.withAge(Math.max(getAge(state) - 1, 0)), 2);
		}
	}
	
    @Override
    public void grow(World world, BlockPos pos, IBlockState state) {
    	
    	return;
    }
    
    public void merliniaGrowth(World world, BlockPos pos, int age) {
    	
    	IBlockState state = world.getBlockState(pos);
        int i = this.getAge(state) + this.getBonemealAgeIncrease(world);
        int j = this.getMaxAge();

        if (i > j)
        {
            i = j;
        }
        world.setBlockState(pos, this.withAge(i), 2);
    }
}
