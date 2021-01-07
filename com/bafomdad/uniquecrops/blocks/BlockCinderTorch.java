package com.bafomdad.uniquecrops.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockCinderTorch extends BlockBaseUC {
	
	protected static final AxisAlignedBB TORCH_AABB = new AxisAlignedBB(0.275D, 0.0D, 0.275D, 0.725D, 0.45D, 0.725D);
	
	public BlockCinderTorch() {
		
		super("cindertorch", Material.SAND, SoundType.STONE);
		setHardness(0.01F);
		setResistance(0.1F);
		setLightLevel(1.0F);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		
		return TORCH_AABB;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		
		return false;
	}
}
