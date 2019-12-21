package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCItems;

import net.minecraft.item.ItemRecord;
import net.minecraft.util.SoundEvent;

public class ItemVaporRecord extends ItemRecord {

	public ItemVaporRecord(String name, SoundEvent sound) {
		
		super(UniqueCrops.MOD_ID + ":" + name, sound);
		setRegistryName("record." + name);
		setTranslationKey(UniqueCrops.MOD_ID + "." + name);
		setCreativeTab(UniqueCrops.TAB);
		UCItems.items.add(this);
	}
}
