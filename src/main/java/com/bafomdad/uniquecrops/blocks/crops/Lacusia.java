package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.blocks.tiles.TileLacusia;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.Containers;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import java.util.Random;

public class Lacusia extends BaseCropsBlock implements EntityBlock {

    public Lacusia() {

        super(() -> Items.AIR, UCItems.LACUSIA_SEED);
        setClickHarvest(false);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, Random rand) {

        if (isMaxAge(state)) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof TileLacusia lacusia) {
                lacusia.updateStuff();
                if (world.hasNeighborSignal(pos) || !lacusia.canAdd())
                    world.scheduleTick(pos, this, 20);
            }
        }
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {

        if (!state.is(newState.getBlock())) {
            BlockEntity tileentity = world.getBlockEntity(pos);
            if (tileentity instanceof TileLacusia) {
                Containers.dropItemStack(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, ((TileLacusia)tileentity).getItem());
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos pos, BlockPos facingPos) {

        if (world instanceof Level && ((Level)world).hasNeighborSignal(pos))
            world.scheduleTick(pos, this, 20);

        return super.updateShape(state, facing, facingState, world, pos, facingPos);
    }
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {

        return isMaxAge(state) ? new TileLacusia(pos, state): null;
    }
}
