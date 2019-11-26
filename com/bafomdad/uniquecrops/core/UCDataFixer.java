package com.bafomdad.uniquecrops.core;

import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.IFixableData;
import net.minecraftforge.common.util.ModFixs;
import net.minecraftforge.fml.common.FMLCommonHandler;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.google.common.collect.ImmutableMap;

public class UCDataFixer {
	
	public static final int DATA_FIXER_VERSION = 1;

	public static void init() {
		
		ModFixs fixes = FMLCommonHandler.instance().getDataFixer().init(UniqueCrops.MOD_ID, DATA_FIXER_VERSION);
		
		fixes.registerFix(FixTypes.BLOCK_ENTITY, new UCTileFixer());
	}
	
	private static class UCTileFixer implements IFixableData {
		
		private final Map<String, String> teNames;
		
		{
			ImmutableMap.Builder<String, String> nameMap = ImmutableMap.builder();
			nameMap
				.put("minecraft:tileartisia", 		"uniquecrops:artisia")
				.put("minecraft:tileshyplant", 		"uniquecrops:shyplant")
				.put("minecraft:tilemusicaplant", 	"uniquecrops:musicaplant")
				.put("minecraft:tilecinderbella", 	"uniquecrops:cinderbella")
				.put("minecraft:tilelacusia", 		"uniquecrops:lacusia")
				.put("uc:tileindustria", 			"uniquecrops:industria")
				.put("minecraft:tileferoxia", 		"uniquecrops:feroxia")
				.put("minecraft:uctilesundial", 	"uniquecrops:sundial")
				.put("minecraft:tileabstractbarrel","uniquecrops:abstractbarrel")
				.put("uc:tilemirror", 				"uniquecrops:mirror");
			
			teNames = nameMap.build();
		}

		@Override
		public int getFixVersion() {

			return 1;
		}

		@Override
		public NBTTagCompound fixTagCompound(NBTTagCompound tag) {

			String tileLocation = tag.getString("id");
			tag.setString("id", teNames.getOrDefault(tileLocation, tileLocation));
			
			return tag;
		}
	}
}
