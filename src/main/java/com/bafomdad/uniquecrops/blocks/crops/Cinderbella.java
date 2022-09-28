package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class Cinderbella extends BaseCropsBlock {

    public Cinderbella() {

        super(UCItems.SLIPPERGLASS, UCItems.CINDERBELLA_SEED);
        setIgnoreGrowthRestrictions(true);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {

        if (world instanceof Level && !this.canPlantOrGrow((Level)world, pos)) return false;

        return super.canSurvive(state, world, pos);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random rand) {

        if (this.canIgnoreGrowthRestrictions(world, pos) || this.canPlantOrGrow(world, pos)) {
            super.randomTick(state, world, pos, rand);
            return;
        }
        world.setBlock(pos, Blocks.DEAD_BUSH.defaultBlockState(), 2);
    }

    @Override
    public void performBonemeal(ServerLevel world, Random rand, BlockPos pos, BlockState state) {

        if (this.canIgnoreGrowthRestrictions(world, pos)) {
            super.performBonemeal(world, rand, pos, state);
            return;
        }
        if (!this.canPlantOrGrow(world, pos)) {
            world.setBlock(pos, Blocks.DEAD_BUSH.defaultBlockState(), 2);
            return;
        }
        super.performBonemeal(world, rand, pos, state);
    }

    private boolean canPlantOrGrow(Level world, BlockPos pos) {

        int time = (int)(world.getDayTime() % 24000L);
        if (time >= 18500L || time <= 12542L) return false;

        int pumpkins = 0;
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            if (isPumpkin(world.getBlockState(pos.relative(dir)))) pumpkins++;
        }
        return pumpkins >= 4;
    }

    private boolean isPumpkin(BlockState state) {

        return state.getBlock() == Blocks.PUMPKIN || state.getBlock() == Blocks.CARVED_PUMPKIN || state.getBlock() == Blocks.JACK_O_LANTERN;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, Random rand) {

        if (isMaxAge(state))
            world.addParticle(ParticleTypes.CRIT, pos.getX() + 0.5, pos.getY() + 0.85, pos.getZ() + 0.5, 0.0D, 0.0D, 0.0D);
    }
}
