package com.bafomdad.uniquecrops.init;

import com.bafomdad.uniquecrops.UniqueCrops;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class UCLootTables {

	public static final ResourceLocation CHEST_ISLAND = new ResourceLocation(UniqueCrops.MOD_ID, "chests/island_chest");
	
	public static void init() {
		
		LootTableList.register(CHEST_ISLAND);
	}
}
