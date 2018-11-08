package com.bafomdad.uniquecrops.blocks;

import java.util.List;

import javax.annotation.Nonnull;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPlantGlass extends BlockBaseUC {

	public BlockPlantGlass() {
		
		super("invisiglass", Material.GLASS);
		setSoundType(SoundType.GLASS);
		setHardness(0.2F);
	}
	
	@Override
    public boolean isFullCube(IBlockState state) {
        
		return false;
    }
	
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        
    	return BlockRenderLayer.CUTOUT;
    }
    
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        
    	return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        
    	IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
        Block block = iblockstate.getBlock();
        if (blockState != iblockstate)
            return true;

        if (block == this)
            return false;

        return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }
    
	@Override
	public void addCollisionBoxToList(IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull AxisAlignedBB aabb, @Nonnull List<AxisAlignedBB> stacks, Entity entity, boolean isActualState) {
		
		if (!(entity instanceof EntityPlayer))
			super.addCollisionBoxToList(state, world, pos, aabb, stacks, entity, isActualState);
			
		if (entity instanceof EntityPlayer && !((EntityPlayer)entity).capabilities.isCreativeMode) {
			if (/*UniqueCrops.proxy.invisiTrace()*/ (!((EntityPlayer)entity).inventory.armorInventory.get(3).isEmpty() && ((EntityPlayer)entity).inventory.armorInventory.get(3).getItem() == UCItems.glasses3D))
				super.addCollisionBoxToList(state, world, pos, aabb, stacks, entity, isActualState);
		}
	}
    
    @Override
    public RayTraceResult collisionRayTrace(IBlockState state, World world, BlockPos pos, Vec3d start, Vec3d end) {
    	
    	if (!UniqueCrops.proxy.invisiTrace()) return null;

    	return super.collisionRayTrace(state, world, pos, start, end);
    }
}
