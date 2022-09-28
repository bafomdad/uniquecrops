package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class Abstract extends BaseCropsBlock {

    public Abstract() {

        super(UCItems.ABSTRACT, UCItems.ABSTRACT_SEED);
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {

        if (!world.isClientSide) {
            world.removeBlock(pos, true);
            world.levelEvent(2001, pos, Block.getId(state));
            UCUtils.setAbstractCropGrowth(placer, world.random.nextInt(2) + 1);
        }
    }
}
