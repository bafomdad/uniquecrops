package com.bafomdad.uniquecrops.blocks.tiles;

import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCTiles;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

public class TileLacusia extends BaseTileUC {

    private final ItemStackHandler inv = new ItemStackHandler(1);
    private int dir;
    private final int waitTime = 10;
    private final int waitTimeStuck = 20;
    private final Direction[] REVERSE = new Direction[] { Direction.EAST, Direction.NORTH, Direction.WEST, Direction.SOUTH };

    public TileLacusia() {

        super(UCTiles.LACUSIA.get());
    }

    public void updateStuff() {

        boolean hasPower = world.isBlockPowered(getPos());
        if (!world.isRemote) {
            if (this.canAdd() && hasPower) {
                TileEntity tileInv = null;
                for (Direction face : Direction.Plane.HORIZONTAL) {
                    BlockPos looppos = getPos().offset(face);
                    if (!world.isBlockLoaded(looppos)) return;

                    TileEntity tile = world.getTileEntity(looppos);
                    if (tile != null && tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, face).isPresent()) {
                        tileInv = tile;
                        dir = face.getIndex();
                        break;
                    }
                }
                if (tileInv != null) {
                    tileInv.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.byHorizontalIndex(dir))
                            .ifPresent(cap -> {
                                for (int i = 0; i < cap.getSlots(); i++) {
                                    ItemStack extract = cap.getStackInSlot(i);
                                    if (!extract.isEmpty()) {
                                        this.setItem(extract.copy());
                                        cap.extractItem(i, extract.getMaxStackSize(), false);
                                        this.markBlockForUpdate();
                                        break;
                                    }
                                }
                            });
                }
            }
            else if (!canAdd()) {
                boolean schedule = false;
                for (Direction face : Direction.Plane.HORIZONTAL) {
                    if (hasPower) face = face.getOpposite();
                    if (directionMatches(face)) continue;

                    BlockPos looppos = getPos().offset(face);
                    if (!world.isBlockLoaded(looppos)) return;

                    TileEntity tile = world.getTileEntity(looppos);
                    if (tile instanceof TileLacusia) {
                        TileLacusia lacusia = (TileLacusia)tile;
                        if (lacusia.canAdd()) {
                            lacusia.setItem(getItem());
                            this.setItem(ItemStack.EMPTY);
                            lacusia.markBlockForUpdate();
                            this.markBlockForUpdate();
                            dir = face.getIndex();
                            lacusia.dir = face.getOpposite().getIndex();
                            world.getPendingBlockTicks().scheduleTick(looppos, UCBlocks.LACUSIA_CROP.get(), waitTime);
                            break;
                        }
                        else {
                            dir = face.getIndex();
                            world.getPendingBlockTicks().scheduleTick(getPos(), UCBlocks.LACUSIA_CROP.get(), waitTimeStuck);
                        }
                    }
                    if (tile != null && tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, face).isPresent()) {
                        Direction finalFace = face;
                        tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, face).ifPresent(cap -> {
                            int amount = 0;
                            ItemStack simulate = ItemHandlerHelper.insertItem(cap, getItem().copy(), true);
                            int available = getItem().getCount() - simulate.getCount();

                            if (available >= getItem().getCount()) {
                                amount = available;
                                ItemHandlerHelper.insertItem(cap, getItem(), false);
                                this.setItem(simulate);
                                this.markBlockForUpdate();
                                dir = finalFace.getIndex();
                                if (!getItem().isEmpty())
                                    world.getPendingBlockTicks().scheduleTick(getPos(), UCBlocks.LACUSIA_CROP.get(), waitTime);
//                                break;
                            }
                            else if (available <= 0) {
                                dir = finalFace.getIndex();
                                world.getPendingBlockTicks().scheduleTick(getPos(), UCBlocks.LACUSIA_CROP.get(), waitTimeStuck);
                            }
                        });
                    }
                }
            }
        }
    }

    private boolean directionMatches(Direction facing) {

        return facing.getIndex() == dir;
    }

    public boolean canAdd() {

        return inv.getStackInSlot(0).isEmpty();
    }

    public ItemStack getItem() {

        return inv.getStackInSlot(0);
    }

    public void setItem(ItemStack toSet) {

        inv.setStackInSlot(0, toSet);
    }

    @Override
    public void writeCustomNBT(CompoundNBT tag) {

        tag.putInt("UC:facing", dir);
        tag.put("inventory", inv.serializeNBT());
    }

    @Override
    public void readCustomNBT(CompoundNBT tag) {

        dir = tag.getInt("UC:facing");
        inv.deserializeNBT(tag.getCompound("inventory"));
    }
}
