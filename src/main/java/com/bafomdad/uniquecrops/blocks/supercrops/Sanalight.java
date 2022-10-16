package com.bafomdad.uniquecrops.blocks.supercrops;

import com.bafomdad.uniquecrops.blocks.BaseSuperCropsBlock;
import com.bafomdad.uniquecrops.init.UCBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import java.util.Random;

public class Sanalight extends BaseSuperCropsBlock {

    public Sanalight() {

        super(Properties.of(Material.DECORATION).noCollission().instabreak().sound(SoundType.CROP).lightLevel((func) -> 15));
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState newState, boolean flag) {

        world.scheduleTick(pos, this, 20);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {

        if (!world.isClientSide()) {
            BlockPos.betweenClosed(pos.offset(-15, -7, -15), pos.offset(15, 7, 15)).forEach(
                    p -> {
                        if (world.getBlockState(p).is(UCBlocks.FUNNY_LIGHT.get()))
                            world.setBlock(p, Blocks.AIR.defaultBlockState(), 2);
                    });
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, Random rand) {

        glowUp(world, pos, rand);
    }

    private void glowUp(ServerLevel world, BlockPos pos, Random rand) {

        int x = pos.getX() + rand.nextInt(16) - rand.nextInt(16);
        int y = pos.getY() + rand.nextInt(8) - rand.nextInt(8);
        int z = pos.getZ() + rand.nextInt(16) - rand.nextInt(16);

        if (y > world.getHeight())
            y = world.getHeight();

        BlockPos randPos = new BlockPos(x, y, z);
        if (world.getBlockState(randPos).isAir() && world.getBrightness(LightLayer.BLOCK, randPos) < 9)
            world.setBlock(randPos, UCBlocks.FUNNY_LIGHT.get().defaultBlockState(), 2);

        world.scheduleTick(pos, this, 20);
    }
}
