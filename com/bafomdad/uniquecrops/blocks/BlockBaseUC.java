package com.bafomdad.uniquecrops.blocks;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public abstract class BlockBaseUC extends Block {

	public BlockBaseUC(String name, Material mat) {
		
		super(mat);
		setRegistryName(name);
		setTranslationKey(UniqueCrops.MOD_ID + "." + name);
		setCreativeTab(UniqueCrops.TAB);
		UCBlocks.blocks.add(this);
	}
}
