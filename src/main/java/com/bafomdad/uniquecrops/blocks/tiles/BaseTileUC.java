package com.bafomdad.uniquecrops.blocks.tiles;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public abstract class BaseTileUC extends BlockEntity {

    public BaseTileUC(BlockEntityType<?> type, BlockPos pos, BlockState state) {

        super(type, pos, state);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {

        writeCustomNBT(tag);
    }

    @Override
    public void load(CompoundTag tag) {

        readCustomNBT(tag);
        super.load(tag);
    }

    public void writeCustomNBT(CompoundTag tag) {}

    public void readCustomNBT(CompoundTag tag) {}

    @Override
    public CompoundTag getUpdateTag() {

        var tag = new CompoundTag();
        writeCustomNBT(tag);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {

        return ClientboundBlockEntityDataPacket.create(this);
    }

//    @Override
//    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet) {
//
//        super.onDataPacket(net, packet);
//        readCustomNBT(packet.getTag());
//    }

    public void markBlockForUpdate() {

        BlockState state = getLevel().getBlockState(getBlockPos());
        if (!getLevel().isClientSide())
            getLevel().sendBlockUpdated(getBlockPos(), state, state, Block.UPDATE_ALL);
    }

    public void markBlockForRenderUpdate() {

        getLevel().setBlocksDirty(worldPosition, getLevel().getBlockState(worldPosition), getLevel().getBlockState(worldPosition));
    }
}
