package com.bafomdad.uniquecrops.api;

import net.minecraft.item.ItemStack;

public interface IBookUpgradeable {

	public int getLevel(ItemStack stack);
	
	public void setLevel(ItemStack stack, int level);
}
