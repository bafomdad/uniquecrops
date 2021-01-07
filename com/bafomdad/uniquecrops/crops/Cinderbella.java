package com.bafomdad.uniquecrops.crops;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.core.enums.EnumCrops;
import com.bafomdad.uniquecrops.init.UCItems;

public class Cinderbella extends BlockCropsBase {

	public Cinderbella() {
		
		super(EnumCrops.CINDERBELLA);
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsCinderbella;
	}
	
	@Override
	public Item getCrop() {
		
		return UCItems.generic;
	}
	
	@Override
	public boolean canPlantCrop(World world, EntityPlayer player, EnumFacing side, BlockPos pos, ItemStack stack) {
	
		if (this.canPlantOrGrow(world, pos.up())) {
			this.onBlockPlacedBy(world, pos.offset(side), getDefaultState(), player, stack);
		}
		return super.canPlantCrop(world, player, side, pos, stack);
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		
		if (getAge(state) < getMaxAge())
			return 0;
		
		return 14;
	}
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		if (this.canIgnoreGrowthRestrictions(world, pos)) {
			super.updateTick(world, pos, state, rand);
			return;
		}
		if (this.canPlantOrGrow(world, pos)) {
			super.updateTick(world, pos, state, rand);
			return;
		}
		world.setBlockState(pos, Blocks.DEADBUSH.getDefaultState(), 2);
	}
	
    @Override
    public void grow(World world, BlockPos pos, IBlockState state) {
    	
    	if (this.canIgnoreGrowthRestrictions(world, pos)) {
    		super.grow(world, pos, state);
    		return;
    	}
    	if (!world.isRemote && !this.canPlantOrGrow(world, pos)) {
    		world.setBlockState(pos, Blocks.DEADBUSH.getDefaultState(), 2);
    		return;
    	}
    	super.grow(world, pos, state);
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
    	
        return BlockRenderLayer.TRANSLUCENT;
    }
	
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
    	
    	this.createParticles(state, world, pos, rand, EnumParticleTypes.CRIT, 3);
    }
    
    @Override
    public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
    	
    	return false;
    }
    
    @Override
    public boolean isFullBlock(IBlockState state) {
    	
    	return false;
    }
    
    public boolean canPlantOrGrow(World world, BlockPos pos) {

    	if (world.getWorldTime() >= 18500L || world.getWorldTime() <= 12542L) return false;
		
		int pumpkins = 0;
		for (EnumFacing facing : EnumFacing.HORIZONTALS) {
			IBlockState pumpkin = world.getBlockState(pos.offset(facing));
			if (pumpkin.getBlock() == Blocks.PUMPKIN) {
				pumpkins++;
			}
		}
		if (pumpkins < 4)
			return false;

		return true;
    }
}
