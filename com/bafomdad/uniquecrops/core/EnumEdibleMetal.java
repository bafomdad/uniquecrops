package com.bafomdad.uniquecrops.core;

public enum EnumEdibleMetal {

	NUGGET(2, 1.2F),
	INGOT(10, 0.65F),
	GEM(14, 0.65F);
	
	private final int amount;
	private final float saturation;
	
	EnumEdibleMetal(int amount, float saturation) {
		
		this.amount = amount;
		this.saturation = saturation;
	}
	
	public int getAmount() {
		
		return amount;
	}
	
	public float getSaturation() {
		
		return saturation;
	}
}
