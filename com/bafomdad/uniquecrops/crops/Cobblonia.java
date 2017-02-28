package com.bafomdad.uniquecrops.crops;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.EnumCrops;
import com.bafomdad.uniquecrops.init.UCItems;

public class Cobblonia extends BlockCropsBase {

	public Cobblonia() {
		
		super(EnumCrops.COBBLEPLANT, false);
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsCobblonia;
	}
	
	@Override
	public Item getCrop() {
		
		return Item.getItemFromBlock(Blocks.COBBLESTONE);
	}
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		if (this.getAge(state) >= getMaxAge()) {
			cobbleGen(world, pos);
		}
		super.updateTick(world, pos, state, rand);
	}
	
	private void cobbleGen(World world, BlockPos pos) {
		
		BlockPos north = pos.down().offset(EnumFacing.NORTH);
		BlockPos west = pos.down().offset(EnumFacing.WEST);
		
		int cobblegen = 0;
		
		if (world.getBlockState(north).getBlock() == Blocks.WATER && world.getBlockState(pos.down().offset(EnumFacing.SOUTH)).getBlock() == Blocks.LAVA)
			cobblegen++;
		if (world.getBlockState(west).getBlock() == Blocks.WATER && world.getBlockState(pos.down().offset(EnumFacing.EAST)).getBlock() == Blocks.LAVA)
			cobblegen++;
		if (world.getBlockState(north).getBlock() == Blocks.LAVA && world.getBlockState(pos.down().offset(EnumFacing.SOUTH)).getBlock() == Blocks.WATER)
			cobblegen++;
		if (world.getBlockState(west).getBlock() == Blocks.LAVA && world.getBlockState(pos.down().offset(EnumFacing.EAST)).getBlock() == Blocks.WATER)
			cobblegen++;
		
		if (cobblegen > 0) {
			EntityItem cobble = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, new ItemStack(Blocks.COBBLESTONE, cobblegen, 0));
			if (!world.isRemote)
				world.spawnEntityInWorld(cobble);
		}
	}
}
