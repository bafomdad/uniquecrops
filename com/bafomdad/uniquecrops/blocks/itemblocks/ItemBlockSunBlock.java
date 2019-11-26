package com.bafomdad.uniquecrops.blocks.itemblocks;

import java.util.List;

import com.bafomdad.uniquecrops.core.UCStrings;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBlockSunBlock extends ItemBlock {

	public ItemBlockSunBlock(Block block) {
		
		super(block);
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> list, ITooltipFlag whatisthis) {
		
		list.add(I18n.format(UCStrings.TOOLTIP + "sunblock"));
	}
}
