package com.bafomdad.uniquecrops.blocks;

import com.bafomdad.uniquecrops.core.VSUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.Stats;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class DreamcatcherBlock extends Block {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final VoxelShape AABB = Shapes.box(0.1875, 0.375, 0.9325, 0.8125, 1.0, 1.0);
    public static final VoxelShape[] AABBS = new VoxelShape[] {
            AABB, // no need for down
            AABB, // no need for up
            VSUtils.rotateShape(Direction.SOUTH, Direction.NORTH, AABB),
            AABB, // already facing south
            VSUtils.rotateShape(Direction.SOUTH, Direction.WEST, AABB),
            VSUtils.rotateShape(Direction.SOUTH, Direction.EAST, AABB)
    };

    public DreamcatcherBlock() {

        super(Properties.of(Material.WOOD).strength(0.175F).noCollission().randomTicks());
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {

        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {

        Direction dir = state.getValue(FACING);
        return AABBS[dir.ordinal()];
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {

        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection());
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random rand) {

        BlockPos bedPos = pos.below();
        if (world.getBlockState(bedPos).getBlock() instanceof BedBlock) {
            if (world.getBlockState(bedPos).getValue(BedBlock.PART) == BedPart.HEAD) {
                for (ServerPlayer player : world.players()) {
                    if (bedPos.equals(player.getRespawnPosition())) {
                        ServerStatsCounter stats = player.getStats();
                        stats.setValue(player, Stats.CUSTOM.get(Stats.TIME_SINCE_REST), world.random.nextInt(100));
                    }
                }
            }
        }
    }
}
