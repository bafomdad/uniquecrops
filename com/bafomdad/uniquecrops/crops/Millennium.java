package com.bafomdad.uniquecrops.crops;

import java.util.Random;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.blocks.tiles.TileMillennium;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.core.enums.EnumCrops;
import com.bafomdad.uniquecrops.init.UCItems;

public class Millennium extends BlockCropsBase {

	public Millennium() {
		
		super(EnumCrops.FOREVERPLANT);
		GameRegistry.registerTileEntity(TileMillennium.class, new ResourceLocation(UniqueCrops.MOD_ID, "TileMillennium"));
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsMillennium;
	}
	
	@Override
	public Item getCrop() {
		
		return UCItems.generic;
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		
		if (getAge(state) < getMaxAge())
			return 0;
		
		return 17;
	}
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		this.checkAndDropBlock(world, pos, state);
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileMillennium) {
			if (!world.isRemote && getAge(state) < getMaxAge()) {
				TileMillennium mill = (TileMillennium)tile;
				if (mill.isTimeEmpty()) {
					mill.setTime();
					return;
				}
				if (mill.calcTime() >= UCConfig.millenniumTime) {
					world.setBlockState(pos, this.withAge(getAge(state) + 1), 2);
					mill.setTime();
				}
			}
		}
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		
		return new TileMillennium();
	}
}
