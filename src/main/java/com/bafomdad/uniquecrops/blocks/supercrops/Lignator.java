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

    public static final VoxelShape AABB = VoxelShapes.create(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);

    public Lignator() {

        super(Properties.from(Blocks.CACTUS));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx) {

        return AABB;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {

        if (!(entity instanceof ItemEntity))
            entity.attackEntityFrom(DamageSource.CACTUS, 2.0F);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving) {

        if (world.getBlockState(pos.down()).getBlock() != this)
            world.getPendingBlockTicks().scheduleTick(pos, this, 200);
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos pos, BlockPos facingPos) {

        if (world.isAirBlock(pos.down()))
            world.destroyBlock(pos, false);

        return super.updatePostPlacement(state, facing, facingState, world, pos, facingPos);
    }

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {

        if (world.getBlockState(pos.down()).getBlock() != this) {
            for (BlockPos loopPos : BlockPos.getAllInBoxMutable(pos.add(-5, 0, -5), pos.add(5, 0, 5))) {
                if (world.getBlockState(loopPos).getBlock().isIn(BlockTags.LOGS))
                    world.destroyBlock(loopPos.toImmutable(), true);

                if (!pos.equals(loopPos) && world.getBlockState(loopPos).getBlock() == this)
                    world.destroyBlock(loopPos.toImmutable(), false);
            }
        }
        if (world.getBlockState(pos.up()).getBlock() != this && (world.isAirBlock(pos.up()) || world.getBlockState(pos.up()).getMaterial() == Material.LEAVES)) {
            growAndHarvest(world, pos.up());
            world.getPendingBlockTicks().scheduleTick(pos.up(), this, 200);
        }
    }

    private void growAndHarvest(ServerWorld world, BlockPos pos) {

        boolean grow = false;
        for (BlockPos loopPos : BlockPos.getAllInBoxMutable(pos.add(-5, 0, -5), pos.add(5, 0, 5))) {
            if (world.getBlockState(loopPos).getBlock().isIn(BlockTags.LOGS)) {
                if (!grow) grow = true;
                world.destroyBlock(loopPos.toImmutable(), true);
            }
            if (!pos.equals(loopPos) && world.getBlockState(loopPos).getBlock() == this)
                world.destroyBlock(loopPos.toImmutable(), false);
        }
        if (grow) {
            world.setBlockState(pos, this.getDefaultState(), 3);
        }
        else if (!grow) {
            int i;
            for (i = 1; world.getBlockState(pos.down(i)).getBlock() == this; ++i) {
                ;
            }
            world.destroyBlock(pos.down(i - 2), false);
            world.getPendingBlockTicks().scheduleTick(pos.down(i - 1), this, 200);
        }
    }
}
