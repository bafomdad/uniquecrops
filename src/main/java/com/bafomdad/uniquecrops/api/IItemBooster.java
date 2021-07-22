package com.bafomdad.uniquecrops.api;

import com.bafomdad.uniquecrops.capabilities.CPCapability;
import net.minecraft.item.ItemStack;

public interface IItemBooster {

    /**
     * Return the range that will be added to the crop's search radius for players holding an item with the {@link CPCapability}
     */
    public int getRange(ItemStack stack);

    /**
     * Return the power that will be added with crop power before adding it to an item with the {@link CPCapability}
     */
    public int getPower(ItemStack stack);

    /**
     * Return the amount of capacity that will get added onto an item with the @CPCapability
     */
    public default int getCapacity(ItemStack stack) {

        return 0;
    }
}