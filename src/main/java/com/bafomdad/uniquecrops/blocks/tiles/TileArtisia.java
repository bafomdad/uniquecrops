package com.bafomdad.uniquecrops.blocks.tiles;

import com.bafomdad.uniquecrops.api.IArtisiaRecipe;
import com.bafomdad.uniquecrops.blocks.crops.Artisia;
import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.core.enums.EnumParticle;
import com.bafomdad.uniquecrops.init.UCRecipes;
import com.bafomdad.uniquecrops.init.UCTiles;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketDispatcher;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TileArtisia extends BaseTileUC {

    public BlockPos core = BlockPos.ZERO;
    private ItemStackHandler inv = new ItemStackHandler(1);

    private static final BlockPos[] GRIDPOS = {
            new BlockPos(-1, 0, -1), new BlockPos(1, 0, 1), new BlockPos(-1, 0, 1), new BlockPos(1, 0, -1),
            new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, 0, 1)
    };

    public TileArtisia() {

        super(UCTiles.ARTISIA.get());
    }

    public void findCore() {

        boolean foundGrid = true;
        for (int i = 0; i < GRIDPOS.length; i++) {
            BlockPos looppos = getPos().add(GRIDPOS[i]);
            TileEntity te = getWorld().getTileEntity(looppos);
            if (te instanceof TileArtisia) continue;

            else {
                foundGrid = false;
                break;
            }
        }
        if (foundGrid) {
            for (int i = 0; i < GRIDPOS.length; i++) {
                BlockPos looppos = getPos().add(GRIDPOS[i]);
                TileEntity te = getWorld().getTileEntity(looppos);
                if (te instanceof TileArtisia) {
                    TileArtisia tile = (TileArtisia)te;
                    tile.setCore(this.getPos());
                }
            }
            this.setCore(getPos());
        }
    }

    public void setCore(BlockPos pos) {

        this.core = pos;
    }

    public boolean isCore() {

        return (isValid()) && (this.core.equals(getPos()));
    }

    public boolean isValid() {

        return this.core != null && !core.equals(BlockPos.ZERO);
    }

    public void setStackSpace(ItemEntity ei) {

        List<ItemStack> stacks = new ArrayList<>();
        for (int i = 0; i < GRIDPOS.length; i++) {
            BlockPos loopPos = getPos().add(GRIDPOS[i]);
            TileEntity te = getWorld().getTileEntity(loopPos);
            if (te instanceof TileArtisia) {
                TileArtisia tile = (TileArtisia)te;
                if (canAccept(loopPos)) {
                    if (!tile.getItem().isEmpty()) {
                        stacks.add(tile.getItem());
                    }
                    else if (tile.getItem().isEmpty()) {
                        ItemStack stack = ei.getItem().copy();
                        stack.setCount(1);
                        if (!getWorld().isRemote) {
                            tile.setItem(stack);
                            stacks.add(stack);
                            ei.getItem().shrink(1);
                        }
                        if (ei.getItem().getCount() <= 0) ei.remove();
                        break;
                    }
                }
            }
        }
        if (stacks.size() >= 8 && ei.isAlive()) {
            ItemStack stack = ei.getItem().copy();
            stack.setCount(1);
            if (!getWorld().isRemote) {
                this.setItem(stack);
                ei.getItem().shrink(1);
                stacks.add(stack);
            }
            if (ei.getItem().getCount() <= 0) ei.remove();

            Optional<IArtisiaRecipe> seedRecipe = world.getRecipeManager().getRecipe(UCRecipes.ARTISIA_TYPE, UCUtils.wrap(stacks), world);
            seedRecipe.ifPresent(recipe -> {
               if (!getWorld().isRemote) {
                    ItemStack output = recipe.getRecipeOutput().copy();
                    clearItems();
                    this.setItem(output);
               }
            });
        }
    }

    private boolean canAccept(BlockPos pos) {

        BlockState state = getWorld().getBlockState(pos);
        return ((Artisia)state.getBlock()).isMaxAge(state);
    }

    private void clearItems() {

        for (int i = 0; i < GRIDPOS.length; i++) {
            BlockPos looppos = getPos().add(GRIDPOS[i]);
            TileEntity te = getWorld().getTileEntity(looppos);
            if (te instanceof TileArtisia) {
                ((TileArtisia)te).setItem(ItemStack.EMPTY);
                UCPacketDispatcher.dispatchTEToNearbyPlayers(te);
                UCPacketHandler.sendToNearbyPlayers(getWorld(), looppos, new PacketUCEffect(EnumParticle.CLOUD, looppos.getX(), looppos.getY() + 0.5D, looppos.getZ(), 6));
            }
        }
    }

    public ItemStack getItem() {

        return inv.getStackInSlot(0);
    }

    public void setItem(ItemStack stack) {

        inv.setStackInSlot(0, stack);
        UCPacketDispatcher.dispatchTEToNearbyPlayers(this);
    }

    @Override
    public void writeCustomNBT(CompoundNBT tag) {

        tag.putLong("Core", core.toLong());
        tag.put("inventory", inv.serializeNBT());
    }

    @Override
    public void readCustomNBT(CompoundNBT tag) {

        this.core = BlockPos.fromLong(tag.getLong("Core"));
        inv.deserializeNBT(tag.getCompound("inventory"));
    }

    @Override
    public boolean receiveClientEvent(int id, int param) {

        return super.receiveClientEvent(id, param);
    }
}