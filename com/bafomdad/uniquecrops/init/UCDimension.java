package com.bafomdad.uniquecrops.init;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.dimension.CropWorldProvider;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class UCDimension {

	public static DimensionType cropworldType;
	public static int dimID = 131;
	
	public static void init() {
		
		cropworldType = DimensionType.register(UniqueCrops.MOD_ID, "_cropworld", dimID, CropWorldProvider.class, false);
		DimensionManager.registerDimension(dimID, cropworldType);
	}
}
