package com.bafomdad.uniquecrops.entities;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityLivingBlock extends EntityThrowable {
	
	private IBlockState fallingTile;
	private EnumFacing facing;

	public EntityLivingBlock(World world, IBlockState fallingBlockState, EnumFacing facing) {
		
		super(world);
		this.fallingTile = fallingBlockState;
		this.facing = facing;
		this.setSize(0.25F, 0.25F);
		this.setShootingDirection(facing);
	}
	
	public EntityLivingBlock(World world) {
		
		super(world);
	}
	
	public void setShootingDirection(EnumFacing facing) {
		
		if (facing == null) return;
		
		double x = facing.getFrontOffsetX();
		double y = facing.getFrontOffsetY();
		double z = facing.getFrontOffsetZ();
		this.setThrowableHeading(x, y, z, 0.5F, 0);
	}

	@Override
	public void onImpact(RayTraceResult result) {

		if (fallingTile == null) {
			this.setDead();
			return;
		}
		BlockPos pos = new BlockPos(result.hitVec);
		if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
			IBlockState state = worldObj.getBlockState(pos);
			if (!(state.getBlock() instanceof BlockCrops)) {
				worldObj.setBlockState(pos.offset(result.sideHit), fallingTile, 3);
				this.setDead();
				return;
			}
		}
		else this.setDead();
	}
	
	public IBlockState getBlock() {
		
		return fallingTile;
	}
}
