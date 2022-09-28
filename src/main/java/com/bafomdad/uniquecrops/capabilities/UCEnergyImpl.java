package com.bafomdad.uniquecrops.capabilities;

import com.bafomdad.uniquecrops.api.IItemEnergy;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.energy.IEnergyStorage;

public class UCEnergyImpl implements IEnergyStorage {

    public ItemStack stack;
    public IItemEnergy container;

    public UCEnergyImpl(ItemStack stack, IItemEnergy container) {

        this.stack = stack;
        this.container = container;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {

        return container.receiveEnergy(stack, maxReceive, simulate);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {

        return container.extractEnergy(stack, maxExtract, simulate);
    }

    @Override
    public int getEnergyStored() {

        return container.getEnergy(stack);
    }

    @Override
    public int getMaxEnergyStored() {

        return container.getCapacity(stack);
    }

    @Override
    public boolean canExtract() {

        return true;
    }

    @Override
    public boolean canReceive() {

        return true;
    }
}
