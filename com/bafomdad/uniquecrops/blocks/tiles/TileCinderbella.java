package com.bafomdad.uniquecrops.blocks.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileCinderbella extends TileBaseUC {
	
	public static boolean plantedCorrect = false;
	public static long timePlanted = 0L;

	@Override
	public void readCustomNBT(NBTTagCompound tag) {
		
		this.plantedCorrect = tag.getBoolean("UC_plantedCorrect");
		this.timePlanted = tag.getLong("UC_timePlanted");
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound tag) {
		
		tag.setBoolean("UC_plantedCorrect", this.plantedCorrect);
		tag.setLong("UC_timePlanted", this.timePlanted);
	}
	
	public void setAbleToGrow(World world) {
		
		plantedCorrect = true;
		timePlanted = world.getWorldTime();
	}
}
