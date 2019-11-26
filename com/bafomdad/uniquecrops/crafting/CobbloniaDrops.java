package com.bafomdad.uniquecrops.crafting;

import net.minecraft.item.ItemStack;

public class CobbloniaDrops {

	private ItemStack toDrop;
	private double dropWeight;
	
	public CobbloniaDrops(ItemStack drop, double weight) {
		
		this.toDrop = drop;
		this.dropWeight = weight;
	}
	
	public ItemStack getDrop() {
		
		return toDrop;
	}
	
	public double getDropChance() {
		
		return dropWeight;
	}
	
	public String toString() {
		
		return this.toDrop + " / " + this.dropWeight;
	}
}
