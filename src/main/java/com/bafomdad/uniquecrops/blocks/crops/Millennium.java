package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.blocks.tiles.TileMillennium;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.server.level.ServerLevel;

import java.util.Random;

public class Millennium extends BaseCropsBlock implements EntityBlock {

    public Millennium() {

        super(UCItems.MILLENNIUMEYE, UCItems.MILLENNIUM_SEED);
        setBonemealable(false);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random rand) {

        BlockEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileMillennium) {
            if (!world.isClientSide && !isMaxAge(state)) {
                TileMillennium mill = (TileMillennium)tile;
                if (mill.isTimeEmpty()) {
                    mill.setTime();
                    return;
                }
                if (mill.calcTime() >= UCConfig.COMMON.millenniumTime.get()) {
                    float f = getGrowthChance(this, world, pos);
                    if (rand.nextInt((int)(10.0F / f) + 1) == 0) {
                        world.setBlock(pos, this.setValueAge(getAge(state) + 1), 2);
                        mill.setTime();
                    }
                }
            }
        }
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {

        return new TileMillennium(pos, state);
    }
}
