package com.bafomdad.uniquecrops.core.enums;

import net.minecraft.util.IStringSerializable;

public enum EnumFoodstuffs implements IStringSerializable {
	
	LARGEPLUM(1, 0.6F, true),
	TERIYAKI(16, 1.0F, false),
	HEART(0, 0F, true),
	REVERSEPOTION(0, 0F, true),
	GOLDENBREAD(4, 0.3F, false),
	DIETPILLS(-4, 0F, true),
	WAFFLE(8, 1.0F, true),
	ENNUIPOTION(0, 0F, true),
	YOGURT(3, 0.6F, false),
	EGGNOG(4, 1.2F, true);
	
	final int amount;
	final float saturation;
	final boolean alwaysEdible;

	EnumFoodstuffs(int amount, float saturation, boolean alwaysEdible) {
		
		this.amount = amount;
		this.saturation = saturation;
		this.alwaysEdible = alwaysEdible;
	}
	
	public int getAmount() {
		
		return this.amount;
	}
	
	public float getSaturation() {
		
		return this.saturation;
	}
	
	public boolean isAlwaysEdible() {
		
		return this.alwaysEdible;
	}
	
    public String getName() {
    	
    	return this.name().toLowerCase();
    }
}
