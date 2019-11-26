package com.bafomdad.uniquecrops.blocks;

import java.awt.Color;
import java.util.Random;

import javax.annotation.Nullable;

import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.dimension.CropWorldTeleporter;
import com.bafomdad.uniquecrops.init.UCDimension;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCropPortal extends BlockBaseUC {
	
	protected static final AxisAlignedBB PORTAL_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);
	private static final CropWorldTeleporter TP = new CropWorldTeleporter();

	public BlockCropPortal() {
		
		super("portal", Material.PORTAL);
		setHardness(-1.0F);
		setSoundType(SoundType.GLASS);
		setLightLevel(0.75F);
	}
	
	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    	
    	return PORTAL_AABB;
    }
	
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        
    	return NULL_AABB;
    }
    
    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
        
    	int dimensionID = (world.provider.getDimension() == 0) ? UCDimension.dimID : 0;
    	if (!entity.isRiding() && !entity.isBeingRidden() && entity instanceof EntityPlayer) {
    		setLastTeleportPosition(world, pos, entity);
    		entity.changeDimension(dimensionID, TP);
    	}
    }
    
    private void setLastTeleportPosition(World world, BlockPos pos, Entity entity) {
    	
    	NBTTagCompound data = entity.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
    	if (data != null) {
    		NBTTagCompound lastTelePos = new NBTTagCompound();
    		int attempts = 5;
    		for (EnumFacing facing : EnumFacing.HORIZONTALS) {
    			for (int i = 0; i < attempts; i++) {
    				BlockPos loopPos = pos.offset(facing, i);
    				if (world.getBlockState(loopPos).getBlock() == this) continue;

    				if (!world.isAirBlock(loopPos) && !world.isAirBlock(loopPos.up())) continue;
    				
    				else {
    					pos = loopPos.up();
    					break;
    				}
    			}
    		}
    		lastTelePos.setLong(UCStrings.LAST_POSITION, pos.toLong());
    		entity.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, lastTelePos);
    	}
    }
    
    @Override
    public boolean isFullCube(IBlockState state) {
    	
        return false;
    }
    
    @Override
    public boolean isOpaqueCube(IBlockState state) {
    	
        return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
    	
    	if (side != EnumFacing.UP || side != EnumFacing.DOWN)
    		return false;
    	
    	return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }
    
    @Override
    public int quantityDropped(Random random) {
    	
        return 0;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
    	
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
    	
        return ItemStack.EMPTY;
    }
    
    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
    	
        return BlockFaceShape.UNDEFINED;
    }
    
    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
    	
		for (EnumFacing facing : EnumFacing.HORIZONTALS) {
			BlockPos loopPos = pos.offset(facing);
			if (world.isAirBlock(loopPos)) {
				world.setBlockToAir(pos);
			}
		}
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
    	
        for (int i = 0; i < 2; ++i) {
            double d0 = (double)((float)pos.getX() + rand.nextFloat());
            double d1 = (double)((float)pos.getY() + rand.nextFloat());
            double d2 = (double)((float)pos.getZ() + rand.nextFloat());

            int k = 3369483;
            double d3 = (double)(k >> 16 & 255) / 255.0D;
            double d4 = (double)(k >> 8 & 255) / 255.0D;
            double d5 = (double)(k >> 0 & 255) / 255.0D;

            world.spawnParticle(EnumParticleTypes.SPELL_MOB, d0, d1, d2, d3, d4, d5);
        }
    }
}
