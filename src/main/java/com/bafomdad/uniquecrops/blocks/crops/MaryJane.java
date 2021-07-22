package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class MaryJane extends BaseCropsBlock {

    public MaryJane() {

        super(UCItems.CINDERLEAF, UCItems.MARYJANE_SEED);
        setClickHarvest(false);
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid) {

        if (isMaxAge(state)) {
            if (player == null) return false;

            if (!player.isCreative() && (player.isBurning() && player.isImmuneToFire()) || !player.isBurning()) {
                world.setBlockState(pos.down(), Blocks.DIRT.getDefaultState(), 2);
                world.setBlockState(pos, Blocks.FIRE.getDefaultState(), 2);
                return false;
            }
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {

        if (isMaxAge(state)) return ActionResultType.PASS;

        if (player.getHeldItem(hand).getItem() == Items.BLAZE_POWDER) {
            if (!world.isRemote) {
                this.grow((ServerWorld)world, world.rand, pos, state);
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public boolean canUseBonemeal(World world, Random random, BlockPos blockPos, BlockState blockState) {

        return world.getDimensionType().isUltrawarm();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {

        if (isMaxAge(state))
            world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 0.85, pos.getZ() + 0.5, 0.0D, 0.0D, 0.0D);
    }
}
