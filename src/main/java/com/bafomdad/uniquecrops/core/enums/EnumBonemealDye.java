package com.bafomdad.uniquecrops.core.enums;

import java.util.ArrayList;
import java.util.Iterator;
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

    WHITE(Blocks.LILY_OF_THE_VALLEY.getDefaultState(), Blocks.WHITE_TULIP.getDefaultState()),
    ORANGE(Blocks.ORANGE_TULIP.getDefaultState()),
    MAGENTA(Blocks.ALLIUM.getDefaultState()) {
        @Override
        public void growFlower(World world, BlockPos pos) {
            if (world.rand.nextBoolean())
                ((DoublePlantBlock)Blocks.LILAC).placeAt(world, pos, 2);
            else
                super.growFlower(world, pos);
        }
    },
    LIGHT_BLUE(Blocks.BLUE_ORCHID.getDefaultState()),
    YELLOW(Blocks.DANDELION.getDefaultState()) {
        @Override
        public void growFlower(World world, BlockPos pos) {
            if (world.rand.nextBoolean())
                ((DoublePlantBlock)Blocks.SUNFLOWER).placeAt(world, pos, 2);
            else
                super.growFlower(world, pos);
        }
    },
    LIME(Blocks.LILY_PAD.getDefaultState()),
    PINK(Blocks.PINK_TULIP.getDefaultState()) {
        @Override
        public void growFlower(World world, BlockPos pos) {
            if (world.rand.nextBoolean())
                ((DoublePlantBlock)Blocks.PEONY).placeAt(world, pos, 2);
            else
                super.growFlower(world, pos);
        }
    },
    GRAY,
    SILVER(Blocks.AZURE_BLUET.getDefaultState(), Blocks.OXEYE_DAISY.getDefaultState()),
    CYAN,
    PURPLE,
    BLUE(Blocks.CORNFLOWER.getDefaultState()),
    BROWN,
    GREEN(Blocks.FERN.getDefaultState()) {
        @Override
        public void growFlower(World world, BlockPos pos) {
            if (world.rand.nextBoolean())
                ((DoublePlantBlock)Blocks.LARGE_FERN).placeAt(world, pos, 2);
            else
                super.growFlower(world, pos);
        }
    },
    RED(Blocks.POPPY.getDefaultState(), Blocks.RED_TULIP.getDefaultState()) {
        @Override
        public void growFlower(World world, BlockPos pos) {
            if (world.rand.nextBoolean())
                ((DoublePlantBlock)Blocks.ROSE_BUSH).placeAt(world, pos, 2);
            else
                super.growFlower(world, pos);
        }
    },
    BLACK(Blocks.WITHER_ROSE.getDefaultState()) {
        @Override
        public void growFlower(World world, BlockPos pos) {
            if (world.rand.nextInt(1000) == 0)
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
        Iterable<BlockPos> posList = BlockPos.getAllInBoxMutable(pos.add(-range, -1, -range), pos.add(range, 1, range));
        Iterator<BlockPos> posit = posList.iterator();
        while (posit.hasNext()) {
            BlockPos loopPos = posit.next();
            if (loopPos.getY() < 255 && world.isAirBlock(loopPos) && world.isAirBlock(loopPos.up()) && world.getBlockState(loopPos.down()).getBlock() instanceof GrassBlock) {
                growthSpots.add(loopPos.toImmutable());
            }
        }
        growthSpots = UCUtils.makeCollection(growthSpots, true);
        int count = Math.min(growthSpots.size(), world.rand.nextBoolean() ? 3 : 4);
        for (int i = 0; i < count; i++) {
            BlockPos flowerPos = growthSpots.get(world.rand.nextInt(growthSpots.size()));
            growthSpots.remove(flowerPos);
            growFlower(world, flowerPos);
        }
        return Event.Result.ALLOW;
    }

    public void growFlower(World world, BlockPos pos) {

        world.setBlockState(pos, UCUtils.selectRandom(world.rand, this.states), 2);
    }
}

