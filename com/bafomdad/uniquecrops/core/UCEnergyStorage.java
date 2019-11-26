package com.bafomdad.uniquecrops.core;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.EnergyStorage;

public class UCEnergyStorage extends EnergyStorage {

	public UCEnergyStorage(int capacity, int maxTransfer) {
		
		super(capacity, maxTransfer);
	}
	
	public void deserializeNBT(NBTTagCompound tag) {
		
		this.setEnergyStored(tag.getInteger("Energy"));
	}
	
	public void serializeNBT(NBTTagCompound tag) {
		
		tag.setInteger("Energy", this.getEnergyStored());
	}
	
	public void setEnergyStored(int energy) {
		
		this.energy = energy;
	}
	
	public void setMaxEnergyStorage(int capacity) {
		
		this.capacity = energy;
	}
}
