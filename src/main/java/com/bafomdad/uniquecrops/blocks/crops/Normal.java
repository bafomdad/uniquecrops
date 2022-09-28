package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Random;

public class Normal extends BaseCropsBlock {

    static final Item[] DROPS = new Item[] { Items.WHEAT, Items.BEETROOT, Items.CARROT, Items.POTATO };

    public Normal() {

        super(() -> Items.WHEAT, UCItems.NORMAL_SEED);
    }

    @Override
    public Item getCrop() {

        return UCUtils.selectRandom(new Random(), DROPS);
    }
}
