package com.bafomdad.uniquecrops.api;

import com.bafomdad.uniquecrops.capabilities.CPCapability;
import net.minecraft.item.ItemStack;

public interface IItemBooster {

    /**
     * Return the range that will be added to the crop's search radius for players holding an item setValue the {@link CPCapability}
     */
    int getRange(ItemStack stack);

    /**
     * Return the power that will be added setValue crop power before adding it to an item setValue the {@link CPCapability}
     */
    int getPower(ItemStack stack);

    /**
     * Return the amount of capacity that will get added onto an item setValue the @CPCapability
     */
    default int getCapacity(ItemStack stack) {

        return 0;
    }
}