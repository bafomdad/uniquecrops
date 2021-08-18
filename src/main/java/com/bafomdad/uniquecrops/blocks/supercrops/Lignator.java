package com.bafomdad.uniquecrops.blocks.supercrops;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class Lignator extends Block {

    public static final VoxelShape AABB = VoxelShapes.box(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);

    public Lignator() {

        super(Properties.copy(Blocks.CACTUS));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx) {

        return AABB;
    }

    @Override
    public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {

        if (!(entity instanceof ItemEntity))
            entity.hurt(DamageSource.CACTUS, 2.0F);
    }

    @Override
    public void onPlace(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving) {

        if (world.getBlockState(pos.below()).getBlock() != this)
            world.getBlockTicks().scheduleTick(pos, this, 200);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos pos, BlockPos facingPos) {

        if (world.isEmptyBlock(pos.below()))
            world.destroyBlock(pos, false);

        return super.updateShape(state, facing, facingState, world, pos, facingPos);
    }

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {

        if (world.getBlockState(pos.below()).getBlock() != this) {
            for (BlockPos loopPos : BlockPos.betweenClosed(pos.offset(-5, 0, -5), pos.offset(5, 0, 5))) {
                if (world.getBlockState(loopPos).getBlock().is(BlockTags.LOGS))
                    world.destroyBlock(loopPos.immutable(), true);

                if (!pos.equals(loopPos) && world.getBlockState(loopPos).getBlock() == this)
                    world.destroyBlock(loopPos.immutable(), false);
            }
        }
        if (world.getBlockState(pos.above()).getBlock() != this && (world.isEmptyBlock(pos.above()) || world.getBlockState(pos.above()).getMaterial() == Material.LEAVES)) {
            growAndHarvest(world, pos.above());
            world.getBlockTicks().scheduleTick(pos.above(), this, 200);
        }
    }

    private void growAndHarvest(ServerWorld world, BlockPos pos) {

        boolean grow = false;
        for (BlockPos loopPos : BlockPos.betweenClosed(pos.offset(-5, 0, -5), pos.offset(5, 0, 5))) {
            if (world.getBlockState(loopPos).getBlock().is(BlockTags.LOGS)) {
                if (!grow) grow = true;
                world.destroyBlock(loopPos.immutable(), true);
            }
            if (!pos.equals(loopPos) && world.getBlockState(loopPos).getBlock() == this)
                world.destroyBlock(loopPos.immutable(), false);
        }
        if (grow) {
            world.setBlock(pos, this.defaultBlockState(), 3);
        }
        else if (!grow) {
            int i;
            for (i = 1; world.getBlockState(pos.below(i)).getBlock() == this; ++i) {
                ;
            }
            world.destroyBlock(pos.below(i - 2), false);
            world.getBlockTicks().scheduleTick(pos.below(i - 1), this, 200);
        }
    }
}
