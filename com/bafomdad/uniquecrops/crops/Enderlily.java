package com.bafomdad.uniquecrops.crops;

import java.util.Iterator;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.EnumCrops;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

public class Enderlily extends BlockCropsBase {

	public Enderlily() {
		
		super(EnumCrops.TELEPLANT);
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsEnderlily;
	}
	
	@Override
	public Item getCrop() {
		
		return UCItems.generic;
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		
		if (getAge(state) < getMaxAge())
			return 0;
		
		return 5;
	}
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		this.checkAndDropBlock(world, pos, state);
		enderlilyTele(world, pos, state, getAge(state));
	}
	
    @Override
    public void grow(World world, BlockPos pos, IBlockState state) {
    	
    	enderlilyTele(world, pos, state, getAge(state));
    }
    
    public boolean isEnderLilyGrown(World world, BlockPos pos, IBlockState state) {
    	
    	if (getAge(state) >= getMaxAge()) {
    		for (int i = 0; i <= world.rand.nextInt(1) + 1; i++) {
    			InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.ENDER_PEARL));
    		}
    		world.setBlockState(pos, this.withAge(0), 3);
    		return true;
    	}
    	return false;
    }
	
	private void enderlilyTele(World world, BlockPos pos, IBlockState state, int age) {
		
		if (world.getLightFromNeighbors(pos.up()) >= 9) {
			if (age < ((BlockCrops)state.getBlock()).getMaxAge()) {
				Iterable<BlockPos> getBox = BlockPos.getAllInBox(pos.add(-4, 0, -4), pos.add(4, 0, 4));
				Iterator it = getBox.iterator();
				while (it.hasNext()) {
					BlockPos looppos = (BlockPos)it.next();
					Block loopblock = world.getBlockState(looppos).getBlock();
					if (loopblock != null && (world.isAirBlock(looppos) || (loopblock instanceof IGrowable && loopblock != this))) {
						IBlockState savestate = world.getBlockState(looppos);
						if (world.getBlockState(looppos.add(0, -1, 0)).getBlock() == Blocks.FARMLAND && world.rand.nextInt(7) == 0)
						{
							world.setBlockState(looppos, ((BlockCrops)state.getBlock()).withAge(age + 1), 2);
							world.setBlockState(pos, savestate);
							UCPacketHandler.sendToNearbyPlayers(world, looppos, new PacketUCEffect(EnumParticleTypes.PORTAL, looppos.getX(), looppos.getY(), looppos.getZ(), 6));
							UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticleTypes.PORTAL, pos.getX(), pos.getY(), pos.getZ(), 6));
							return;
						}
					}
				}
			}
		}
	}
}
