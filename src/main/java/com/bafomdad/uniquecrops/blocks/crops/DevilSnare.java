package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Items;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import java.util.Random;

public class DevilSnare extends BaseCropsBlock {

    private static final double SNARE_SPEED = 0.25F;

    public DevilSnare() {

        super(() -> Items.STICK, UCItems.DEVILSNARE_SEED);
        setBonemealable(false);
        setClickHarvest(false);
    }

    @Override
    public boolean isValidGround(BlockState state, BlockGetter reader, BlockPos pos) {

        return state.is(Blocks.FARMLAND) || state.is(Blocks.DIRT) || state.is(Blocks.COARSE_DIRT);
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {

        if (!this.isMaxAge(state)) return;

        if (entity instanceof LivingEntity && ((LivingEntity)entity).getItemBySlot(EquipmentSlot.FEET).getItem() != UCItems.GLASS_SLIPPERS.get())
            entity.makeStuckInBlock(state, new Vec3(SNARE_SPEED, 1.0, SNARE_SPEED));
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random rand) {

        if (world.getBrightness(LightLayer.SKY, pos) > 7) {
            if (isMaxAge(state))
                world.setBlock(pos, this.setValueAge(0), 2);
            return;
        }
        if (isMaxAge(state))
            this.trySpread(world, pos);

        super.randomTick(state, world, pos, rand);
    }

    private void trySpread(ServerLevel world, BlockPos pos) {

        for (Direction dir : Direction.Plane.HORIZONTAL) {
            BlockPos loopPos = pos.relative(dir);
            if (world.isEmptyBlock(loopPos)) {
                BlockState toPlace = this.defaultBlockState();
                if (toPlace.canSurvive(world, loopPos) && world.random.nextInt(2) == 0) {
                    if (world.getBlockState(loopPos.below()).getBlock() != Blocks.FARMLAND)
                        world.setBlock(loopPos.below(), Blocks.FARMLAND.defaultBlockState(), 3);
                    world.setBlock(loopPos, toPlace, 3);
                    return;
                }
            }
        }
    }
}
