package com.bafomdad.uniquecrops.api;

import net.minecraft.world.item.ItemStack;

public interface IItemEnergy {

    int receiveEnergy(ItemStack stack, int maxReceive, boolean sim);

    int extractEnergy(ItemStack stack, int maxExtract, boolean sim);

    int getEnergy(ItemStack stack);

    int getCapacity(ItemStack stack);
}
