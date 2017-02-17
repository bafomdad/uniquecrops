package com.bafomdad.uniquecrops.items;

import java.util.ArrayList;
import java.util.List;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCItems;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemEnderSnooker extends Item {

	public ItemEnderSnooker() {
		
		setRegistryName("endersnooker");
		setUnlocalizedName(UniqueCrops.MOD_ID + ".endersnooker");
		setCreativeTab(UniqueCrops.TAB);
		setMaxDamage(16);
		setMaxStackSize(1);
		GameRegistry.register(this);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		
		List<EntityLivingBase> elb = acquireAllLookTargets(player, 32, 3);
		for (EntityLivingBase target : elb) {
			if (target.canEntityBeSeen(player) && !(target instanceof EntityPlayer)) {
				BlockPos targetpos = target.getPosition();
				BlockPos playerpos = player.getPosition();
				if (!world.isRemote) {
					target.setPositionAndUpdate(playerpos.getX(), playerpos.getY(), playerpos.getZ());
					player.setPositionAndUpdate(targetpos.getX(), targetpos.getY(), targetpos.getZ());
					if (target instanceof EntityWolf && world.rand.nextInt(100) == 0)
						target.entityDropItem(UCItems.generic.createStack("dogresidue"), 1);
					stack.damageItem(1, player);
					return new ActionResult(EnumActionResult.SUCCESS, stack);
				}
			}
		}
		return new ActionResult(EnumActionResult.PASS, stack);
	}
	
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		
		return true;
	}
	
	/* Thanks to Coolalias for all code below this line */
	private static final int MAX_DISTANCE = 256;
	
	private List<EntityLivingBase> acquireAllLookTargets(EntityLivingBase seeker, int distance, double radius) {
		
		if (distance < 0 || distance > MAX_DISTANCE) {
			distance = MAX_DISTANCE;
		}
		List<EntityLivingBase> targets = new ArrayList<EntityLivingBase>();
		Vec3d vec3 = seeker.getLookVec();
		double targetX = seeker.posX;
		double targetY = seeker.posY + seeker.getEyeHeight() - 0.10000000149011612D;
		double targetZ = seeker.posZ;
		double distanceTraveled = 0;

		while ((int) distanceTraveled < distance) {
			targetX += vec3.xCoord;
			targetY += vec3.yCoord;
			targetZ += vec3.zCoord;
			distanceTraveled += vec3.lengthVector();
			AxisAlignedBB bb = new AxisAlignedBB(targetX-radius, targetY-radius, targetZ-radius, targetX+radius, targetY+radius, targetZ+radius);
			List<EntityLivingBase> list = seeker.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, bb);
			for (EntityLivingBase target : list) {
				if (target != seeker && target.canBeCollidedWith() && isTargetInSight(vec3, seeker, target)) {
					if (!targets.contains(target)) {
						targets.add(target);
					}
				}
			}
		}
		return targets;
	}
	
	private boolean isTargetInSight(Vec3d vec3, EntityLivingBase seeker, Entity target) {
		
		return seeker.canEntityBeSeen(target) && isTargetInFrontOf(seeker, target, 60);
	}
	
	private boolean isTargetInFrontOf(Entity seeker, Entity target, float fov) {
		// thanks again to Battlegear2 for the following code snippet
		double dx = target.posX - seeker.posX;
		double dz;
		for (dz = target.posZ - seeker.posZ; dx * dx + dz * dz < 1.0E-4D; dz = (Math.random() - Math.random()) * 0.01D) {
			dx = (Math.random() - Math.random()) * 0.01D;
		}
		while (seeker.rotationYaw > 360) { seeker.rotationYaw -= 360; }
		while (seeker.rotationYaw < -360) { seeker.rotationYaw += 360; }
		float yaw = (float)(Math.atan2(dz, dx) * 180.0D / Math.PI) - seeker.rotationYaw;
		yaw = yaw - 90;
		while (yaw < -180) { yaw += 360; }
		while (yaw >= 180) { yaw -= 360; }
		
		return yaw < fov && yaw > -fov;
	}
}
