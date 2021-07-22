package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class DevilSnare extends BaseCropsBlock {

    private static final double SNARE_SPEED = 0.25F;

    public DevilSnare() {

        super(() -> Items.STICK, UCItems.DEVILSNARE_SEED);
        setBonemealable(false);
        setClickHarvest(false);
    }

    @Override
    public boolean isValidGround(BlockState state, IBlockReader reader, BlockPos pos) {

        return state.isIn(Blocks.FARMLAND) || state.isIn(Blocks.DIRT) || state.isIn(Blocks.COARSE_DIRT);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {

        if (!this.isMaxAge(state)) return;

        if (entity instanceof LivingEntity && ((LivingEntity)entity).getItemStackFromSlot(EquipmentSlotType.FEET).getItem() != UCItems.GLASS_SLIPPERS.get())
            entity.setMotionMultiplier(state, new Vector3d(SNARE_SPEED, 1.0, SNARE_SPEED));
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {

        if (world.getLightFor(LightType.SKY, pos) > 7) {
            if (isMaxAge(state))
                world.setBlockState(pos, this.withAge(0), 2);
            return;
        }
        if (isMaxAge(state))
            this.trySpread(world, pos);

        super.randomTick(state, world, pos, rand);
    }

    private void trySpread(ServerWorld world, BlockPos pos) {

        for (Direction dir : Direction.Plane.HORIZONTAL) {
            BlockPos loopPos = pos.offset(dir);
            if (world.isAirBlock(loopPos)) {
                BlockState toPlace = this.getDefaultState();
                if (toPlace.isValidPosition(world, loopPos) && world.rand.nextInt(2) == 0) {
                    if (world.getBlockState(loopPos.down()).getBlock() != Blocks.FARMLAND)
                        world.setBlockState(loopPos.down(), Blocks.FARMLAND.getDefaultState(), 3);
                    world.setBlockState(loopPos, toPlace, 3);
                    return;
                }
            }
        }
    }
}
