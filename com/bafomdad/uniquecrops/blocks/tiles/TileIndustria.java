package com.bafomdad.uniquecrops.blocks.tiles;

import net.minecraft.block.BlockCrops;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

import com.bafomdad.uniquecrops.core.UCEnergyStorage;
import com.bafomdad.uniquecrops.init.UCBlocks;

public class TileIndustria extends TileBaseUC implements ITickable {

	UCEnergyStorage energy = new UCEnergyStorage(40000, 200);
	
	@Override
	public void update() {

		if (!world.canBlockSeeSky(pos)) return;
		
		if (!world.isRemote && world.isDaytime()) {
			if (!energy.canReceive()) return;
			
			energy.receiveEnergy(20, false);
			int age = energy.getEnergyStored() / 5000;
			if (Math.min(age, 7) != world.getBlockState(pos).getValue(BlockCrops.AGE))
				world.setBlockState(pos, UCBlocks.cropIndustria.withAge(Math.min(age, 7)));
		}
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound tag) {
		
		energy.serializeNBT(tag);
	}
	
	public void readCustomNBT(NBTTagCompound tag) {

		energy.deserializeNBT(tag);
	}
	
	@Override
	public boolean hasCapability(Capability<?> cap, EnumFacing facing) {
		
		return this.getCapability(cap, facing) != null;
	}
	
	@Override
	public <T> T getCapability(Capability<T> cap, EnumFacing facing) {
		
		if (cap == CapabilityEnergy.ENERGY)
			return (T)energy;
		
		return null;
	}
}
