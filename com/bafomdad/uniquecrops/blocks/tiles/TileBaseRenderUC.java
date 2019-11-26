package com.bafomdad.uniquecrops.blocks.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;

public class TileBaseRenderUC extends TileBaseUC {

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		
		super.onDataPacket(net, packet);
		if (packet != null && packet.getNbtCompound() != null)
			readCustomNBT(packet.getNbtCompound());
		
		getWorld().markBlockRangeForRenderUpdate(pos, pos);
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		
		return writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		
		NBTTagCompound nbtTag = new NBTTagCompound();
		this.writeCustomNBT(nbtTag);
		
		return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
	}
	
	public void markBlockForUpdate() {
		
		IBlockState state = getWorld().getBlockState(pos);
		getWorld().notifyBlockUpdate(pos, state, state, 3);
	}
	
	public void markBlockForRenderUpdate() {
		
		getWorld().markBlockRangeForRenderUpdate(pos, pos);
	}
}
