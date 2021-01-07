package com.bafomdad.uniquecrops.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.tiles.TileCraftyPlant;
import com.bafomdad.uniquecrops.core.enums.EnumDirectional;
import com.bafomdad.uniquecrops.init.UCBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockWorkbench.InterfaceCraftingTable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockStalk extends BlockBaseStalk {
	
	public static final PropertyEnum STALKS = PropertyEnum.<EnumDirectional>create("stalk", EnumDirectional.class);

	public BlockStalk() {
		
		setRegistryName("stalk");
		setTranslationKey(UniqueCrops.MOD_ID + ".stalk");
		setDefaultState(blockState.getBaseState().withProperty(STALKS, EnumDirectional.NORTH));
		setTickRandomly(true);
		GameRegistry.registerTileEntity(TileCraftyPlant.class, new ResourceLocation(UniqueCrops.MOD_ID, "craftyplant"));
	}
	
	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
       
		if (getMetaFromState(state) == EnumDirectional.UP.ordinal()) {
			return Item.getItemFromBlock(Blocks.CRAFTING_TABLE);
		}
		return super.getItemDropped(state, rand, fortune);
    }
	
	@Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileCraftyPlant) {
			player.openGui(UniqueCrops.instance, 4, world, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}
		return false;
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {

		if (getMetaFromState(state) == EnumDirectional.DOWN.ordinal() && world.isAirBlock(pos.up())) {
			if (rand.nextInt(5) == 0) {
				world.playEvent(2001, pos.up(), Block.getStateId(state));
				world.setBlockState(pos.up(), UCBlocks.stalk.getDefaultState().withProperty(STALKS, EnumDirectional.UP), 2);
			}
		}
	}
	
    @Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        
    	if (getMetaFromState(state) == EnumDirectional.UP.ordinal()) {
    		return FULL_BLOCK_AABB;
    	}
    	return NULL_AABB;
    }
	
	@Override
	protected void checkAndDropBlock(World world, BlockPos pos, IBlockState state) {
		
		if (getMetaFromState(state) == EnumDirectional.DOWN.ordinal()) {
			for (EnumFacing facing : EnumFacing.HORIZONTALS) {
				BlockPos loopPos = pos.offset(facing);
				if (world.isAirBlock(loopPos)) {
					world.destroyBlock(pos, false);
					return;
				}
			}
		}
		if (getMetaFromState(state) == EnumDirectional.UP.ordinal()) {
			if (world.isAirBlock(pos.down())) {
				world.destroyBlock(pos, true);
				return;
			}
		}
		if (isNeighborStalkMissing(world, pos, state)) {
			world.destroyBlock(pos, false);
		}
	}
	
	private boolean isNeighborStalkMissing(World world, BlockPos pos, IBlockState state) {
		
		if (!(state.getBlock() instanceof BlockBaseStalk)) return false;
		
		EnumDirectional prop = (EnumDirectional)state.getValue(STALKS);
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
		
		return getDefaultState().withProperty(STALKS, EnumDirectional.byIndex(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		
		return ((EnumDirectional)state.getValue(STALKS)).ordinal();
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		
		return getMetaFromState(state) == EnumDirectional.UP.ordinal();
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		
		return new TileCraftyPlant();
	}
}
