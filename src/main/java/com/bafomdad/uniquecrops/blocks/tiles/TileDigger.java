package com.bafomdad.uniquecrops.blocks.tiles;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.init.UCTiles;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class TileDigger extends BaseTileUC {

    BlockPos digPos = BlockPos.ZERO;
    boolean jobDone = false;

    public TileDigger() {

        super(UCTiles.QUARRY.get());
    }

    public boolean isJobDone() {

        return jobDone;
    }

    public boolean digBlock(World digWorld) {

        if (digPos == BlockPos.ZERO) {
            startDig(digWorld);
        }
        if (canDig(digWorld)) {
            if (digWorld.getBlockEntity(digPos) != null) {
                advance(digWorld);
                return true;
            }
            BlockState digState = digWorld.getBlockState(digPos);
            if (setQuarriedBlock(digWorld, digState)) {
                advance(digWorld);
                return true;
            }
        }
        return false;
    }

    private boolean canDig(World digWorld) {

        if (digPos == BlockPos.ZERO) return false;
        BlockState digState = digWorld.getBlockState(digPos);
        if (digState.getDestroySpeed(digWorld, digPos) < 0 || digState.getBlock() instanceof FarmlandBlock || digState.getBlock() instanceof CropsBlock || digState.getBlock() instanceof BaseCropsBlock) {
            advance(digWorld);
            return false;
        }
        ChunkPos cPos = new ChunkPos(digPos);
        if (digPos.getX() > cPos.getMaxBlockX() && digPos.getZ() > cPos.getMaxBlockZ()) {
            jobDone = true;
            return false;
        }
        return true;
    }

    private boolean setQuarriedBlock(World digWorld, BlockState digState) {

        if (digWorld.isEmptyBlock(getBlockPos().above())) {
            digWorld.destroyBlock(digPos, false);
            if (!digState.getMaterial().isLiquid())
                digWorld.setBlock(getBlockPos().above(), digState, 3);
            return true;
        }
        ItemStack digStack = digState.getBlock().getCloneItemStack(digWorld, digPos, digState);
        if (digStack.isEmpty()) return true;

        TileEntity tile = digWorld.getBlockEntity(getBlockPos().above());
        LazyOptional<IItemHandler> inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.DOWN);
        if (!inventory.isPresent()) return false;

        IItemHandler handler = inventory.resolve().get();
        if (insertQuarryItem(handler, digStack, true)) {
            insertQuarryItem(handler, digStack, false);
            digWorld.destroyBlock(digPos, false);
            return true;
        }
        return false;
    }

    private void startDig(World digWorld) {

        ChunkPos chunkPos = new ChunkPos(getBlockPos());
        digPos = new BlockPos(chunkPos.getMinBlockX(), getBlockPos().getY(), chunkPos.getMinBlockZ());
    }

    private void advance(World digWorld) {

        if (digPos.getY() >= 1) {
            digPos = digPos.below();
        }
        if (digPos.getY() < 1) {
            ChunkPos cPos = new ChunkPos(digPos);
            if (digPos.getX() < cPos.getMaxBlockX()) {
                digPos = digPos.offset(1, getBlockPos().getY(), 0);
                if (digWorld.isEmptyBlock(digPos))
                    advance(digWorld);
                return;
            }
            if (digPos.getZ() < cPos.getMaxBlockZ()) {
                digPos = digPos.offset(-15, getBlockPos().getY(), 1);
                if (digWorld.isEmptyBlock(digPos))
                    advance(digWorld);
                return;
            }
        }
    }

    private boolean insertQuarryItem(IItemHandler inv, ItemStack stack, boolean simulate) {

        return ItemHandlerHelper.insertItem(inv, stack, simulate).isEmpty();
    }

    @Override
    public void writeCustomNBT(CompoundNBT tag) {

        tag.putLong("UC:digPos", digPos.asLong());
        tag.putBoolean("UC:digJobFinished", jobDone);
    }

    @Override
    public void readCustomNBT(CompoundNBT tag) {

        digPos = BlockPos.of(tag.getLong("UC:digPos"));
        jobDone = tag.getBoolean("UC:digJobFinished");
    }
}
