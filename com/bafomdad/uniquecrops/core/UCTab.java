package com.bafomdad.uniquecrops.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.bafomdad.uniquecrops.init.UCItems;

public class UCTab extends CreativeTabs {

	public UCTab() {
		
		super("tabUniqueCrops");
	}
	
	@Override
	public ItemStack getTabIconItem() {
		
		return new ItemStack(UCItems.generic, 1, 0);
	}
}
