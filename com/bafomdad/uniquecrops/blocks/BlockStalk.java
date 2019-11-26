package com.bafomdad.uniquecrops.blocks;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockStalk extends BlockBaseStalk {
	
	public static final PropertyEnum STALKS = PropertyEnum.<BlockStalk.EnumStalk>create("stalk", BlockStalk.EnumStalk.class);

	public BlockStalk() {
		
		setRegistryName("stalk");
		setTranslationKey(UniqueCrops.MOD_ID + ".stalk");
		setDefaultState(blockState.getBaseState().withProperty(STALKS, BlockStalk.EnumStalk.NORTH));
	}
	
	@Override
	protected void checkAndDropBlock(World world, BlockPos pos, IBlockState state) {
		
		if (isNeighborStalkMissing(world, pos, state)) {
			world.destroyBlock(pos, false);
		}
	}
	
	private boolean isNeighborStalkMissing(World world, BlockPos pos, IBlockState state) {
		
		if (!(state.getBlock() instanceof BlockBaseStalk)) return false;
		
		BlockStalk.EnumStalk prop = (BlockStalk.EnumStalk)state.getValue(STALKS);
		switch (prop) {
			case NORTH: return isStalk(world, pos.east()) || isStalk(world, pos.west());
			case SOUTH: return isStalk(world, pos.east()) || isStalk(world, pos.west());
			case WEST: return isStalk(world, pos.north()) || isStalk(world, pos.south());
			case EAST: return isStalk(world, pos.north()) || isStalk(world, pos.south());
			case NORTHEAST: return isStalk(world, pos.south()) || isStalk(world, pos.west());
			case NORTHWEST: return isStalk(world, pos.south()) || isStalk(world, pos.east());
			case SOUTHEAST: return isStalk(world, pos.north()) || isStalk(world, pos.west());
			case SOUTHWEST: return isStalk(world, pos.north()) || isStalk(world, pos.east());
			default: return false;
		}
	}
	
	private boolean isStalk(World world, BlockPos pos) {
		
		return !(world.getBlockState(pos).getBlock() instanceof BlockBaseStalk);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		
		return new BlockStateContainer(this, STALKS);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		
		return getDefaultState().withProperty(STALKS, BlockStalk.EnumStalk.byIndex(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		
		return ((BlockStalk.EnumStalk)state.getValue(STALKS)).ordinal();
	}
	
	public static enum EnumStalk implements IStringSerializable {
		
		NORTH("north"),
		SOUTH("south"),
		EAST("east"),
		WEST("west"),
		NORTHEAST("northeast"),
		NORTHWEST("northwest"),
		SOUTHEAST("southeast"),
		SOUTHWEST("southwest");
		
		private final String name;
		
		private EnumStalk(String name) {
			
			this.name = name;
		}
		
		public BlockPos getOffset(BlockPos pos) {
			
			switch (this) {
				case NORTH: return pos.north();
				case SOUTH: return pos.south();
				case EAST: return pos.east();
				case WEST: return pos.west();
				case NORTHEAST: return pos.north().east();
				case NORTHWEST: return pos.north().west();
				case SOUTHEAST: return pos.south().east();
				case SOUTHWEST: return pos.south().west();
				default: return pos;
			}
		}
		
		public static EnumStalk byIndex(int meta) {
			
			return EnumStalk.values()[meta];
		}

		@Override
		public String toString() {
			
			return this.name;
		}

		@Override
		public String getName() {

			return this.name;
		}
	}
}
