package com.bafomdad.uniquecrops.core.enums;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.GrassBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.bafomdad.uniquecrops.core.UCUtils;
import net.minecraftforge.eventbus.api.Event;

public enum EnumBonemealDye {

    WHITE(Blocks.LILY_OF_THE_VALLEY.defaultBlockState(), Blocks.WHITE_TULIP.defaultBlockState()),
    ORANGE(Blocks.ORANGE_TULIP.defaultBlockState()),
    MAGENTA(Blocks.ALLIUM.defaultBlockState()) {
        @Override
        public void growFlower(World world, BlockPos pos) {
            if (world.random.nextBoolean())
                ((DoublePlantBlock)Blocks.LILAC).placeAt(world, pos, 2);
            else
                super.growFlower(world, pos);
        }
    },
    LIGHT_BLUE(Blocks.BLUE_ORCHID.defaultBlockState()),
    YELLOW(Blocks.DANDELION.defaultBlockState()) {
        @Override
        public void growFlower(World world, BlockPos pos) {
            if (world.random.nextBoolean())
                ((DoublePlantBlock)Blocks.SUNFLOWER).placeAt(world, pos, 2);
            else
                super.growFlower(world, pos);
        }
    },
    LIME(Blocks.LILY_PAD.defaultBlockState()),
    PINK(Blocks.PINK_TULIP.defaultBlockState()) {
        @Override
        public void growFlower(World world, BlockPos pos) {
            if (world.random.nextBoolean())
                ((DoublePlantBlock)Blocks.PEONY).placeAt(world, pos, 2);
            else
                super.growFlower(world, pos);
        }
    },
    GRAY,
    SILVER(Blocks.AZURE_BLUET.defaultBlockState(), Blocks.OXEYE_DAISY.defaultBlockState()),
    CYAN,
    PURPLE,
    BLUE(Blocks.CORNFLOWER.defaultBlockState()),
    BROWN,
    GREEN(Blocks.FERN.defaultBlockState()) {
        @Override
        public void growFlower(World world, BlockPos pos) {
            if (world.random.nextBoolean())
                ((DoublePlantBlock)Blocks.LARGE_FERN).placeAt(world, pos, 2);
            else
                super.growFlower(world, pos);
        }
    },
    RED(Blocks.POPPY.defaultBlockState(), Blocks.RED_TULIP.defaultBlockState()) {
        @Override
        public void growFlower(World world, BlockPos pos) {
            if (world.random.nextBoolean())
                ((DoublePlantBlock)Blocks.ROSE_BUSH).placeAt(world, pos, 2);
            else
                super.growFlower(world, pos);
        }
    },
    BLACK(Blocks.WITHER_ROSE.defaultBlockState()) {
        @Override
        public void growFlower(World world, BlockPos pos) {
            if (world.random.nextInt(1000) == 0)
                super.growFlower(world, pos);
        }
    };

    final BlockState[] states;

    EnumBonemealDye(BlockState... states) {

        this.states = states;
    }

    public Event.Result grow(World world, BlockPos pos) {

        if (states == null || states.length <= 0) return Event.Result.DENY;

        final int range = 3;
        List<BlockPos> growthSpots = new ArrayList<>();
        Iterable<BlockPos> posList = BlockPos.betweenClosed(pos.offset(-range, -1, -range), pos.offset(range, 1, range));
        for (BlockPos loopPos : posList) {
            if (loopPos.getY() < 255 && world.isEmptyBlock(loopPos) && world.isEmptyBlock(loopPos.above()) && world.getBlockState(loopPos.below()).getBlock() instanceof GrassBlock) {
                growthSpots.add(loopPos.immutable());
            }
        }
        growthSpots = UCUtils.makeCollection(growthSpots, true);
        int count = Math.min(growthSpots.size(), world.random.nextBoolean() ? 3 : 4);
        for (int i = 0; i < count; i++) {
            BlockPos flowerPos = growthSpots.get(world.random.nextInt(growthSpots.size()));
            growthSpots.remove(flowerPos);
            growFlower(world, flowerPos);
        }
        return Event.Result.ALLOW;
    }

    public void growFlower(World world, BlockPos pos) {

        world.setBlock(pos, UCUtils.selectRandom(world.random, this.states), 2);
    }
}

