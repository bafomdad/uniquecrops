package com.bafomdad.uniquecrops.crops;


import java.util.List;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.enums.EnumCrops;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.ItemGoodieBag;
import com.google.common.collect.Lists;

public class Adventus extends BlockCropsBase {

	public Adventus() {
		
		super(EnumCrops.ADVENTUS);
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsAdventus;
	}
	
	@Override
	public Item getCrop() {
		
		return UCItems.goodieBag;
	}

	@Override
	public boolean canIncludeInBook() {

		return false;
	}
	
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
    	
    	if (!ItemGoodieBag.isHoliday())
        	return Lists.newArrayList();
    	
    	return super.getDrops(world, pos, state, fortune);
    }
}
