package com.bafomdad.uniquecrops.capabilities;

import com.bafomdad.uniquecrops.api.ICropPower;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CPProvider implements ICapabilitySerializable<CompoundTag> {

    public static Capability<ICropPower> CROP_POWER = CapabilityManager.get(new CapabilityToken<>(){});

    private final ICropPower crop;
    private final LazyOptional<ICropPower> instance;

    public CPProvider() {

        this.crop = new CPCapability();
        this.instance = LazyOptional.of(() -> crop);
    }

    public CPProvider(int capacity, boolean ignoreCooldown) {

        this.crop = new CPCapability();
        crop.setCapacity(capacity);
        crop.setIgnoreCooldown(ignoreCooldown);
        this.instance = LazyOptional.of(() -> crop);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {

        return CROP_POWER.orEmpty(cap, instance);
    }

    @Override
    public CompoundTag serializeNBT() {

        return crop.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

        crop.deserializeNBT(nbt);
    }
}
