package com.bafomdad.uniquecrops.blocks.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockLeaves extends ItemBlock {

	public ItemBlockLeaves(Block block) {
		
		super(block);
	}
	
	@Override
	public int getMetadata(int meta) {
		
		return meta | 4;
	}
}
