package com.bafomdad.uniquecrops.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRuinedBricksGhost extends BlockBaseUC {

	public BlockRuinedBricksGhost() {
		
		super("ruinedbricksghost", Material.ROCK);
		setHardness(1.15F);
		setResistance(30.0F);
	}
	
	@Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		
        return NULL_AABB;
    }
	
    @Override
    public boolean isFullCube(IBlockState state) {
    	
        return false;
    }
	
    @Override
    public boolean isOpaqueCube(IBlockState state) {
    	
    	return false;
    }
    
	@Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
    	
        return BlockRenderLayer.TRANSLUCENT;
    }
}
