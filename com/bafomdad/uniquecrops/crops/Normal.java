package com.bafomdad.uniquecrops.crops;

import java.util.Random;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.EnumCrops;
import com.bafomdad.uniquecrops.init.UCItems;

public class Normal extends BlockCropsBase {
	
	private Item[] croplist = new Item[] { Items.WHEAT, Items.CARROT, Items.POTATO, Items.BEETROOT, Items.MELON };

	public Normal() {
		
		super(EnumCrops.NORMAL, true);
	}
	
	@Override
	protected Item getSeed() {
		
		return UCItems.seedsNormal;
	}
	
	@Override
	protected Item getCrop() {
		
		Random rand = new Random();
		return croplist[rand.nextInt(croplist.length)];
	}
}
