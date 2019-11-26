package com.bafomdad.uniquecrops.crops.supercrops;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.BlockSuperCropsBase;
import com.bafomdad.uniquecrops.blocks.tiles.TileWeatherflesia;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.enums.EnumDirectional;
import com.bafomdad.uniquecrops.core.enums.EnumSuperCrops;
import com.bafomdad.uniquecrops.init.UCItems;

public class Weatherflesia extends BlockSuperCropsBase {
	
	private static final String[] BIOME_NAME = new String[] { "biomeName", "field_76791_y" };
	public static final PropertyEnum RAFFLESIA = PropertyEnum.<EnumDirectional>create("rafflesia", EnumDirectional.class);
	private static final AxisAlignedBB[] AABB = new AxisAlignedBB[] {
		new AxisAlignedBB(0.0D, 0.0D, 0.5D, 1.0D, 0.625D, 1.0D),
		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 0.5D),
		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 0.625D, 1.0D),
		new AxisAlignedBB(0.5D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D),
		new AxisAlignedBB(0.0D, 0.0D, 0.5D, 0.5D, 0.625D, 1.0D),
		new AxisAlignedBB(1.0D, 0.0D, 1.0D, 0.5D, 0.625D, 0.5D),
		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 0.625D, 0.5D),
		new AxisAlignedBB(0.5D, 0.0D, 0.5D, 1.0D, 0.625D, 0.0D),
		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D),
		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D)
	};
	
	public Weatherflesia() {
		
		super(EnumSuperCrops.WEATHER);
		setDefaultState(blockState.getBaseState().withProperty(RAFFLESIA, EnumDirectional.UP));
		GameRegistry.registerTileEntity(TileWeatherflesia.class, new ResourceLocation(UniqueCrops.MOD_ID, "TileWeatherflesia"));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		
		return new BlockStateContainer(this, RAFFLESIA);
	}
	
	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		
		return AABB[getMetaFromState(state)];
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		
		return Items.AIR;
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileWeatherflesia) {
			TileWeatherflesia weather = (TileWeatherflesia)tile;
			weather.tickBiomeStrength();
			ItemStack stack = weather.getItem();
			if (!stack.isEmpty() && stack.isItemDamaged()) {
				int repairStrength = weather.getBiomeStrength() / 2;
				stack.setItemDamage(stack.getItemDamage() - repairStrength);
				weather.markBlockForUpdate();
			}
		}
	}
	
	@Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileWeatherflesia) {
			TileWeatherflesia weather = (TileWeatherflesia)tile;
			ItemStack stack = player.getHeldItem(hand);
			if (!stack.isEmpty() && stack.getItem() == UCItems.pixelBrush && !player.isSneaking()) {
				int biomeId = Biome.getIdForBiome(world.getBiome(pos));
				NBTUtils.setInt(stack, UCStrings.TAG_BIOME, biomeId);
				weather.setItem(stack);
				player.setHeldItem(hand, ItemStack.EMPTY);
				weather.markBlockForUpdate();
				return true;
			} 
			if (stack.isEmpty() && player.isSneaking()) {
				ItemStack tileItem = weather.getItem();
				if (!tileItem.isEmpty()) {
					weather.setItem(ItemStack.EMPTY);
					player.setHeldItem(hand, tileItem);
					weather.markBlockForUpdate();
				}
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
		
		super.neighborChanged(state, world, pos, block, fromPos);
		this.checkAndDropBlock(world, pos, state);
	}
	
	@Override
	protected void checkAndDropBlock(World world, BlockPos pos, IBlockState state) {
		
		if (getMetaFromState(state) == EnumDirectional.DOWN.ordinal() || getMetaFromState(state) == EnumDirectional.UP.ordinal()) {
			for (EnumFacing facing : EnumFacing.HORIZONTALS) {
				BlockPos loopPos = pos.offset(facing);
				if (world.isAirBlock(loopPos)) {
					world.destroyBlock(pos, false);
					return;
				}
			}
		}
		if (isNeighborMissing(world, pos, state))
			world.destroyBlock(pos, false);
	}
	
	private boolean isNeighborMissing(World world, BlockPos pos, IBlockState state) {
		
		if (!(state.getBlock() instanceof Weatherflesia)) return false;
		
		EnumDirectional prop = (EnumDirectional)state.getValue(RAFFLESIA);
		switch (prop) {
			case NORTH: return isRafflesia(world, pos.east()) || isRafflesia(world, pos.west());
			case SOUTH: return isRafflesia(world, pos.east()) || isRafflesia(world, pos.west());
			case WEST: return isRafflesia(world, pos.north()) || isRafflesia(world, pos.south());
			case EAST: return isRafflesia(world, pos.north()) || isRafflesia(world, pos.south());
			case NORTHEAST: return isRafflesia(world, pos.south()) || isRafflesia(world, pos.west());
			case NORTHWEST: return isRafflesia(world, pos.south()) || isRafflesia(world, pos.east());
			case SOUTHEAST: return isRafflesia(world, pos.north()) || isRafflesia(world, pos.west());
			case SOUTHWEST: return isRafflesia(world, pos.north()) || isRafflesia(world, pos.east());
			default: return false;
		}
	}
	
	private boolean isRafflesia(World world, BlockPos pos) {
		
		return !(world.getBlockState(pos).getBlock() instanceof Weatherflesia);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		
		return getDefaultState().withProperty(RAFFLESIA, EnumDirectional.byIndex(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		
		return ((EnumDirectional)state.getValue(RAFFLESIA)).ordinal();
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		
		return getMetaFromState(state) == EnumDirectional.UP.ordinal() || getMetaFromState(state) == EnumDirectional.DOWN.ordinal();
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		
		return new TileWeatherflesia();
	}
}
