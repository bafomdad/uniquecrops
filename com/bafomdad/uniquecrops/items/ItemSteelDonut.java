package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCItems;

import net.minecraft.item.Item;

public class ItemSteelDonut extends Item {

	public ItemSteelDonut() {
		
		setRegistryName("steel_donut");
		setTranslationKey(UniqueCrops.MOD_ID + ".steeldonut");
		setCreativeTab(UniqueCrops.TAB);
		setMaxStackSize(1);
		UCItems.items.add(this);
	}
}
