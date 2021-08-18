package com.bafomdad.uniquecrops.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BaseSuperCropsBlock extends Block {

    public BaseSuperCropsBlock() {

        super(Properties.of(Material.PLANT).noCollission().randomTicks().strength(5.0F, 1000.0F).sound(SoundType.CROP));
    }

    public BaseSuperCropsBlock(Properties prop) {

        super(prop);
    }
}
