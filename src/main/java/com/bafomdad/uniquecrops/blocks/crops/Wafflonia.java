package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Wafflonia extends BaseCropsBlock {

    public Wafflonia() {

        super(UCItems.UNCOOKEDWAFFLE, UCItems.WAFFLONIA_SEED);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {

        if (waffleAbout(world, state, pos))
            super.randomTick(state, world, pos, rand);
    }

    @Override
    public void performBonemeal(ServerWorld world, Random rand, BlockPos pos, BlockState state) {

        if (waffleAbout(world, state, pos))
            super.performBonemeal(world, rand, pos, state);
    }

    private boolean waffleAbout(ServerWorld world, BlockState state, BlockPos pos) {

        for (Direction dir : Direction.Plane.HORIZONTAL) {
            if (foundWaffles(world, pos, dir) == 4)
                return true;
        }
        return false;
    }

    private int foundWaffles(ServerWorld world, BlockPos pos, Direction dir) {

        AtomicInteger waffles = new AtomicInteger();
        BlockPos offset = pos;

        switch (dir) {
            case NORTH: offset = offset.north().east(); break;
            case EAST: offset = offset.east().south(); break;
            case SOUTH: offset = offset.south().west(); break;
            case WEST: offset = offset.west().north(); break;
        }
        BlockPos.betweenClosed(pos, offset).forEach(b -> {
            if (world.getBlockState(b).getBlock() == this) waffles.getAndIncrement();
        });
        return waffles.get();
    }
}
