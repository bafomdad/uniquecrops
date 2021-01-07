package com.bafomdad.uniquecrops.blocks.tiles;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class TileCraftyPlant extends TileBaseUC {

	private ItemStackHandler inv = new ItemStackHandler(11);
	
	public IItemHandlerModifiable getCraftingInventory() {
		
		return this.inv;
	}
	
	public int getCraftingSize() {
		
		return 9;
	}

	public ItemStack getStaff() {
		
		return this.inv.getStackInSlot(10);
	}
	
	public ItemStack getResult() {
		
		return this.inv.getStackInSlot(9);
	}
	
	public void setResult(ItemStack toSet) {
		
		this.inv.setStackInSlot(9, toSet);
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound tag) {
		
		tag.setTag("inventory", inv.serializeNBT());
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound tag) {
		
		inv.deserializeNBT(tag.getCompoundTag("inventory"));
	}
}
