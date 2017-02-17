package com.bafomdad.uniquecrops.crops;

import java.util.Random;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.blocks.tiles.TileShyPlant;
import com.bafomdad.uniquecrops.core.EnumCrops;
import com.bafomdad.uniquecrops.init.UCItems;

public class WeepingBells extends BlockCropsBase implements ITileEntityProvider {

	public WeepingBells() {
		
		super(EnumCrops.SHYPLANT, false);
		GameRegistry.registerTileEntity(TileShyPlant.class, "TileShyPlant");
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsWeepingbells;
	}
	
	@Override
	public Item getCrop() {
		
		return UCItems.generic;
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		
		if (getAge(state) < getMaxAge())
			return 0;
		
		return 15;
	}
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		TileShyPlant te = (TileShyPlant)world.getTileEntity(pos);
		if (te != null && te.isLooking()) {
			super.updateTick(world, pos, state, rand);
		}
	}
	
	public boolean isWeepingCropGrown(IBlockState state) {
		
		return this.getAge(state) >= this.getMaxAge();
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		return new TileShyPlant();
	}
}
