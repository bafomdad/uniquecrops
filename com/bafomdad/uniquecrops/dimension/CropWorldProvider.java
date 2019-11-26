package com.bafomdad.uniquecrops.dimension;

import com.bafomdad.uniquecrops.init.UCDimension;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class CropWorldProvider extends WorldProvider {

	@Override
	public DimensionType getDimensionType() {

		return UCDimension.cropworldType;
	}
	
	@Override
	public String getSaveFolder() {
		
		return "CROPWORLD";
	}
	
	@Override
	public IChunkGenerator createChunkGenerator() {
		
		return new CropWorldChunkGenerator(world);
	}
	
	@Override
    public void updateWeather() {}
	
	@Override
    public float calculateCelestialAngle(long worldTime, float partialTicks) {
		
		return 1F;
	}
	
	@Override
    public boolean canRespawnHere() {
        
		return false;
    }
}
