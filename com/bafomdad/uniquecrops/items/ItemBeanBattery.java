package com.bafomdad.uniquecrops.items;

import java.text.NumberFormat;
import java.util.List;
import java.util.Random;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.core.UCEnergyStorage;
import com.bafomdad.uniquecrops.init.UCItems;

public class ItemBeanBattery extends Item {

	public ItemBeanBattery() {
		
		setRegistryName("beanpower");
		setTranslationKey(UniqueCrops.MOD_ID + ".beanpower");
		setCreativeTab(UniqueCrops.TAB);
		setMaxStackSize(1);
		UCItems.items.add(this);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
		
		if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
			IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY,null);
			if (storage != null) {
				NumberFormat format = NumberFormat.getInstance();
				tooltip.add(String.format("%s/%s Bean Power", format.format(storage.getEnergyStored()), format.format(storage.getMaxEnergyStored())));
			}
		}
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		
		return true;
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		
		if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
			IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
			if (storage != null) {
				double max = storage.getMaxEnergyStored();
				double diff = max - storage.getEnergyStored();
				
				return diff / max;
			}
		}
		return super.getDurabilityForDisplay(stack);
	}
	
    public void setEnergy(ItemStack stack, int energy) {
        
    	if (stack.hasCapability(CapabilityEnergy.ENERGY, null)){
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if (storage instanceof UCEnergyStorage) {
            	UCEnergyStorage es = (UCEnergyStorage)storage;
                es.setEnergyStored(Math.min(energy, es.getMaxEnergyStored()));
            }
        }
    }
	
    public int receiveEnergy(ItemStack stack, int maxReceive, boolean simulate) {
        
    	if (stack.hasCapability(CapabilityEnergy.ENERGY, null)){
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if(storage != null)
                return storage.receiveEnergy(maxReceive, simulate);
        }
        return 0;
    }

    public int extractEnergy(ItemStack stack, int maxExtract, boolean simulate) {
        
    	if (stack.hasCapability(CapabilityEnergy.ENERGY, null)){
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if(storage != null)
                return storage.extractEnergy(maxExtract, simulate);
        }
        return 0;
    }
	
	public int getEnergyStored(ItemStack stack) {
		
		if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
			IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
			if (storage != null)
				return storage.getEnergyStored();
		}
		return 0;
	}
	
	public int getMaxEnergyStored(ItemStack stack) {
		
		if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
			IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
			if (storage != null)
				return storage.getMaxEnergyStored();
		}
		return 0;
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound tag) {
		
		if (stack.getItem() == this) {
			return new EnergyItemProvider(stack, this);
		}
		return null;
	}
	
	private static class EnergyItemProvider implements ICapabilityProvider {

		public final UCEnergyStorage storage;
		
		public EnergyItemProvider(ItemStack stack, ItemBeanBattery item) {

			this.storage = new UCEnergyStorage(UCConfig.beanBatteryCapacity, 200) {
				@Override
				public int getEnergyStored() {
					
					if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Energy"))
						return NBTUtils.getInt(stack, "Energy", 0);
					
					return 0;
				}
				
				@Override
				public void setEnergyStored(int energy) {
					
					if (!stack.hasTagCompound())
						stack.setTagCompound(new NBTTagCompound());
					
					stack.getTagCompound().setInteger("Energy", energy);
				}
			};
		}
		
		@Override
		public boolean hasCapability(Capability<?> cap, EnumFacing facing) {

			return this.getCapability(cap, facing) != null;
		}

		@Override
		public <T> T getCapability(Capability<T> cap, EnumFacing facing) {

			if (cap == CapabilityEnergy.ENERGY)
				return (T)this.storage;

			return null;
		} 
	}
}
