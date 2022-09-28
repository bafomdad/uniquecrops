package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.init.UCItems;

public class Adventus extends BaseCropsBlock {

    public Adventus() {

        super(UCItems.GOODIE_BAG, UCItems.ADVENTUS_SEED);
        setClickHarvest(false);
        setIncludeSeed(false);
    }
}
