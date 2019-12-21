package com.bafomdad.uniquecrops.crops;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.blocks.tiles.TileShyPlant;
import com.bafomdad.uniquecrops.core.enums.EnumCrops;
import com.bafomdad.uniquecrops.init.UCItems;

public class WeepingBells extends BlockCropsBase {

	public WeepingBells() {
		
		super(EnumCrops.SHYPLANT);
		GameRegistry.registerTileEntity(TileShyPlant.class, new ResourceLocation(UniqueCrops.MOD_ID, "shyplant"));
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
		
		if (this.canIgnoreGrowthRestrictions(world, pos)) {
			super.updateTick(world, pos, state, rand);
			return;
		}
		this.checkAndDropBlock(world, pos, state);
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileShyPlant && ((TileShyPlant)te).isLooking()) {
			super.updateTick(world, pos, state, rand);
		}
	}

	@Override
    public boolean hasTileEntity(IBlockState state) {
		
		return true;
	}
	
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
    	
    	return new TileShyPlant();
    }
}
