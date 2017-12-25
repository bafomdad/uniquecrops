package com.bafomdad.uniquecrops.blocks.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class TileBaseUC extends TileEntity {
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		
		super.writeToNBT(tag);
		writeCustomNBT(tag);
		return tag;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		
		super.readFromNBT(tag);
		readCustomNBT(tag);
	}
	
	public void writeCustomNBT(NBTTagCompound tag) {}
	
	public void readCustomNBT(NBTTagCompound tag) {}
	
	@Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
    	
    	return oldState.getBlock() != newState.getBlock();
    }
}
