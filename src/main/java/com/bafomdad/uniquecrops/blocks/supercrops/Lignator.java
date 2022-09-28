package com.bafomdad.uniquecrops.blocks.supercrops;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraftforge.registries.ForgeRegistries;

public class Lignator extends Block {

    public static final VoxelShape AABB = Shapes.box(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);

    public Lignator() {

        super(Properties.copy(Blocks.CACTUS));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {

        return AABB;
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {

        if (!(entity instanceof ItemEntity))
            entity.hurt(DamageSource.CACTUS, 2.0F);
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean isMoving) {

        if (world.getBlockState(pos.below()).getBlock() != this)
            world.scheduleTick(pos, this, 200);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos pos, BlockPos facingPos) {

        if (world.isEmptyBlock(pos.below()))
            world.destroyBlock(pos, false);

        return super.updateShape(state, facing, facingState, world, pos, facingPos);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, Random rand) {

        if (world.getBlockState(pos.below()).getBlock() != this) {
            for (BlockPos loopPos : BlockPos.betweenClosed(pos.offset(-5, 0, -5), pos.offset(5, 0, 5))) {
                if (ForgeRegistries.BLOCKS.tags().getTag(BlockTags.LOGS).contains(world.getBlockState(loopPos).getBlock()))
                    world.destroyBlock(loopPos.immutable(), true);

                if (!pos.equals(loopPos) && world.getBlockState(loopPos).getBlock() == this)
                    world.destroyBlock(loopPos.immutable(), false);
            }
        }
        if (world.getBlockState(pos.above()).getBlock() != this && (world.isEmptyBlock(pos.above()) || world.getBlockState(pos.above()).getMaterial() == Material.LEAVES)) {
            growAndHarvest(world, pos.above());
            world.scheduleTick(pos.above(), this, 200);
        }
    }

    private void growAndHarvest(ServerLevel world, BlockPos pos) {

        boolean grow = false;
        for (BlockPos loopPos : BlockPos.betweenClosed(pos.offset(-5, 0, -5), pos.offset(5, 0, 5))) {

            if (ForgeRegistries.BLOCKS.tags().getTag(BlockTags.LOGS).contains(world.getBlockState(loopPos).getBlock())) {
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
            world.scheduleTick(pos.below(i - 1), this, 200);
        }
    }
}
