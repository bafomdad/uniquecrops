package com.bafomdad.uniquecrops.blocks.tiles;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraftforge.items.ItemStackHandler;

public class TileWeatherflesia extends TileBaseRenderUC {
	
	private ItemStackHandler inv = new ItemStackHandler(1);
	private int biomeStrength = 0;

	@Override
	public void readCustomNBT(NBTTagCompound tag) {
		
		this.biomeStrength = tag.getInteger("UC:biomeStrength");
		inv.deserializeNBT(tag.getCompoundTag("inventory"));
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound tag) {
	
		tag.setInteger("UC:biomeStrength", this.biomeStrength);
		tag.setTag("inventory", inv.serializeNBT());
	}
	
	public int getBiomeStrength() {
		
		return this.biomeStrength;
	}
	
	public void tickBiomeStrength() {
		
		if (this.biomeStrength < 100)
			this.biomeStrength++;
	}
	
	public ItemStack getItem() {
		
		return inv.getStackInSlot(0);
	}
	
	public void setItem(ItemStack stack) {
		
		inv.setStackInSlot(0, stack);
	}
}
