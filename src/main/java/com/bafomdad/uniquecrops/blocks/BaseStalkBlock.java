package com.bafomdad.uniquecrops.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public abstract class BaseStalkBlock extends Block {

    public BaseStalkBlock(Properties prop) {

        super(prop);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {

        super.neighborChanged(state, world, pos, block, fromPos, isMoving);
        this.checkAndDropBlock(world, pos, state);
    }

    protected void checkAndDropBlock(Level world, BlockPos pos, BlockState state) {}
}
