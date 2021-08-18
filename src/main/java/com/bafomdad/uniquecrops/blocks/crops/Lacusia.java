package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.blocks.tiles.TileLacusia;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class Lacusia extends BaseCropsBlock {

    public Lacusia() {

        super(() -> Items.AIR, UCItems.LACUSIA_SEED);
        setClickHarvest(false);
    }

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {

        if (isMaxAge(state)) {
            TileEntity tile = world.getBlockEntity(pos);
            if (tile instanceof TileLacusia) {
                ((TileLacusia)tile).updateStuff();
                if (world.hasNeighborSignal(pos))
                    world.getBlockTicks().scheduleTick(pos, this, 20);
            }
        }
    }

    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {

        if (!state.is(newState.getBlock())) {
            TileEntity tileentity = world.getBlockEntity(pos);
            if (tileentity instanceof TileLacusia) {
                InventoryHelper.dropItemStack(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, ((TileLacusia)tileentity).getItem());
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos pos, BlockPos facingPos) {

        if (world instanceof World && ((World)world).hasNeighborSignal(pos))
            world.getBlockTicks().scheduleTick(pos, this, 20);

        return super.updateShape(state, facing, facingState, world, pos, facingPos);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {

        return isMaxAge(state);
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {

        return new TileLacusia();
    }
}
