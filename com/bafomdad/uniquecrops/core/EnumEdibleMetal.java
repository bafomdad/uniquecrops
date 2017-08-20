package com.bafomdad.uniquecrops.core;

public enum EnumEdibleMetal {

	NUGGET(2, 1.2F),
	INGOT(18, 8.4F),
	GEM(14, 12.8F);
	
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
