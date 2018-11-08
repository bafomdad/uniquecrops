package com.bafomdad.uniquecrops.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCBlocks;

public class BlockCraftStalk extends BlockBaseStalk {

	public BlockCraftStalk() {
		
		setRegistryName("stalktop");
		setTranslationKey(UniqueCrops.MOD_ID + ".stalktop");
	}
	
	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
       
		return Item.getItemFromBlock(Blocks.CRAFTING_TABLE);
    }
	
	@Override
	protected void checkAndDropBlock(World world, BlockPos pos, IBlockState state) {
		
		if (world.isAirBlock(pos.down()))
			world.destroyBlock(pos, true);
	}
}
