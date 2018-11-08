package com.bafomdad.uniquecrops.core;

import com.bafomdad.uniquecrops.UniqueCrops;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public enum EnumGlobe {

	DESERTTEMPLE(new ResourceLocation(UniqueCrops.MOD_ID, "structures/deserttemple"), LootTableList.CHESTS_DESERT_PYRAMID),
	DESERTWELL(new ResourceLocation(UniqueCrops.MOD_ID, "structures/desertwell"), LootTableList.CHESTS_DESERT_PYRAMID),
	DUNGEON(new ResourceLocation(UniqueCrops.MOD_ID, "structures/dungeon"), LootTableList.CHESTS_SIMPLE_DUNGEON),
	JUNGLETEMPLE(new ResourceLocation(UniqueCrops.MOD_ID, "structures/jungletemple"), LootTableList.CHESTS_JUNGLE_TEMPLE),
	MINESHAFT(new ResourceLocation(UniqueCrops.MOD_ID, "structures/mineshaft"), LootTableList.CHESTS_ABANDONED_MINESHAFT),
	STRONGHOLD(new ResourceLocation(UniqueCrops.MOD_ID, "structures/stronghold"), LootTableList.CHESTS_STRONGHOLD_CORRIDOR),
	VILLAGE(new ResourceLocation(UniqueCrops.MOD_ID, "structures/village"), LootTableList.CHESTS_VILLAGE_BLACKSMITH);
	
	final ResourceLocation res;
	final ResourceLocation loot;
	
	private EnumGlobe(ResourceLocation res, ResourceLocation lootType) {
		
		this.res = res;
		this.loot = lootType;
	}
	
	public ResourceLocation getResource() {
		
		return res;
	}
	
	public ResourceLocation getLoot() {
		
		return loot;
	}
}
