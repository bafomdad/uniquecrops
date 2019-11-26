package com.bafomdad.uniquecrops.core;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.EnergyStorage;

public class UCEnergyStorage extends EnergyStorage {

	public UCEnergyStorage(int capacity, int maxTransfer) {
		
		super(capacity, maxTransfer);
	}
	
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		
		if (!this.canReceive())
			return 0;
		
		int energy = this.getEnergyStored();
		int energyReceived = Math.min(this.capacity - energy, Math.min(this.maxReceive, maxReceive));
		if (!simulate)
			this.setEnergyStored(energy + energyReceived);
		
		return energyReceived;
	}
	
	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		
		if (!this.canExtract())
			return 0;
		
		int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
		if (!simulate)
			this.setEnergyStored(energy - energyExtracted);
		
		return energyExtracted;
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
}
