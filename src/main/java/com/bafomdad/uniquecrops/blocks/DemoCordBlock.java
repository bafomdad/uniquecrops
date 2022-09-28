package com.bafomdad.uniquecrops.blocks;

import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class DemoCordBlock extends Block {

    public static final VoxelShape AABB = Shapes.box(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.09375D, 0.9375D);

    public DemoCordBlock() {

        super(Properties.copy(Blocks.REDSTONE_WIRE));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {

        return AABB;
    }

    public BlockState updateShape(BlockState state, Direction dir, BlockState other, LevelAccessor world, BlockPos pos, BlockPos otherPos) {

        if (!world.isClientSide() && world.getBlockState(pos.below()).isAir() && world instanceof Level level) {
            detonate(level, pos);
            return state;
        }
        return super.updateShape(state, dir, other, world, pos, otherPos);
    }

    @Override
    public void onBlockExploded(BlockState state, Level world, BlockPos pos, Explosion explosion) {

        if (!world.isClientSide) {
            world.removeBlock(pos, false);
            detonate(world, pos);
        }
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {

        super.setPlacedBy(world, pos, state, placer, stack);
        if (!world.isClientSide) {
            if (world.getBestNeighborSignal(pos) > 0)
                detonate(world, pos);
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {

        if (!world.isClientSide && world.getBestNeighborSignal(pos) > 0)
            detonate(world, pos);
    }

    private void detonate(Level world, BlockPos pos) {

        world.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
        world.explode(null, pos.getX(), pos.getY(), pos.getZ(), 1, Explosion.BlockInteraction.BREAK);
    }
}
