package com.bafomdad.uniquecrops.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.bafomdad.uniquecrops.init.UCItems;

public class UCTab extends CreativeTabs {

	public UCTab() {
		
		super("tabUniqueCrops");
	}
	
	@Override
	public Item getTabIconItem() {
		
		return UCItems.generic;
	}
	
	@Override
	public int getIconItemDamage() {
		
		return 0;
	}
}
