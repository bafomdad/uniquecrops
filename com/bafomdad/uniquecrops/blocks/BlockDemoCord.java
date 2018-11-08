package com.bafomdad.uniquecrops.blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

import javax.annotation.Nullable;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDemoCord extends BlockBaseUC {
	
	protected static final AxisAlignedBB DEMOCORD_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.09375D, 0.9375D);

	public BlockDemoCord() {
		
		super("democord", Material.CIRCUITS);
	}
	
	@Override
    public void onBlockExploded(World world, BlockPos pos, Explosion explosion) {
		
		if (!world.isRemote) {		
			world.setBlockToAir(pos);
			world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 1, true);
		}
	}
	
	@Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
    	
		super.onBlockAdded(world, pos, state);
		if (!world.isRemote) {
			if (world.getRedstonePowerFromNeighbors(pos) > 0) {
				world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 1, true);
			}
		}
    }
	
    private IBlockState updateSurroundingDemocord(World worldIn, BlockPos pos, IBlockState state) {
    	
    	return null;
    }
    
    private void notifyWireNeighborsOfStateChange(World worldIn, BlockPos pos) {
    	
        if (worldIn.getBlockState(pos).getBlock() == this) {
            worldIn.notifyNeighborsOfStateChange(pos, this, false);

            for (EnumFacing enumfacing : EnumFacing.values()) {
                worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this, false);
            }
        }
    }
    
    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
    	
    	if (!world.isRemote) {
    		if (world.getRedstonePowerFromNeighbors(pos) > 0)
    			world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 1, true);
//        	int[] powers = new int[EnumFacing.HORIZONTALS.length];
//    		int i = 0;
//        	for (EnumFacing face : EnumFacing.HORIZONTALS) {
//        		int power = world.getRedstonePower(pos.offset(face), face);
//        		powers[i] = power;
//        		i++;
//        	}
//        	int powerSize = Arrays.stream(powers).sum();
//        	if (powerSize > 0) {
//        		world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 1, true);
//        	}
    	}
    }
    
    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
    	
    	super.breakBlock(world, pos, state);
    }

	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
       
		return DEMOCORD_AABB;
    }
	
	@Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        
		return NULL_AABB;
    }
	
	@Override
    public boolean isOpaqueCube(IBlockState state) {
        
		return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        
    	return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
    	
        return BlockRenderLayer.CUTOUT;
    }
    
//    @Override
//    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
//       
//    	return Item.getItemFromBlock(UCBlocks.demoCord);
//    }
    
    public static class ItemDemocord extends ItemBlock {
    	
    	public ItemDemocord(Block block) {
    		
    		super(block);
    	}
    }
}
