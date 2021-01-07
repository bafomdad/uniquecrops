package com.bafomdad.uniquecrops.capabilities;

import com.bafomdad.uniquecrops.UniqueCrops;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class CPProvider implements ICapabilitySerializable<NBTTagCompound> {
	
	@CapabilityInject(CPCapability.class)
	public static Capability<CPCapability> CROP_POWER = null;
	public static final ResourceLocation CP_ID = new ResourceLocation(UniqueCrops.MOD_ID, "crop_power_capability");
	
	private final CPCapability cap;
	
	public CPProvider() {
		
		cap = new CPCapability();
	}
	
	public CPProvider(int capacity, boolean ignoreCooldown) {
		
		cap = new CPCapability();
		cap.setCapacity(capacity);
		cap.setIgnoreCooldown(ignoreCooldown);
	}
	
	public static void register() {
		
		CapabilityManager.INSTANCE.register(CPCapability.class, new Capability.IStorage<CPCapability>() {
			@Override
			public NBTBase writeNBT(Capability<CPCapability> cap, CPCapability instance, EnumFacing side) {
				
				return instance.serializeNBT();
			}
			
			@Override
			public void readNBT(Capability<CPCapability> cap, CPCapability instance, EnumFacing side, NBTBase nbt) {
				
				if (nbt instanceof NBTTagCompound)
					instance.deserializeNBT((NBTTagCompound)nbt);
			}
		}, () -> new CPCapability());
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {

		return capability == CROP_POWER;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {

		return hasCapability(capability, facing) ? CROP_POWER.cast(cap) : null;
	}

	@Override
	public NBTTagCompound serializeNBT() {

		return cap.serializeNBT();
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {

		cap.deserializeNBT(nbt);
	}
}
