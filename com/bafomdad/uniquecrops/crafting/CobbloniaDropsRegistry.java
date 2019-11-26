package com.bafomdad.uniquecrops.crafting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class CobbloniaDropsRegistry {

	private List<CobbloniaDrops> dropsList;
	private TreeMap<Double, ItemStack> itemDrops;
	
	double count;
	final Random rand;
	
	public CobbloniaDropsRegistry() {
		
		dropsList = new ArrayList();
		itemDrops = new TreeMap();
		
		count = 0.0;
		rand = new Random();
	}
	
	public void addDrop(ItemStack toDrop, double weightChance) {
		
		CobbloniaDrops drop = new CobbloniaDrops(toDrop, weightChance);
		
		this.dropsList.add(drop);
	}
	
	public void setupDropChances() {
		
		this.dropsList.stream().forEach(drop -> { itemDrops.put(count, drop.getDrop()); count += drop.getDropChance(); });
	}
	
	public ItemStack getRandomWeightedDrop() {
		
		if (itemDrops.size() <= 0) return new ItemStack(Blocks.COBBLESTONE);
		
		double num = rand.nextDouble() * count;
		
		Entry<Double, ItemStack> itemFloor = itemDrops.floorEntry(num);
		return itemFloor.getValue().copy();
	}
}
