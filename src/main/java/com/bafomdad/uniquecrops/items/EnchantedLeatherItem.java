package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;

public class EnchantedLeatherItem extends ItemBaseUC {

    public EnchantedLeatherItem() {

        super(UCItems.unstackable().rarity(Rarity.UNCOMMON));
    }

    @Override
    public boolean hasEffect(ItemStack stack) {

        return true;
    }
}
