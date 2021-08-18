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
    public void performBonemeal(ServerWorld world, Random rand, BlockPos pos, BlockState state) {

        checkHighplant(world, pos, state);
    }

    private void checkHighplant(World world, BlockPos pos, BlockState state) {

        int chanceByHeight = Math.round(pos.getY() / 16);

        if (world.getLightEmission(pos.above()) >= 9) {
            if (!isMaxAge(state) && world.random.nextInt(16 - chanceByHeight) == 0) {
                world.setBlock(pos, this.setValueAge(getAge(state) + 1), 2);
            }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext ctx) {

        if (ctx.getClickedPos().getY() < 100) return Blocks.AIR.defaultBlockState();
        return super.getStateForPlacement(ctx);
    }
}
