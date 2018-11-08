package com.bafomdad.uniquecrops.blocks.tiles;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class TileDigger extends TileBaseUC {
	
	BlockPos digPos = BlockPos.ORIGIN;
	boolean jobDone = false;
	
	public boolean isJobDone() {
		
		return jobDone;
	}
	
	public boolean digBlock(World digWorld) {
	
		if (digPos == BlockPos.ORIGIN) {
			startDig(digWorld);
		}
		if (canDig(digWorld)) {
			if (digWorld.getTileEntity(digPos) != null) {
				advance(digWorld);
				return true;
			}
			IBlockState digState = digWorld.getBlockState(digPos);
			if (setQuarriedBlock(digWorld, digState)) {
				advance(digWorld);
				return true;
			}
		}
		return false;
	}
	
	private boolean canDig(World digWorld) {

		if (digPos == BlockPos.ORIGIN) return false;
		IBlockState digState = digWorld.getBlockState(digPos);
		if (digState.getBlockHardness(digWorld, digPos) < 0 || digState.getBlock() instanceof BlockFarmland || digState.getBlock() instanceof BlockCrops) {
			advance(digWorld);
			return false;
		}
		ChunkPos cPos = new ChunkPos(digPos);
		if (digPos.getX() > cPos.getXEnd() && digPos.getZ() > cPos.getZEnd()) {
			jobDone = true;
			return false;
		}
		return true;
	}
	
	private boolean setQuarriedBlock(World digWorld, IBlockState digState) {
		
		if (digWorld.isAirBlock(getPos().up())) {
			digWorld.destroyBlock(digPos, false);
			if (!digState.getMaterial().isLiquid())
				digWorld.setBlockState(getPos().up(), digState, 3);
			return true;
		}
		ItemStack digStack = digState.getBlock().getItem(digWorld, digPos, digState);
		if (digStack.isEmpty()) return true;
		
		TileEntity tile = digWorld.getTileEntity(getPos().up());
		if (tile != null && tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN)) {
			IItemHandler inv = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
			if (insertQuarryItem(inv, digStack, true)) {
				insertQuarryItem(inv, digStack, false);
				digWorld.destroyBlock(digPos, false);
				return true;
			}
		}
		return false;
	}
	
	private void startDig(World digWorld) {
		
		ChunkPos chunkPos = new ChunkPos(getPos());
		digPos = new BlockPos(chunkPos.getXStart(), getPos().getY(), chunkPos.getZStart());
	}
	
	private void advance(World digWorld) {
		
		if (digPos.getY() >= 1) {
			digPos = digPos.down();
		}
		if (digPos.getY() < 1) {
			ChunkPos cPos = new ChunkPos(digPos);
			if (digPos.getX() < cPos.getXEnd()) {
				digPos = digPos.add(1, getPos().getY(), 0);
				if (digWorld.isAirBlock(digPos))
					advance(digWorld);
				return;
			}
			if (digPos.getZ() < cPos.getZEnd()) {
				digPos = digPos.add(-15, getPos().getY(), 1);
				if (digWorld.isAirBlock(digPos))
					advance(digWorld);
				return;
			}
		}
	}
	
	private boolean insertQuarryItem(IItemHandler inv, ItemStack stack, boolean simulate) {
		
		return ItemHandlerHelper.insertItem(inv, stack, simulate).isEmpty();
	}

	public void writeCustomNBT(NBTTagCompound tag) {
		
		tag.setLong("UC:digPos", digPos.toLong());
		tag.setBoolean("UC:digJobFinished", jobDone);
	}
	
	public void readCustomNBT(NBTTagCompound tag) {
		
		digPos = BlockPos.fromLong(tag.getLong("UC:digPos"));
		jobDone = tag.getBoolean("UC:digJobFinished");
	}
}
