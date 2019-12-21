package com.bafomdad.uniquecrops.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.bafomdad.uniquecrops.init.UCBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockBaseStalk extends Block {

	public BlockBaseStalk() {
		
		super(Material.PLANTS);
		setHardness(0.1F);
		setResistance(1.0F);
		UCBlocks.blocks.add(this);
	}
	
	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		
		return Items.AIR;
	}
	
	@Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
    	
		super.neighborChanged(state, world, pos, block, fromPos);
		this.checkAndDropBlock(world, pos, state);
    }
	
    protected void checkAndDropBlock(World world, BlockPos pos, IBlockState state) {}
    
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        
    	return false;
    }
    
    @Override
    public boolean isFullCube(IBlockState state) {
    	
        return false;
    }
}
