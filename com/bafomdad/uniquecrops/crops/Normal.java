package com.bafomdad.uniquecrops.crops;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.core.enums.EnumCrops;
import com.bafomdad.uniquecrops.init.UCItems;

public class Normal extends BlockCropsBase {
	
	private Item[] croplist = new Item[] { Items.WHEAT, Items.CARROT, Items.POTATO, Items.BEETROOT, Items.MELON };

	public Normal() {
		
		super(EnumCrops.NORMAL);
	}
	
	@Override
	protected Item getSeed() {
		
		return UCItems.seedsNormal;
	}
	
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        
    	if (this.getAge(state) < getMaxAge())
    		return 1;
    	
    	return random.nextInt(3) + 1;
    }
	
	@Override
	protected Item getCrop() {
		
		Random rand = new Random();
		return croplist[rand.nextInt(croplist.length)];
	}
}
