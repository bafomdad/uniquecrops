package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.entities.MovingCropEntity;
import com.bafomdad.uniquecrops.init.UCEntities;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags;

import java.util.Random;

public class Magnes extends BaseCropsBlock {

    public static BooleanProperty POLARITY = BooleanProperty.create("polarity");
    private static final int RANGE = 7;

    public Magnes() {

        super(UCItems.FERROMAGNETICIRON, UCItems.MAGNES_SEED);
        registerDefaultState(defaultBlockState().setValue(AGE, 0).setValue(POLARITY, false));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {

        super.createBlockStateDefinition(builder);
        builder.add(POLARITY);
    }

    public boolean isBlue(BlockState state) {

        return state.getValue(POLARITY);
    }

    public boolean isOppositePolarity(BlockState state1, BlockState state2) {

        return state1.getValue(POLARITY) != state2.getValue(POLARITY);
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {

        ItemStack stack = player.getItemInHand(hand);
        if (!stack.isEmpty() && this.isMaxAge(state)) {
            if (stack.getItem().is(Tags.Items.DYES_BLUE) && !isBlue(state)) {
                if (!player.isCreative())
                    stack.shrink(1);
                world.setBlock(pos, state.setValue(POLARITY, true), 2);
                return ActionResultType.SUCCESS;
            }
            if (stack.getItem().is(Tags.Items.DYES_RED) && isBlue(state)) {
                if (!player.isCreative())
                    stack.shrink(1);
                world.setBlock(pos, state.setValue(POLARITY, false), 2);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {

        super.randomTick(state, world, pos, rand);
        if (this.isMaxAge(state))
            magnetize(world, pos, state);
    }

    private void magnetize(ServerWorld world, BlockPos pos, BlockState state) {

        for (Direction facing : Direction.Plane.HORIZONTAL) {
            for (int i = 0; i < RANGE; i++) {
                BlockPos.Mutable loopPos = pos.relative(facing, i).mutable();
                BlockState loopState = world.getBlockState(loopPos);
                if (loopState.getBlock() == this && isOppositePolarity(state, loopState) && this.isMaxAge(loopState) && i > 1) {
                    spawnMovingCrop(world, pos, state, facing, i);
                    spawnMovingCrop(world, loopPos.immutable(), loopState, facing.getOpposite(), i);
                    break;
                }
            }
        }
    }

    private void spawnMovingCrop(ServerWorld world, BlockPos pos, BlockState state, Direction facing, int distance) {

        FallingBlockEntity fallingBlock = new FallingBlockEntity(world, pos.getX(), pos.getY(), pos.getZ(), state);
        fallingBlock.setNoGravity(true);
        fallingBlock.time = 10;
        fallingBlock.fallDistance = 10;
        MovingCropEntity entity = UCEntities.MOVING_CROP.get().create(world);
        entity.setPos(pos.getX() + 0.25, pos.getY(), pos.getZ() + 0.25);
        entity.setFacingAndDistance(facing, distance);

        world.removeBlock(pos, false);
        world.addFreshEntity(entity);
        world.addFreshEntity(fallingBlock);
        fallingBlock.startRiding(entity);
    }
}
