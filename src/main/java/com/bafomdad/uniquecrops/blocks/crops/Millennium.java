package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.blocks.tiles.TileMillennium;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class Millennium extends BaseCropsBlock {

    public Millennium() {

        super(UCItems.MILLENNIUMEYE, UCItems.MILLENNIUM_SEED);
        setBonemealable(false);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {

        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileMillennium) {
            if (!world.isRemote && !isMaxAge(state)) {
                TileMillennium mill = (TileMillennium)tile;
                if (mill.isTimeEmpty()) {
                    mill.setTime();
                    return;
                }
                if (mill.calcTime() >= UCConfig.COMMON.millenniumTime.get()) {
                    float f = getGrowthChance(this, world, pos);
                    if (rand.nextInt((int)(10.0F / f) + 1) == 0) {
                        world.setBlockState(pos, this.withAge(getAge(state) + 1), 2);
                        mill.setTime();
                    }
                }
            }
        }
    }

    @Override
    public boolean hasTileEntity(BlockState state) {

        return true;
    }

    public TileEntity createTileEntity(BlockState state, IBlockReader world) {

        return new TileMillennium();
    }
}
