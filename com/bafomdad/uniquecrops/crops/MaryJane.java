package com.bafomdad.uniquecrops.crops;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.EnumCrops;
import com.bafomdad.uniquecrops.init.UCItems;

public class MaryJane extends BlockCropsBase {

	public MaryJane() {
		
		super(EnumCrops.BLAZINGPLANT, true);
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsMaryjane;
	}
	
	@Override
	public Item getCrop() {
		
		return UCItems.generic;
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		
		if (getAge(state) < getMaxAge())
			return 0;
		
		return 3;
	}
	
	@Override
    public int getLightValue(IBlockState state) {
		
		return this.getAge(state) + 1;
	}
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		if (!world.provider.doesWaterVaporize())
			return;
		
		super.updateTick(world, pos, state, rand);
	}
	
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, @Nullable ItemStack stack) {
    	
    	if (this.getAge(state) >= this.getMaxAge()) {
    		if (player == null)
    			return;
    		
    		if (!player.capabilities.isCreativeMode && (player.isBurning() && player.isImmuneToFire()) || (!player.isBurning()))
    		{
    			world.setBlockState(pos.add(0, -1, 0), Blocks.DIRT.getDefaultState(), 2);
    			world.setBlockState(pos, Blocks.FIRE.getDefaultState(), 2);
    			return;
    		}
    	}
    	super.harvestBlock(world, player, pos, state, te, stack);
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
    	
    	this.createParticles(state, world, pos, rand, EnumParticleTypes.FLAME, 0);
    }
}
