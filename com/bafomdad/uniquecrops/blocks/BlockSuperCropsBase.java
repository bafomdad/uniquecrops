package com.bafomdad.uniquecrops.blocks;

import java.util.Random;

import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.core.enums.EnumSuperCrops;
import com.bafomdad.uniquecrops.init.UCBlocks;

public abstract class BlockSuperCropsBase extends BlockBush {

	public BlockSuperCropsBase(EnumSuperCrops type) {
		
		setRegistryName(type.getName());
		setTranslationKey(UniqueCrops.MOD_ID + "." + type.getName());
		setHardness(5.0F);
		setResistance(1000.0F);
		setSoundType(SoundType.PLANT);
		setCreativeTab(UniqueCrops.TAB);
		UCBlocks.blocks.add(this);
	}
	
	@Override
    public boolean canSustainBush(IBlockState state) {
        
    	return super.canSustainBush(state);
    }
}
