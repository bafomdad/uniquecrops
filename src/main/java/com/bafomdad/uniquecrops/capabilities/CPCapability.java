package com.bafomdad.uniquecrops.capabilities;

import com.bafomdad.uniquecrops.api.ICropPower;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraftforge.common.util.INBTSerializable;

public class CPCapability implements ICropPower {

    int capacity = 100;
    int currentPower = 0;
    int cooldown = 0;

    boolean ignoreCooldown = false;

    /* unimplemented
    int currentCost = 0;
    final int drainRate = 2;
     */

    public int getCapacity() {

        return capacity;
    }

    public int getPower() {

        return currentPower;
    }

    public int getCooldown() {

        return cooldown;
    }

    public boolean hasCooldown() {

        if (ignoreCooldown) return false;

        return cooldown > 0;
    }

    public boolean canAdd() {

        return this.currentPower < this.capacity && (!hasCooldown());
    }

    public void add(int add) {

        this.setPower(Math.min(currentPower + add, this.capacity));
        this.setCooldown(5);
    }

    public void remove(int subtract) {

        setPower(Math.max(this.currentPower - subtract, 0));
    }

    public void setPower(int power) {

        this.currentPower = power;
    }

    public void setCapacity(int capacity) {

        this.capacity = capacity;
    }

    public void setCooldown(int amount) {

        this.cooldown = amount;
    }

    public void setIgnoreCooldown(boolean flag) {

        this.ignoreCooldown = flag;
    }

    @Override
    public CompoundNBT serializeNBT() {

        CompoundNBT tag = new CompoundNBT();
        tag.putInt("UC:cropPowerCapacity", capacity);
        tag.putInt("UC:cropPowerCurrent", currentPower);
        tag.putInt("UC:cropPowerCooldown", cooldown);
        tag.putBoolean("UC:hasCooldown", ignoreCooldown);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {

        this.capacity = nbt.getInt("UC:cropPowerCapacity");
        this.currentPower = nbt.getInt("UC:cropPowerCurrent");
        this.cooldown = nbt.getInt("UC:cropPowerCooldown");
        this.ignoreCooldown = nbt.getBoolean("UC:hasCooldown");
    }
}

