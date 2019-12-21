package com.bafomdad.uniquecrops.blocks.tiles;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ITickable;

public class TileSundial extends TileBaseRenderUC implements ITickable {
	
	public float rotation;
	public float savedRotation;
	public int savedTime = -1;
	public boolean hasPower = false;

	@Override
	public void update() {

		if (world.getTotalWorldTime() % 20 == 0) {
			boolean powered = hasPower;
			long time = world.getWorldTime() % 24000L;
			float rot = world.getCelestialAngleRadians(1.0F);
			int timeMod = 1500;
			if (savedTime > 0)
				hasPower = (int)(time / timeMod) == (savedTime / timeMod);
			rotation = rot;
			if (powered != hasPower)
				world.notifyNeighborsOfStateChange(pos, blockType, true);
		}
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound tag) {
		
		tag.setInteger("UC_savedTime", savedTime);
		tag.setBoolean("UC_hasPower", hasPower);
		tag.setFloat("UC_savedRotation", savedRotation);
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound tag) {
		
		this.savedTime = tag.getInteger("UC_savedTime");
		this.hasPower = tag.getBoolean("UC_hasPower");
		this.savedRotation = tag.getFloat("UC_savedRotation");
	}
}
