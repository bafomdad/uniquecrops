package com.bafomdad.uniquecrops.blocks.tiles;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ITickable;

public class TileSundial extends TileBaseUC implements ITickable {
	
	public float rotation;
	public float savedRotation;
	public int savedTime = -1;
	public boolean hasPower;

	@Override
	public void update() {

		boolean powered = hasPower;
		if (world.getTotalWorldTime() % 20 == 0) {
			long time = world.getWorldTime() % 24000L;
			float rot = world.getCelestialAngle(1.0F);
			hasPower = (int)(time / 1500) == (savedTime / 1500);
			rotation = rot;
			if (powered != hasPower)
				world.notifyNeighborsOfStateChange(pos, blockType, true);
		}
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		
		if (packet != null && packet.getNbtCompound() != null)
			readCustomNBT(packet.getNbtCompound());
		
		markBlockForRenderUpdate();
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		
		return writeToNBT(new NBTTagCompound());
	}
	
	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		
		NBTTagCompound nbtTag = new NBTTagCompound();
		this.writeCustomNBT(nbtTag);
		
		return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
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
	
	public void markBlockForUpdate() {
		
		IBlockState state = getWorld().getBlockState(pos);
		getWorld().notifyBlockUpdate(pos, state, state, 3);
	}
	
	public void markBlockForRenderUpdate() {
		
		getWorld().markBlockRangeForRenderUpdate(pos, pos);
	}
}
