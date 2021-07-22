package com.bafomdad.uniquecrops.blocks.tiles;

import com.bafomdad.uniquecrops.init.UCTiles;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;

public class TileSundial extends BaseTileUC implements ITickableTileEntity {

    public float rotation, savedRotation;
    public int savedTime = -1;
    public boolean hasPower = false;

    public TileSundial() {

        super(UCTiles.SUNDIAL.get());
    }

    @Override
    public void tick() {

        if (world.getGameTime() % 20 == 0) {
            boolean powered = hasPower;
            long time = world.getDayTime() % 24000L;
            float rot = world.getCelestialAngleRadians(1.0F);
            int timeMod = 1500;
            if (savedTime > 0)
                hasPower = (int)(time / timeMod) == (savedTime / timeMod);
            rotation = rot;
            if (powered != hasPower)
                world.notifyNeighborsOfStateChange(pos, getBlockState().getBlock());
        }
    }

    @Override
    public void writeCustomNBT(CompoundNBT tag) {

        tag.putInt("UC_savedTime", savedTime);
        tag.putBoolean("UC_hasPower", hasPower);
        tag.putFloat("UC_savedRotation", savedRotation);
    }

    @Override
    public void readCustomNBT(CompoundNBT tag) {

        this.savedTime = tag.getInt("UC_savedTime");
        this.hasPower = tag.getBoolean("UC_hasPower");
        this.savedRotation = tag.getFloat("UC_savedRotation");
    }
}
