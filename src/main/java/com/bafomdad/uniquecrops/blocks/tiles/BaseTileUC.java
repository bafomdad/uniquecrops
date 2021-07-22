package com.bafomdad.uniquecrops.blocks.tiles;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public abstract class BaseTileUC extends TileEntity {

    public BaseTileUC(TileEntityType<?> type) {

        super(type);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {

        CompoundNBT nbt = super.write(tag);
        writeCustomNBT(nbt);
        return nbt;
    }

    @Override
    public void read(BlockState state, CompoundNBT tag) {

        super.read(state, tag);
        readCustomNBT(tag);
    }

    public void writeCustomNBT(CompoundNBT tag) {}

    public void readCustomNBT(CompoundNBT tag) {}

    @Override
    public CompoundNBT getUpdateTag() {

        return write(new CompoundNBT());
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {

        CompoundNBT tag = new CompoundNBT();
        writeCustomNBT(tag);
        return new SUpdateTileEntityPacket(pos, -999, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet) {

        super.onDataPacket(net, packet);
        readCustomNBT(packet.getNbtCompound());
    }

    public void markBlockForUpdate() {

        BlockState state = getWorld().getBlockState(pos);
        getWorld().notifyBlockUpdate(pos, state, state, 3);
    }

    public void markBlockForRenderUpdate() {

        getWorld().markBlockRangeForRenderUpdate(pos, getWorld().getBlockState(pos), getWorld().getBlockState(pos));
    }
}
