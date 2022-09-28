package com.bafomdad.uniquecrops.capabilities;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.energy.EnergyStorage;

public class UCEnergy extends EnergyStorage {

    private final ItemStack stack;

    public UCEnergy(ItemStack stack, int capacity, int energy) {

        super(capacity, 100, 100, energy);
        this.stack = stack;
    }

    @Override
    public int receiveEnergy(int receive, boolean sim) {

        if (!this.canReceive()) return 0;

        int energystored = this.getEnergyStored();
        int energyreceived = Math.min(capacity - energystored, Math.min(this.maxReceive, receive));
        if (!sim)
            this.setEnergyStored(energystored + energyreceived);

        return energystored;
    }

    @Override
    public int extractEnergy(int extract, boolean sim) {

        if (!this.canExtract()) return 0;

        int energystored = this.getEnergyStored();
        int energyextracted = Math.min(energystored, Math.min(this.maxExtract, extract));
        if (!sim)
            this.setEnergyStored(energystored - energyextracted);
        return energyextracted;
    }

    @Override
    public int getEnergyStored() {

        return this.stack.getOrCreateTag().getInt("UC_Energy");
    }

    private void setEnergyStored(int amount) {

        this.stack.getOrCreateTag().putInt("UC_Energy", amount);
    }
}
