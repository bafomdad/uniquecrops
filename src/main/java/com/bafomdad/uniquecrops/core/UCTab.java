package com.bafomdad.uniquecrops.core;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public final class UCTab extends CreativeModeTab {

    public UCTab() {

        super(UniqueCrops.MOD_ID);
    }

    @Override
    public ItemStack makeIcon() {

        return new ItemStack(UCItems.BOOK_GUIDE::get);
    }
}
