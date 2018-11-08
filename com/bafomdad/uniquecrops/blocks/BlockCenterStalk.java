package com.bafomdad.uniquecrops.blocks;

import javax.annotation.Nullable;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCenterStalk extends BlockBaseStalk {

	public BlockCenterStalk() {
		
		setRegistryName("stalkcenter");
		setTranslationKey(UniqueCrops.MOD_ID + ".stalkcenter");
	}
	
	@Override
	protected void checkAndDropBlock(World world, BlockPos pos, IBlockState state) {
		
		if (world.isAirBlock(pos.up())) {
			world.destroyBlock(pos, false);
			return;
		}
		for (EnumFacing facing : EnumFacing.HORIZONTALS) {
			BlockPos loopPos = pos.offset(facing);
			if (world.isAirBlock(loopPos)) {
				world.destroyBlock(pos, false);
				return;
			}
		}
	}
}
