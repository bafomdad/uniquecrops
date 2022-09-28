package com.bafomdad.uniquecrops.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface ICropPower extends INBTSerializable<CompoundTag>  {

    int getCapacity();

    int getPower();

    int getCooldown();

    boolean hasCooldown();

    boolean canAdd();

    void add(int add);

    void remove(int subtract);

    void setPower(int power);

    void setCapacity(int capacity);

    void setCooldown(int amount);

    void setIgnoreCooldown(boolean flag);
}
