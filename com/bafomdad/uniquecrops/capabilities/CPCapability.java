package com.bafomdad.uniquecrops.capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public class CPCapability implements INBTSerializable<NBTTagCompound> {
	
	int capacity = 100;
	int currentPower = 0;
	int cooldown = 0;
	
	boolean ignoreCooldown = false;
	
	/* unimplemented
	int currentCost = 0;
	final int drainRate = 2;
	 */
	@Override
	public NBTTagCompound serializeNBT() {

		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("UC:cropPowerCapacity", capacity);
		tag.setInteger("UC:cropPowerCurrent", currentPower);
		tag.setInteger("UC:cropPowerCooldown", cooldown);
		tag.setBoolean("UC:ignoresCooldown", ignoreCooldown);
		
		return tag;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {

		this.capacity = nbt.getInteger("UC:cropPowerCapacity");
		this.currentPower = nbt.getInteger("UC:cropPowerCurrent");
		this.cooldown = nbt.getInteger("UC:cropPowerCooldown");
		this.ignoreCooldown = nbt.getBoolean("UC:ignoresCooldown");
	}
	
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
}
