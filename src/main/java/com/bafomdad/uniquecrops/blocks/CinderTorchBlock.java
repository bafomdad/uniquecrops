package com.bafomdad.uniquecrops.blocks;

import com.bafomdad.uniquecrops.core.VSUtils;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public class CinderTorchBlock extends Block implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = DirectionalBlock.FACING;
    public static final VoxelShape SIDE_AABB = Shapes.box(0.275D, 0.275D, 0.35D, 0.725D, 0.725D, 1D);
    public static final VoxelShape UP_AABB = Shapes.box(0.275D, 0.55D, 0.275D, 0.725D, 1D, 0.725D);
    public static final VoxelShape DOWN_AABB = Shapes.box(0.275D, 0.0D, 0.275D, 0.725D, 0.45D, 0.725D);
    public static final VoxelShape[] TORCH_AABB = new VoxelShape[] {
            DOWN_AABB,
            UP_AABB,
            VSUtils.rotateShape(Direction.SOUTH, Direction.NORTH, SIDE_AABB),
            SIDE_AABB,
            VSUtils.rotateShape(Direction.SOUTH, Direction.WEST, SIDE_AABB),
            VSUtils.rotateShape(Direction.SOUTH, Direction.EAST, SIDE_AABB)
    };

    public CinderTorchBlock() {

        super(Properties.of(Material.SAND).strength(0.01F, 0.1F).sound(SoundType.STONE).lightLevel(s -> 15));
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.DOWN).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {

        super.createBlockStateDefinition(builder);
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {

        Direction dir = state.getValue(FACING);
        return TORCH_AABB[dir.ordinal()];
    }

    @Override
    public FluidState getFluidState(BlockState state) {

        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {

        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        return this.defaultBlockState().setValue(FACING, ctx.getClickedFace().getOpposite()).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }
}
