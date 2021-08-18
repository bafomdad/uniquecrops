package com.bafomdad.uniquecrops.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class DemoCordBlock extends Block {

    public static final VoxelShape AABB = VoxelShapes.box(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.09375D, 0.9375D);

    public DemoCordBlock() {

        super(Properties.copy(Blocks.REDSTONE_WIRE));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx) {

        return AABB;
    }

    @Override
    public void onBlockExploded(BlockState state, World world, BlockPos pos, Explosion explosion) {

        if (!world.isClientSide) {
            world.removeBlock(pos, false);
            detonate(world, pos);
        }
    }

    @Override
    public void setPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {

        super.setPlacedBy(world, pos, state, placer, stack);
        if (!world.isClientSide) {
            if (world.getBestNeighborSignal(pos) > 0)
                detonate(world, pos);
        }
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {

        if (!world.isClientSide && world.getBestNeighborSignal(pos) > 0)
            detonate(world, pos);
    }

    private void detonate(World world, BlockPos pos) {

        world.explode(null, pos.getX(), pos.getY(), pos.getZ(), 1, Explosion.Mode.BREAK);
    }
}
