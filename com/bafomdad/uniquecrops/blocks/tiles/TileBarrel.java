package com.bafomdad.uniquecrops.blocks.tiles;

import javax.annotation.Nonnull;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileBarrel extends TileBaseUC {

	ItemStackHandler inv = new ItemStackHandler(100);
	
	@Override
	public void readCustomNBT(NBTTagCompound tag) {
		
		inv.deserializeNBT(tag.getCompoundTag("inventory"));
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound tag) {
		
		tag.setTag("inventory", inv.serializeNBT());
	}
	
	@Override
	public boolean hasCapability(@Nonnull Capability<?> cap, @Nonnull EnumFacing side) {
		
		return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
	}
	
	@Override
	public <T> T getCapability(@Nonnull Capability<T> cap, @Nonnull EnumFacing side) {
		
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inv);
		}
		return super.getCapability(cap, side);
	}

	public IItemHandler getInventory() {

		return inv;
	}
}
