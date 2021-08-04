package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.blocks.tiles.TileArtisia;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.UCPacketDispatcher;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.Random;

public class Artisia extends BaseCropsBlock {

    public Artisia() {

        super(() -> Item.getItemFromBlock(Blocks.CRAFTING_TABLE), UCItems.ARTISIA_SEED);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {

        super.randomTick(state, world, pos, rand);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileArtisia && ((TileArtisia)te).core.equals(BlockPos.ZERO))
            ((TileArtisia)te).findCore();
    }

    @Override
    public void grow(ServerWorld world, Random rand, BlockPos pos, BlockState state) {

        super.grow(world, rand, pos, state);
        if (!world.isRemote) {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof TileArtisia && ((TileArtisia)te).core.equals(BlockPos.ZERO))
                ((TileArtisia)te).findCore();
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {

        if (!this.isMaxAge(state)) return ActionResultType.PASS;

        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileArtisia && hand == Hand.MAIN_HAND) {
            ItemStack tileStack = ((TileArtisia)te).getItem().copy();
            ((TileArtisia)te).setItem(ItemStack.EMPTY);
            ItemHandlerHelper.giveItemToPlayer(player, tileStack);
            UCPacketDispatcher.dispatchTEToNearbyPlayers(te);

            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {

        if (!(entity instanceof ItemEntity) || !isMaxAge(state)) return;

        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileArtisia) {
            if (!((TileArtisia)te).isCore()) return;
            if (!((TileArtisia)te).getItem().isEmpty()) return;

            ((TileArtisia)te).setStackSpace((ItemEntity)entity);
        }
    }

    @Override
    public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {

        if (!state.isIn(newState.getBlock())) {
            TileEntity tileentity = world.getTileEntity(pos);
            if (tileentity instanceof TileArtisia) {
                InventoryHelper.spawnItemStack(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, ((TileArtisia)tileentity).getItem());
            }
            super.onReplaced(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public boolean hasTileEntity(BlockState state) {

        return isMaxAge(state);
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {

        return new TileArtisia();
    }
}
