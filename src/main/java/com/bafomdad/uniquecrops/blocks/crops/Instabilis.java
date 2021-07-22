package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class Instabilis extends BaseCropsBlock {

    public Instabilis() {

        super(() -> Items.AIR, UCItems.INSTABILIS_SEED);
        setClickHarvest(false);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {

        if (!canGrow(world, pos)) return;

        super.randomTick(state, world, pos, rand);
    }

    @Override
    public void grow(ServerWorld world, Random rand, BlockPos pos, BlockState state) {

        if (!canGrow(world, pos)) return;

        super.grow(world, rand, pos, state);
    }

    private boolean canGrow(ServerWorld world, BlockPos pos) {

        for (Direction dir : Direction.Plane.HORIZONTAL)
            if (world.getBlockState(pos.offset(dir)).getBlock() != this) return false;

        return true;
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid) {

        ItemStack stack = player.getHeldItemMainhand();
        if (stack.getItem() instanceof ShearsItem) {
            if (!world.isRemote) {
                if (!player.isCreative())
                    stack.attemptDamageItem(1, world.rand, (ServerPlayerEntity)player);
                if (isMaxAge(state))
                    InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(UCBlocks.DEMO_CORD.get()));
                world.removeBlock(pos, false);
            }
            return willHarvest;
        }
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            BlockPos loopPos = pos.offset(dir);
            if (world.getBlockState(loopPos).getBlock() == this) {
                world.destroyBlock(loopPos, false);
            }
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }
}
