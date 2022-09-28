package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.blocks.tiles.TileArtisia;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.UCPacketDispatcher;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.Containers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.Random;

public class Artisia extends BaseCropsBlock implements EntityBlock {

    public Artisia() {

        super(() -> Item.byBlock(Blocks.CRAFTING_TABLE), UCItems.ARTISIA_SEED);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random rand) {

        super.randomTick(state, world, pos, rand);
        BlockEntity te = world.getBlockEntity(pos);
        if (te instanceof TileArtisia && ((TileArtisia)te).core.equals(BlockPos.ZERO))
            ((TileArtisia)te).findCore();
    }

    @Override
    public void performBonemeal(ServerLevel world, Random rand, BlockPos pos, BlockState state) {

        super.performBonemeal(world, rand, pos, state);
        if (!world.isClientSide) {
            BlockEntity te = world.getBlockEntity(pos);
            if (te instanceof TileArtisia && ((TileArtisia)te).core.equals(BlockPos.ZERO))
                ((TileArtisia)te).findCore();
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

        if (!this.isMaxAge(state)) return InteractionResult.PASS;

        BlockEntity te = world.getBlockEntity(pos);
        if (te instanceof TileArtisia && hand == InteractionHand.MAIN_HAND) {
            ItemStack tileStack = ((TileArtisia)te).getItem().copy();
            ((TileArtisia)te).setItem(ItemStack.EMPTY);
            ItemHandlerHelper.giveItemToPlayer(player, tileStack);
            UCPacketDispatcher.dispatchTEToNearbyPlayers(te);

            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {

        if (!(entity instanceof ItemEntity) || !isMaxAge(state)) return;

        BlockEntity te = world.getBlockEntity(pos);
        if (te instanceof TileArtisia) {
            if (!((TileArtisia)te).isCore()) return;
            if (!((TileArtisia)te).getItem().isEmpty()) return;

            ((TileArtisia)te).setStackSpace((ItemEntity)entity);
        }
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {

        if (!state.is(newState.getBlock())) {
            BlockEntity tileentity = world.getBlockEntity(pos);
            if (tileentity instanceof TileArtisia) {
                Containers.dropItemStack(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, ((TileArtisia)tileentity).getItem());
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {

        return isMaxAge(state) ? new TileArtisia(pos, state) : null;
    }
}
