package com.bafomdad.uniquecrops.capabilities;

import com.bafomdad.uniquecrops.api.ICropPower;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CPProvider implements ICapabilitySerializable<CompoundNBT> {

    @CapabilityInject(ICropPower.class)
    public static Capability<ICropPower> CROP_POWER = null;

    private LazyOptional<ICropPower> instance;
    private ICropPower crop;

    public CPProvider() {

        this.crop = CROP_POWER.getDefaultInstance();
        this.instance = LazyOptional.of(() -> crop);
    }

    public CPProvider(int capacity, boolean ignoreCooldown) {

        this.crop = CROP_POWER.getDefaultInstance();
        crop.setCapacity(capacity);
        crop.setIgnoreCooldown(ignoreCooldown);
        this.instance = LazyOptional.of(() -> crop);
    }

    public static void register() {

        CapabilityManager.INSTANCE.register(ICropPower.class, new Capability.IStorage<ICropPower>() {

            @Nullable
            @Override
            public CompoundNBT writeNBT(Capability<ICropPower> capability, ICropPower instance, Direction side) {

                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<ICropPower> capability, ICropPower instance, Direction side, INBT nbt) {

                if (nbt instanceof CompoundNBT)
                    instance.deserializeNBT((CompoundNBT)nbt);
            }
        }, CPCapability::new);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {

        return CROP_POWER.orEmpty(cap, instance);
    }

    @Override
    public CompoundNBT serializeNBT() {

        return crop.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {

        crop.deserializeNBT(nbt);
    }
}
