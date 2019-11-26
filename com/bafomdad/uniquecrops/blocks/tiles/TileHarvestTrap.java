package com.bafomdad.uniquecrops.blocks.tiles;

import java.awt.Color;
import java.util.Iterator;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileHarvestTrap extends TileBaseRenderUC implements ITickable {
	
	private boolean hasSpirit;
	private boolean collectedSpirit;
	private int spiritTime = 0;
	
	@Override
	public void update() {
		
		if (spiritTime <= 0) return;
		
		if (collectedSpirit && world.getTotalWorldTime() % 20 == 0) {
			tickCropGrowth();
			spiritTime--;
			if (spiritTime <= 0) {
				this.collectedSpirit = false;
				this.markBlockForUpdate();
				return;
			}
		} else if (!collectedSpirit) {
			spiritTime--;
		}
	}
	
	public void tickCropGrowth() {
		
		if (world.isRemote) return;
		
		Iterable<BlockPos> posList = BlockPos.getAllInBox(pos.add(-5, 0, -5), pos.add(5, 1, 5));
		Iterator<BlockPos> iterator = posList.iterator();
		while (iterator.hasNext()) {
			BlockPos loopPos = iterator.next();
			IBlockState loopState = world.getBlockState(loopPos);
			if (loopState.getBlock() instanceof BlockCrops && !((BlockCrops)loopState.getBlock()).isMaxAge(loopState)) {
				world.playEvent(2005, loopPos, 0);
				loopState.getBlock().updateTick(world, loopPos, loopState, world.rand);
			}
		}
	}

	@Override
	public void writeCustomNBT(NBTTagCompound tag) {
		
		tag.setBoolean("UC:HasSpirit", this.hasSpirit);
		tag.setBoolean("UC:CollectedSpirit", this.collectedSpirit);
		tag.setInteger("UC:SpiritTime", this.spiritTime);
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound tag) {
		
		this.hasSpirit = tag.getBoolean("UC:HasSpirit");
		this.collectedSpirit = tag.getBoolean("UC:CollectedSpirit");
		this.spiritTime = tag.getInteger("UC:SpiritTime");
	}
	
	public void setSpiritTime(int time) {
		
		this.spiritTime = time;
		this.markBlockForUpdate();
	}
	
	public void setCollected() {
		
		this.collectedSpirit = true;
	}
	
	public boolean hasSpirit() {
		
		return this.spiritTime > 0;
	}
	
	public boolean isCollected() {
		
		return this.collectedSpirit;
	}
	
	public float[] getSpiritColor() {
		
		return collectedSpirit ? new float[] { Color.GREEN.getRed() / 255F, Color.GREEN.getGreen() / 255F, Color.GREEN.getBlue() / 255F } : new float[] { Color.ORANGE.getRed() / 255F, Color.ORANGE.getGreen() / 255F, Color.ORANGE.getBlue() / 255F };
	}
}
