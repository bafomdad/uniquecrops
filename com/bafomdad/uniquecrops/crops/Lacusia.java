package com.bafomdad.uniquecrops.crops;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.blocks.tiles.TileLacusia;
import com.bafomdad.uniquecrops.core.EnumCrops;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.init.UCItems;

public class Lacusia extends BlockCropsBase {

	public Lacusia() {
		
		super(EnumCrops.LACUSIA, true, UCConfig.cropLacusia);
		GameRegistry.registerTileEntity(TileLacusia.class, "TileLacusia");
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsLacusia;
	}
	
	@Override
	public Item getCrop() {
		
		return Items.STICK;
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		
		if (world.getTileEntity(pos) instanceof TileLacusia) {
			ItemStack stack = ((TileLacusia)world.getTileEntity(pos)).getItem();
			if (!stack.isEmpty()) {
				world.removeTileEntity(pos);
				spawnAsEntity(world, pos, stack);
			}
		}
		super.breakBlock(world, pos, state);
	}
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		if (getAge(state) >= getMaxAge()) {
			TileEntity tile = world.getTileEntity(pos);
			if (tile instanceof TileLacusia) {
				((TileLacusia)tile).updateStuff();
			}
		}
		super.updateTick(world, pos, state, rand);
	}
	
    @Override
    public void grow(World world, BlockPos pos, IBlockState state) {
    	
    	super.grow(world, pos, state);
    	TileEntity tile = world.getTileEntity(pos);
    	if (tile instanceof TileLacusia) {
    		((TileLacusia)tile).updateStuff();
    	}
    }
	
	@Override
    public boolean hasTileEntity(IBlockState state) {
        
		return true;
    }
	
    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
    	
    	if (getAge(state) >= getMaxAge())
    		return new TileLacusia();
    	
    	return null;
    }
}
