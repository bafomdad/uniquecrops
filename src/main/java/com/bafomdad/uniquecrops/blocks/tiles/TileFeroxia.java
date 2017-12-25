package com.bafomdad.uniquecrops.blocks.tiles;

import java.util.UUID;

import net.minecraft.nbt.NBTTagCompound;

public class TileFeroxia extends TileBaseUC {

	private UUID owner;
	
	@Override
	public void readCustomNBT(NBTTagCompound tag) {
		
		this.owner = null;
		if (tag.hasKey("UC_Own"))
			owner = UUID.fromString(tag.getString("UC_Own"));
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound tag) {
		
		if (owner != null)
			tag.setString("UC_Own", owner.toString());
	}
	
	public UUID getOwner() {
		
		return owner;
	}
	
	public boolean setOwner(UUID owner) {
		
		if ((this.owner != null && !this.owner.equals(owner)) || (owner != null && !owner.equals(this.owner))) {
			
			this.owner = owner;
			if (world != null && !world.isRemote)
				markDirty();
		}
		return true;
	}
}
