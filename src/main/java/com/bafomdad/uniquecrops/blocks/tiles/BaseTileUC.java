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
    public CompoundNBT save(CompoundNBT tag) {

        CompoundNBT nbt = super.save(tag);
        writeCustomNBT(nbt);
        return nbt;
    }

    @Override
    public void load(BlockState state, CompoundNBT tag) {

        super.load(state, tag);
        readCustomNBT(tag);
    }

    public void writeCustomNBT(CompoundNBT tag) {}

    public void readCustomNBT(CompoundNBT tag) {}

    @Override
    public CompoundNBT getUpdateTag() {

        return save(new CompoundNBT());
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {

        CompoundNBT tag = new CompoundNBT();
        writeCustomNBT(tag);
        return new SUpdateTileEntityPacket(worldPosition, -999, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet) {

        super.onDataPacket(net, packet);
        readCustomNBT(packet.getTag());
    }

    public void markBlockForUpdate() {

        BlockState state = getLevel().getBlockState(worldPosition);
        getLevel().sendBlockUpdated(worldPosition, state, state, 3);
    }

    public void markBlockForRenderUpdate() {

        getLevel().setBlocksDirty(worldPosition, getLevel().getBlockState(worldPosition), getLevel().getBlockState(worldPosition));
    }
}
