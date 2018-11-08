package com.bafomdad.uniquecrops.blocks;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockOldStone extends Block {

	public BlockOldStone(String name) {
		
		super(Material.ROCK);
		setRegistryName("old" + name);
		setTranslationKey(UniqueCrops.MOD_ID + ".old" + name);
		setCreativeTab(UniqueCrops.TAB);
		setHardness(1.5F);
		setResistance(15.0F);
		setSoundType(SoundType.STONE);
		UCBlocks.blocks.add(this);
	}
}
