package com.bafomdad.uniquecrops.blocks.supercrops;

import com.bafomdad.uniquecrops.blocks.BaseSuperCropsBlock;
import com.bafomdad.uniquecrops.blocks.tiles.TileFascino;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

public class Fascino extends BaseSuperCropsBlock {

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {

        TileEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileFascino) {
            TileFascino fe = (TileFascino)tile;
            if (fe.getStage() != TileFascino.Stage.IDLE) return ActionResultType.SUCCESS;

            ItemStack stack = player.getItemInHand(hand);
            if (stack.getItem() == UCItems.WILDWOOD_STAFF.get()) {
                if (fe.getStage() == TileFascino.Stage.IDLE) {
                    fe.checkEnchants(player, stack);
                }
                return ActionResultType.SUCCESS;
            }
            if (!stack.isEmpty() && !player.isCrouching()) {
                player.setItemInHand(hand, ItemHandlerHelper.insertItem(fe.getInventory(), stack, false));
                fe.markBlockForUpdate();
                return ActionResultType.SUCCESS;
            }
            if (stack.isEmpty() && player.isCrouching()) {
                for (int i = fe.getInventory().getSlots() - 1; i >= 0; i--) {
                    ItemStack tileStack = fe.getInventory().getStackInSlot(i);
                    if (!tileStack.isEmpty()) {
                        ItemHandlerHelper.giveItemToPlayer(player, tileStack);
                        ((ItemStackHandler)fe.getInventory()).setStackInSlot(i, ItemStack.EMPTY);
                        fe.setChanged();
                        break;
                    }
                }
                fe.markBlockForUpdate();
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }

    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {

        if (!state.is(newState.getBlock())) {
            TileEntity tileentity = world.getBlockEntity(pos);
            if (tileentity instanceof TileFascino) {
                for (int i = 0; i < ((TileFascino)tileentity).getInventory().getSlots(); i++) {
                    ItemStack stack = ((TileFascino)tileentity).getInventory().getStackInSlot(i);
                    if (!stack.isEmpty())
                        InventoryHelper.dropItemStack(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack);
                }
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state) {

        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {

        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {

        return new TileFascino();
    }
}
