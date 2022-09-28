package com.bafomdad.uniquecrops.items.base;

import com.bafomdad.uniquecrops.api.IItemEnergy;
import com.bafomdad.uniquecrops.capabilities.UCEnergyImpl;
import com.bafomdad.uniquecrops.core.NBTUtils;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemEnergyUC extends ItemBaseUC implements IItemEnergy {

    public ItemEnergyUC(Properties prop) {

        super(prop);
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack stack) {

        return getEnergy(stack) > 0;
    }

    @Override
    public int getBarWidth(ItemStack stack) {

        LazyOptional<IEnergyStorage> optional = stack.getCapability(CapabilityEnergy.ENERGY);
        if (optional.isPresent()) {
            IEnergyStorage storage = optional.orElseThrow(IllegalStateException::new);
            return Math.round((float)storage.getEnergyStored() * 13.0F / (float)storage.getMaxEnergyStored());
        }
        return 0;
    }

    @Override
    public int getBarColor(@NotNull ItemStack stack) {

        return 0x03fcb6;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {

        return !ItemStack.isSame(oldStack, newStack);
    }

    public void setEnergyStored(ItemStack stack, int value) {

        NBTUtils.setInt(stack, "UC_energy", Mth.clamp(value, 0, getCapacity(stack)));
    }

    @Override
    public int receiveEnergy(ItemStack stack, int maxReceive, boolean sim) {

        int energystored = getEnergy(stack);
        int energyreceived = Math.min(getCapacity(stack) - energystored, maxReceive);
        if (!sim)
            setEnergyStored(stack, energystored + energyreceived);
        return energyreceived;
    }

    @Override
    public int extractEnergy(ItemStack stack, int maxExtract, boolean sim) {

        int energystored = getEnergy(stack);
        int energyextracted = Math.min(energystored, maxExtract);
        if (!sim)
            setEnergyStored(stack, energystored - energyextracted);
        return energyextracted;
    }

    @Override
    public int getEnergy(ItemStack stack) {

        return stack.getOrCreateTag().getInt("UC_energy");
    }

    @Override
    public int getCapacity(ItemStack stack) {

        return 500;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag tag) {

        IItemEnergy container = this;
        return new ICapabilityProvider() {
            @NotNull
            @Override
            public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {

                if (cap == CapabilityEnergy.ENERGY)
                    return LazyOptional.of(() -> new UCEnergyImpl(stack, container)).cast();
                return LazyOptional.empty();
            }
        };
    }
}
