package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.api.IItemBooster;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.world.item.ItemStack;

public class EmeradicDiamondItem extends ItemBaseUC implements IItemBooster {

    @Override
    public boolean isFoil(ItemStack stack) {

        return true;
    }

    @Override
    public int getRange(ItemStack stack) {

        return 5;
    }

    @Override
    public int getPower(ItemStack stack) {

        return 1;
    }
}
