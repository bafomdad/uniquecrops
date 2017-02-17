package com.bafomdad.uniquecrops.blocks.tiles;

import java.util.List;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.crops.WeepingBells;
import com.bafomdad.uniquecrops.core.EnumCrops;
import com.bafomdad.uniquecrops.entities.FakePlayerUC;
import com.mojang.authlib.GameProfile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

public class TileShyPlant extends TileBaseUC implements ITickable {
	
	private boolean looking = false;
	private FakePlayer target;

	@Override
	public void update() {

		if (worldObj.isRemote)
			return;
		
		if (this.worldObj.getTotalWorldTime() % 10L != 0)
			return;
		
		boolean wasLooking = this.isLooking();
		int range = 10;
		List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos.add(-range, -range, -range), pos.add(range, range, range)));
		
		boolean looker = false;
		for (EntityPlayer player : players) {
			ItemStack helm = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
			if (helm != null && helm.getItem() == Item.getItemFromBlock(Blocks.PUMPKIN))
				continue;
			
			RayTraceResult rtr = this.rayTraceFromEntity(worldObj, player, true, range);
			if (rtr != null && rtr.getBlockPos() != null && rtr.getBlockPos().equals(getPos())) {
				looker = true;
				break;
			}
			if (!wasLooking && ((WeepingBells)worldObj.getBlockState(getPos()).getBlock()).isWeepingCropGrown(worldObj.getBlockState(getPos())) && !player.capabilities.isCreativeMode) {
				if (getTarget(worldObj, getPos()).canEntityBeSeen(player)) {
					player.attackEntityFrom(DamageSource.outOfWorld, 1.0F);
				}
			}
		}
		if (looker != wasLooking && !worldObj.isRemote)
			setLooking(looker);
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound tag) {
		
		this.looking = tag.getBoolean("UC_tagLooking");
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound tag) {
		
		tag.setBoolean("UC_tagLooking", looking);
	}
	
	public boolean isLooking() {
		
		return this.looking;
	}
	
	public void setLooking(boolean flag) {
		
		this.looking = flag;
	}
	
	public FakePlayer getTarget(World world, BlockPos pos) {
		
		if (target == null)
			target = new Target(world, pos);
		
		return target;
	}
	
	/*
	* @author mDiyo
	 */
	private RayTraceResult rayTraceFromEntity(World world, Entity player, boolean par3, double range) {
		
		float f = 1.0F;
		float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
		float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
		double d0 = player.prevPosX + (player.posX - player.prevPosX) * f;
		double d1 = player.prevPosY + (player.posY - player.prevPosY) * f;
		if (player instanceof EntityPlayer)
			d1 += ((EntityPlayer)player).eyeHeight;
		double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * f;
		Vec3d vec3 = new Vec3d(d0, d1, d2);
		float f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
		float f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
		float f5 = -MathHelper.cos(-f1 * 0.017453292F);
		float f6 = MathHelper.sin(-f1 * 0.017453292F);
		float f7 = f4 * f5;
		float f8 = f3 * f5;
		double d3 = range;
		Vec3d vec31 = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);
		return world.rayTraceBlocks(vec3, vec31, par3);
	}
	
	private class Target extends FakePlayerUC {
		
		public Target(World world, BlockPos pos) {
			
			super(world, pos, new GameProfile(null, UniqueCrops.MOD_ID + "TileShyPlant" + ":" + pos));
		}
	}
}
