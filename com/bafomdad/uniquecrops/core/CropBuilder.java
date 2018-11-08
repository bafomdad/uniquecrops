package com.bafomdad.uniquecrops.core;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;

public final class CropBuilder {

	BlockCropsBase crop;
	
	private <E extends BlockCropsBase> CropBuilder(E crop) {
		
		this.crop = crop;
	}
	
	public static <E extends BlockCropsBase> CropBuilder create(E crop) {
		
		return new CropBuilder(crop);
	}
	
	public CropBuilder setBonemealable(boolean flag) {
		
		crop.setBonemealable(flag);
		return this;
	}
	
	public CropBuilder setClickHarvest(boolean flag) {
		
		crop.setClickHarvest(flag);
		return this;
	}
	
	public CropBuilder setExtraDrops(boolean flag) {
		
		crop.setExtraDrops(flag);
		return this;
	}
	
	public <E extends BlockCropsBase> BlockCropsBase build() {
		
		return crop;
	}
}
