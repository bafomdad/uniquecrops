package com.bafomdad.uniquecrops.blocks;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCBlocks;

import net.minecraft.block.BlockGravel;
import net.minecraft.block.SoundType;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockOldGravel extends BlockGravel {

	public BlockOldGravel() {
		
		setRegistryName("oldgravel");
		setTranslationKey(UniqueCrops.MOD_ID + ".oldgravel");
		setCreativeTab(UniqueCrops.TAB);
		setHardness(0.6F);
		setSoundType(SoundType.GROUND);
		UCBlocks.blocks.add(this);
	}
}
