package com.bafomdad.uniquecrops.crops;

import java.util.Random;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.blocks.tiles.TileCinderbella;
import com.bafomdad.uniquecrops.core.EnumCrops;
import com.bafomdad.uniquecrops.init.UCItems;

public class Cinderbella extends BlockCropsBase implements ITileEntityProvider {

	public Cinderbella() {
		
		super(EnumCrops.CINDERBELLA, true);
		GameRegistry.registerTileEntity(TileCinderbella.class, "TileCinderbella");
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
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		if (this.getAge(state) < getMaxAge())
			return;
		
		TileEntity te = world.getTileEntity(pos);
		if (te != null && te instanceof TileCinderbella) {
			TileCinderbella tile = (TileCinderbella)te;
			long time = world.getWorldTime() % 24000L;
			if (tile.plantedCorrect && tile.timePlanted <= (time - 6000)) {
				super.updateTick(world, pos, state, rand);
				return;
			}
		}
		world.setBlockState(pos, Blocks.DEADBUSH.getDefaultState(), 2);
	}
	
    @Override
    public void grow(World world, BlockPos pos, IBlockState state) {
    	
    	TileEntity te = world.getTileEntity(pos);
    	if (te != null && te instanceof TileCinderbella) {
    		TileCinderbella tile = (TileCinderbella)te;
    		if (!world.isRemote && !tile.plantedCorrect)
    		{
    			world.setBlockState(pos, Blocks.DEADBUSH.getDefaultState(), 2);
    			return;
    		}
    	}
    	else if (te == null)
    		world.setBlockState(pos, Blocks.DEADBUSH.getDefaultState(), 2);
    	
    	super.grow(world, pos, state);
    }
	
    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    	
    	world.setBlockState(pos, state.getBlock().getDefaultState(), 2);
    	TileEntity te = world.getTileEntity(pos);
    	if (te != null && te instanceof TileCinderbella) {
    		((TileCinderbella)te).setAbleToGrow(world);
    	}
    }

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		return new TileCinderbella();
	}
	
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
    	
    	this.createParticles(state, world, pos, rand, EnumParticleTypes.CRIT, 3);
    }
}
