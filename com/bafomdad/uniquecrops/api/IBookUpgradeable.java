package com.bafomdad.uniquecrops.api;

import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.items.ItemGeneric;

import net.minecraft.item.ItemStack;

public interface IBookUpgradeable {

	default int getLevel(ItemStack stack) {

		if (stack.hasTagCompound() && stack.getTagCompound().hasKey(ItemGeneric.TAG_UPGRADE))
			return NBTUtils.getInt(stack, ItemGeneric.TAG_UPGRADE, -1);
		
		return -1;
	}

	default void setLevel(ItemStack stack, int level) {

		NBTUtils.setInt(stack, ItemGeneric.TAG_UPGRADE, level);
	}
	
	default boolean isMaxLevel(ItemStack stack) {
		
		return getLevel(stack) >= 10;
	}
}
