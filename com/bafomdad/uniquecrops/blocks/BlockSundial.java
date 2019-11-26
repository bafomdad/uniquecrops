package com.bafomdad.uniquecrops.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.tiles.TileSundial;
import com.bafomdad.uniquecrops.core.enums.EnumItems;
import com.bafomdad.uniquecrops.init.UCItems;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSundial extends BlockBaseUC {
	
	protected static final AxisAlignedBB BASE_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.300000011920929D, 0.8999999761581421D);
	public BlockSundial() {
		
		super("sundial", Material.ROCK);
		setHardness(1.0F);
		setResistance(1.0F);
		GameRegistry.registerTileEntity(TileSundial.class, new ResourceLocation(UniqueCrops.MOD_ID, "sundial"));
	}
	
	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		
		return UCItems.generic;
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		
		return EnumItems.SUNDIAL.ordinal();
	}
	
	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    	
        return BASE_AABB;
    }
	
	@Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		
        return NULL_AABB;
    }
	
	@Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		if (!world.isRemote) {
			TileEntity tile = world.getTileEntity(pos);
			if (tile instanceof TileSundial) {
				long time = world.getWorldTime() % 24000L;
				((TileSundial)tile).savedTime = (int)time;
				((TileSundial)tile).savedRotation = world.getCelestialAngle(1.0F);
				((TileSundial)tile).markBlockForUpdate();
			}
		}
		return true;
	}
	
	@Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
    	
		if (blockAccess.getTileEntity(pos) instanceof TileSundial) {
			if (((TileSundial)blockAccess.getTileEntity(pos)).hasPower)
				return 15;
		}
		return 0;
    }
	
	@Override
    public boolean hasTileEntity(IBlockState state) {
        
		return true;
    }
	
    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
    	
        return new TileSundial();
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
    	
    	return false;
    }
    
    @Override
    public boolean isBlockNormalCube(IBlockState state) {
    	
    	return false;
    }
    
    @Override
    public boolean isOpaqueCube(IBlockState state) {
    	
    	return false;
    }
    
    @Override
    public boolean isFullCube(IBlockState state) {
    	
    	return false;
    }
    
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
    	
    	return EnumBlockRenderType.MODEL;
    }
    
    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
    	
    	return EnumItems.SUNDIAL.createStack();
    }
}
