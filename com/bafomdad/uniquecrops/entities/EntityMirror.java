package com.bafomdad.uniquecrops.entities;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCBlocks;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityMirror extends Entity {

	@SideOnly(Side.CLIENT)
	public boolean rendering = false;
	
	public EntityMirror(World world, BlockPos pos, EnumFacing facing) {
		
		this(world);
		if (facing == null) facing = EnumFacing.SOUTH;
		this.setPositionAndRotation(pos.getX() + 0.5D, pos.getY() + 0.00625D, pos.getZ() + 0.5D, facing.getHorizontalAngle(), 20);
	}
	
	public EntityMirror(World world) {
		
		super(world);
		this.setNoGravity(true);
		this.setInvisible(true);
		this.setEntityInvulnerable(true);
	}
	
	@Override
	public void onUpdate() {
		
		super.onUpdate();
		if (world.getBlockState(this.getPosition()).getBlock() != UCBlocks.mirror) {
			this.setDead();
		}
	}
	
	@Override
	public void setDead() {
		
		super.setDead();
		UniqueCrops.proxy.killMirror(this);
	}
	
	@Override
	public boolean shouldRenderInPass(int pass) {
		
		return false;
	}

	@Override
	protected void entityInit() {}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {}
}
