package com.bafomdad.uniquecrops.blocks.tiles;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.init.UCTiles;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileIndustria extends BaseTileUC implements ITickableTileEntity {

    final UCEnergyStorage energy = new UCEnergyStorage(40000, 200);

    public TileIndustria() {

        super(UCTiles.INDUSTRIA.get());
    }

    @Override
    public void tick() {

        if (!world.canBlockSeeSky(pos)) return;

        if (!world.isRemote && world.isDaytime()) {
            if (!energy.canReceive()) return;

            energy.receiveEnergy(UCConfig.COMMON.energyPerTick.get(), false);
            int age = energy.getEnergyStored() / 5000;
            if (Math.min(age, 7) != getBlockState().get(BaseCropsBlock.AGE))
                world.setBlockState(pos, getBlockState().with(BaseCropsBlock.AGE, Math.min(age, 7)));
        }
    }

    @Override
    public void writeCustomNBT(CompoundNBT tag) {

        tag.putInt("UC:energy", this.energy.getEnergyStored());
    }

    @Override
    public void readCustomNBT(CompoundNBT tag) {

        energy.setEnergy(tag.getInt("UC:energy"));
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {

        return cap == CapabilityEnergy.ENERGY ? LazyOptional.of(() -> energy).cast() : LazyOptional.empty();
    }

    public static class UCEnergyStorage extends EnergyStorage {

        public UCEnergyStorage(int capacity, int maxTransfer) {

            super(capacity, maxTransfer);
        }

        public void setEnergy(int energy) {

            this.energy = energy;
        }
    }
}
