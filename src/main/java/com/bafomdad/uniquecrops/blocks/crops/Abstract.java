package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class Abstract extends BaseCropsBlock {

    public Abstract() {

        super(UCItems.ABSTRACT, UCItems.ABSTRACT_SEED);
    }

    @Override
    public void setPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {

        if (!world.isClientSide) {
            world.removeBlock(pos, true);
            world.levelEvent(2001, pos, Block.getId(state));
        }
    }
}
