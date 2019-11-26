package com.bafomdad.uniquecrops.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCBlocks;

public class BlockFlywoodLeaves extends BlockLeaves {
	

	public BlockFlywoodLeaves() {
		
		setRegistryName("flywood_leaves");
		setTranslationKey(UniqueCrops.MOD_ID + ".flywood_leaves");
		setCreativeTab(UniqueCrops.TAB);
		UCBlocks.blocks.add(this);
		this.setDefaultState(this.blockState.getBaseState().withProperty(CHECK_DECAY, Boolean.valueOf(true)).withProperty(DECAYABLE, Boolean.valueOf(true)));
	}
	
	@Override
    protected BlockStateContainer createBlockState() {
		
		return new BlockStateContainer(this, new IProperty[] { CHECK_DECAY, DECAYABLE });
	}
	
	@Override
    public IBlockState getStateFromMeta(int meta) {
		
        return this.getDefaultState().withProperty(DECAYABLE, (meta & 4) == 0).withProperty(CHECK_DECAY, (meta & 8) > 0);
    }
	
	@Override
    public int getMetaFromState(IBlockState state) {
       
		int i = 0;
        if (!state.getValue(DECAYABLE).booleanValue())
            i |= 4;

        if (state.getValue(CHECK_DECAY).booleanValue())
            i |= 8;
        
        return i;
    }
	
	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		
		return Item.getItemFromBlock(UCBlocks.flywoodSapling);
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {

		return NonNullList.withSize(1, new ItemStack(this));
	}

	@Override
	public EnumType getWoodType(int meta) {

		return EnumType.OAK;
	}
}
