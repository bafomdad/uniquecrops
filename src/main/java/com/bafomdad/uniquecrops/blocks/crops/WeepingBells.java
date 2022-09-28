package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.blocks.tiles.TileWeepingBells;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

import java.util.Random;

public class WeepingBells extends BaseCropsBlock implements EntityBlock {

    public WeepingBells() {

        super(UCItems.WEEPINGTEAR, UCItems.WEEPINGBELLS_SEED);
        setBonemealable(false);
        setIgnoreGrowthRestrictions(true);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random rand) {

        boolean flag = false;
        if (this.canIgnoreGrowthRestrictions(world, pos)) flag = true;

        BlockEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileWeepingBells && ((TileWeepingBells)tile).isLooking()) flag = true;

        if (flag) super.randomTick(state, world, pos, rand);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {

        if (!level.isClientSide()) {
            return (lvl, pos, st, te) -> {
                if (te instanceof TileWeepingBells bells) bells.tickServer();
            };
        }
        return null;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {

        return new TileWeepingBells(pos, state);
    }
}
