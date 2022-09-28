package com.bafomdad.uniquecrops.blocks.tiles;

import com.bafomdad.uniquecrops.init.UCTiles;
import com.bafomdad.uniquecrops.network.UCPacketDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

public class TileSundial extends BaseTileUC {

    public float rotation, savedRotation;
    public int savedTime = -1;
    public boolean hasPower = false;

    public TileSundial(BlockPos pos, BlockState state) {

        super(UCTiles.SUNDIAL.get(), pos, state);
    }

    public void tickServer() {

        if (level != null && level.getGameTime() % 20 == 0) {
            boolean powered = hasPower;
            long time = level.getDayTime() % 24000L;
            float rot = level.getSunAngle(1.0F);
            int timeMod = 1500;
            if (savedTime > 0)
                hasPower = (int)(time / timeMod) == (savedTime / timeMod);
            rotation = rot;
            UCPacketDispatcher.dispatchTEToNearbyPlayers(this);
            if (powered != hasPower)
                level.updateNeighborsAt(worldPosition, getBlockState().getBlock());
        }
    }

    @Override
    public void writeCustomNBT(CompoundTag tag) {

        tag.putInt("UC_savedTime", savedTime);
        tag.putBoolean("UC_hasPower", hasPower);
        tag.putFloat("UC_savedRotation", savedRotation);
        tag.putFloat("UC_currentRotation", rotation);
    }

    @Override
    public void readCustomNBT(CompoundTag tag) {

        this.savedTime = tag.getInt("UC_savedTime");
        this.hasPower = tag.getBoolean("UC_hasPower");
        this.savedRotation = tag.getFloat("UC_savedRotation");
        this.rotation = tag.getFloat("UC_currentRotation");
    }
}
