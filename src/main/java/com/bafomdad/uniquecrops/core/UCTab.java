package com.bafomdad.uniquecrops.core;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public final class UCTab extends ItemGroup {

    public UCTab() {

        super(UniqueCrops.MOD_ID);
    }

    @Override
    public ItemStack createIcon() {

        return new ItemStack(UCItems.BOOK_GUIDE::get);
    }
}
