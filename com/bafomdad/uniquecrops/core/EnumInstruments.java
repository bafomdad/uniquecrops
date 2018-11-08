package com.bafomdad.uniquecrops.core;

import com.bafomdad.uniquecrops.UniqueCrops;

import net.minecraft.util.ResourceLocation;

public enum EnumInstruments {
	
	HARP(new ResourceLocation(UniqueCrops.MOD_ID, "textures/gui/harp.png")), // any other blocks
	BASSDRUM(new ResourceLocation(UniqueCrops.MOD_ID, "textures/gui/bassdrum.png")), // material: stone
	SNAREDRUM(new ResourceLocation(UniqueCrops.MOD_ID, "textures/gui/snaredrum.png")), // material: sand
	HAT(new ResourceLocation(UniqueCrops.MOD_ID, "textures/gui/hat.png")), // material: glass
	DOUBLEBASS(new ResourceLocation(UniqueCrops.MOD_ID, "textures/gui/doublebass.png")), // material: wood
	FLUTE(new ResourceLocation(UniqueCrops.MOD_ID, "textures/gui/flute.png")), // clay
	BELL(new ResourceLocation(UniqueCrops.MOD_ID, "textures/gui/bell.png")), // block of gold
	GUITAR(new ResourceLocation(UniqueCrops.MOD_ID, "textures/gui/guitar.png")), // wool
	CHIME(new ResourceLocation(UniqueCrops.MOD_ID, "textures/gui/chime.png")), // packed ice
	XYLOPHONE(new ResourceLocation(UniqueCrops.MOD_ID, "textures/gui/xylophone.png")); // bone block
	
	private final ResourceLocation res;

	EnumInstruments(ResourceLocation res) {
		
		this.res = res;
	}
	
	public ResourceLocation get() {
		
		return res;
	}
}
