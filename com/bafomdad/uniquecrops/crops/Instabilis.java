package com.bafomdad.uniquecrops.crops;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.enums.EnumCrops;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;

public class Instabilis extends BlockCropsBase {

	public Instabilis() {
		
		super(EnumCrops.UNSTABLE);
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsInstabilis;
	}
	
	@Override
	public Item getCrop() {
	
		return Items.AIR;
	}
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		boolean canGrow = true;
		for (EnumFacing facing : EnumFacing.HORIZONTALS) {
			IBlockState loopState = world.getBlockState(pos.offset(facing));
			if (loopState.getBlock() != this) {
				canGrow = false;
				break;
			}
		}
		if (canGrow) {
			super.updateTick(world, pos, state, rand);
			return;
		}
		this.checkAndDropBlock(world, pos, state);
	}
    
    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
    	
    	ItemStack stack = player.getHeldItemMainhand();
    	if (!stack.isEmpty() && stack.getItem() instanceof ItemShears) {
    		if (!world.isRemote) {
    			if (!player.capabilities.isCreativeMode)
    				stack.damageItem(1, player);
    			world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, new ItemStack(UCBlocks.demoCord, world.rand.nextInt(3) + 1)));
    		}
    		return willHarvest;
    	}
		for (EnumFacing facing : EnumFacing.HORIZONTALS) {
			BlockPos loopPos = pos.offset(facing);
			IBlockState loopState = world.getBlockState(loopPos);
			if (loopState.getBlock() == this)
    			this.neighborChanged(state, world, loopPos, this, pos);
		}
    	return willHarvest || super.removedByPlayer(state, world, pos, player, willHarvest);
    }
    
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity tile, ItemStack stack) {
    	
    	super.harvestBlock(world, player, pos, state, tile, stack);
		world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
    }
    
    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
    	
    	if (block == this)
    		world.destroyBlock(pos, false);
    	else super.neighborChanged(state, world, pos, block, fromPos);
    }
}
