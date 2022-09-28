package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.entities.MovingCropEntity;
import com.bafomdad.uniquecrops.init.UCEntities;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {

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
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

        ItemStack stack = player.getItemInHand(hand);
        if (!stack.isEmpty() && this.isMaxAge(state)) {
            if (UCUtils.hasTag(Tags.Items.DYES_BLUE, stack.getItem()) && !isBlue(state)) {
                if (!player.isCreative())
                    stack.shrink(1);
                world.setBlock(pos, state.setValue(POLARITY, true), 2);
                return InteractionResult.SUCCESS;
            }
            if (UCUtils.hasTag(Tags.Items.DYES_RED, stack.getItem()) && isBlue(state)) {
                if (!player.isCreative())
                    stack.shrink(1);
                world.setBlock(pos, state.setValue(POLARITY, false), 2);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random rand) {

        super.randomTick(state, world, pos, rand);
        if (this.isMaxAge(state))
            magnetize(world, pos, state);
    }

    private void magnetize(ServerLevel world, BlockPos pos, BlockState state) {

        for (Direction facing : Direction.Plane.HORIZONTAL) {
            for (int i = 0; i < RANGE; i++) {
                BlockPos.MutableBlockPos loopPos = pos.relative(facing, i).mutable();
                BlockState loopState = world.getBlockState(loopPos);
                if (loopState.getBlock() == this && isOppositePolarity(state, loopState) && this.isMaxAge(loopState) && i > 1) {
                    spawnMovingCrop(world, pos, state, facing, i);
                    spawnMovingCrop(world, loopPos.immutable(), loopState, facing.getOpposite(), i);
                    break;
                }
            }
        }
    }

    private void spawnMovingCrop(ServerLevel world, BlockPos pos, BlockState state, Direction facing, int distance) {

        FallingBlockEntity fallingBlock = FallingBlockEntity.fall(world, pos, state);
        fallingBlock.setNoGravity(true);
        fallingBlock.time = 10;
        fallingBlock.fallDistance = 10;
        MovingCropEntity entity = UCEntities.MOVING_CROP.get().create(world);
        if (entity == null) return;
        entity.setPos(pos.getX() + 0.25, pos.getY(), pos.getZ() + 0.25);
        entity.setFacingAndDistance(facing, distance);

        world.removeBlock(pos, false);
        world.addFreshEntity(entity);
        world.addFreshEntity(fallingBlock);
        fallingBlock.startRiding(entity);
    }
}
