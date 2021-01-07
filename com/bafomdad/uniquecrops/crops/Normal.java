package com.bafomdad.uniquecrops.crops;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.enums.EnumCrops;
import com.bafomdad.uniquecrops.init.UCItems;

public class Normal extends BlockCropsBase {
	
//	private Item[] croplist = new Item[] { Items.WHEAT, Items.CARROT, Items.POTATO, Items.BEETROOT, Items.MELON };
	
	private static List<Item> dropsList = new ArrayList();

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
//		return croplist[rand.nextInt(croplist.length)];
		return dropsList.get(rand.nextInt(dropsList.size()));
	}
	
	public static void initDrops(String[] items) {
		
		Stream.of(items).forEach(s -> {
			Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(s));
			if (item != null) {
				dropsList.add(item);
				return;
			}
			Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(s));
			if (block != null) {
				dropsList.add(Item.getItemFromBlock(block));
				return;
			}
		});
	}
}
