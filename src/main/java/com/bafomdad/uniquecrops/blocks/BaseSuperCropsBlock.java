package com.bafomdad.uniquecrops.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class BaseSuperCropsBlock extends Block {

    public BaseSuperCropsBlock() {

        super(Properties.of(Material.PLANT).noCollission().randomTicks().strength(5.0F, 1000.0F).sound(SoundType.CROP));
    }

    public BaseSuperCropsBlock(Properties prop) {

        super(prop);
    }
}
