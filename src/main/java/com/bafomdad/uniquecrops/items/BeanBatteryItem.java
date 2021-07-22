package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BeanBatteryItem extends ItemBaseUC {

    public BeanBatteryItem() {

        super(UCItems.unstackable());
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {

        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {

        stack.getCapability(CapabilityEnergy.ENERGY, null).resolve().map(energy -> {
           double max = energy.getMaxEnergyStored();
           double diff = max - energy.getEnergyStored();
           return diff / max;
        });
        return super.getDurabilityForDisplay(stack);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {

        return new EnergyItemProvider();
    }

    private static class EnergyItemProvider implements ICapabilityProvider {

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {

            return cap == CapabilityEnergy.ENERGY ? LazyOptional.of(() -> new EnergyStorage(500, 100, 100, 500)).cast() : LazyOptional.empty();
        }
    }
}
