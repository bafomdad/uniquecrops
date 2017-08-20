package com.bafomdad.uniquecrops.crops;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.EnumCrops;
import com.bafomdad.uniquecrops.core.EnumItems;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.init.UCItems;

public class Invisibilia extends BlockCropsBase {

	public Invisibilia() {
		
		super(EnumCrops.INVISIBLEPLANT, true, UCConfig.cropInvisibilia);
		this.clickHarvest = false;
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsInvisibilia;
	}
	
	@Override
	public Item getCrop() {
		
		return UCItems.generic;
	}
	
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        
    	if (this.getAge(state) < getMaxAge())
    		return 1;
    	
    	return random.nextInt(2) + 1;
    }
	
	@Override
	public int damageDropped(IBlockState state) {
		
		if (getAge(state) < getMaxAge())
			return 0;
		
		return EnumItems.INVISITWINE.ordinal();
	}
	
	@Override
    public RayTraceResult collisionRayTrace(IBlockState state, World world, BlockPos pos, Vec3d start, Vec3d end) {
		
		if (!UniqueCrops.proxy.invisiTrace()) {
			return null;
		}
		return super.collisionRayTrace(state, world, pos, start, end);
	}
}
