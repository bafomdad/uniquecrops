package com.bafomdad.uniquecrops.crops;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.EnumCrops;
import com.bafomdad.uniquecrops.core.EnumItems;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

public class Dirigible extends BlockCropsBase {

	public Dirigible() {
		
		super(EnumCrops.FLYINGPLANT, false, UCConfig.cropdirigible);
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsDirigible;
	}
	
	@Override
	public Item getCrop() {
		
		return UCItems.generic;
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		
		if (getAge(state) < getMaxAge())
			return 0;
		
		return EnumItems.PLUM.ordinal();
	}
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		if (world.getLightFromNeighbors(pos.up()) >= 9) {
			if ((this.getAge(state) + 1) >= getMaxAge())
			{
				EntityItem ei = new EntityItem(world, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, UCItems.generic.createStack(EnumItems.PLUM));
				if (!world.isRemote)
					world.spawnEntityInWorld(ei);
				UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticleTypes.EXPLOSION_NORMAL, pos.getX(), pos.getY(), pos.getZ(), 4));
				world.setBlockState(pos, withAge(0), 2);
				return;
			}
		}
		super.updateTick(world, pos, state, rand);
	}
	
    @Override
    public void grow(World world, BlockPos pos, IBlockState state) {
    	
        int i = this.getAge(state) + this.getBonemealAgeIncrease(world);
        int j = this.getMaxAge();
        
    	if (i > (j - 1))
    		i = (j - 1);
	    else if (i > j)
	    	i = j;

	    world.setBlockState(pos, this.withAge(i), 2);
    }
}
