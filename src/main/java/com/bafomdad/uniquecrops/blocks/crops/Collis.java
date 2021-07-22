package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class Collis extends BaseCropsBlock {

    public Collis() {

        super(UCItems.GOLDENRODS, UCItems.COLLIS_SEED);
        setIgnoreGrowthRestrictions(true);
        setIncludeSeed(false);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {

        if (this.canIgnoreGrowthRestrictions(world, pos)) {
            super.randomTick(state, world, pos, rand);
            return;
        }
        checkHighplant(world, pos, state);
    }

    @Override
    public void grow(ServerWorld world, Random rand, BlockPos pos, BlockState state) {

        checkHighplant(world, pos, state);
    }

    private void checkHighplant(World world, BlockPos pos, BlockState state) {

        int chanceByHeight = Math.round(pos.getY() / 16);

        if (world.getLight(pos.up()) >= 9) {
            if (!isMaxAge(state) && world.rand.nextInt(16 - chanceByHeight) == 0) {
                world.setBlockState(pos, this.withAge(getAge(state) + 1), 2);
            }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext ctx) {

        if (ctx.getPos().getY() < 100) return Blocks.AIR.getDefaultState();
        return super.getStateForPlacement(ctx);
    }
}
