package com.bafomdad.uniquecrops.items;

import java.util.ArrayList;
import java.util.List;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.EnumItems;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;

public class ItemEnderSnooker extends Item {

	public ItemEnderSnooker() {
		
		setRegistryName("endersnooker");
		setTranslationKey(UniqueCrops.MOD_ID + ".endersnooker");
		setCreativeTab(UniqueCrops.TAB);
		setMaxDamage(16);
		setMaxStackSize(1);
		UCItems.items.add(this);
	}
	
	@Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        
		if (player.isSneaking()) {
			IBlockState state = world.getBlockState(pos);
			if (state.getBlock() == UCBlocks.darkBlock) {
				if (!world.isRemote) {
					if (pos.getY() <= 1)
						world.setBlockState(pos, Blocks.BEDROCK.getDefaultState(), 2);
					world.setBlockToAir(pos);
				}
				ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(UCBlocks.darkBlock));
				return EnumActionResult.SUCCESS;
			}
		}
		return EnumActionResult.PASS;
    }
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		
		ItemStack stack = player.getHeldItem(hand);
		
		if (!stack.isEmpty() && stack.getItem() == this) {
			List<EntityLivingBase> elb = acquireAllLookTargets(player, 32, 3);
			for (EntityLivingBase target : elb) {
				if (target.canEntityBeSeen(player) && !(target instanceof EntityPlayer) && target.isNonBoss()) {
					BlockPos targetpos = target.getPosition();
					BlockPos playerpos = player.getPosition();
					if (!world.isRemote) {
						target.setPositionAndUpdate(playerpos.getX(), playerpos.getY(), playerpos.getZ());
						player.setPositionAndUpdate(targetpos.getX(), targetpos.getY(), targetpos.getZ());
						if (target instanceof EntityWolf && world.rand.nextInt(100) == 0)
							target.entityDropItem(UCItems.generic.createStack(EnumItems.DOGRESIDUE), 1);
						stack.damageItem(1, player);
						return new ActionResult(EnumActionResult.SUCCESS, stack);
					}
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
			targetX += vec3.x;
			targetY += vec3.y;
			targetZ += vec3.z;
			distanceTraveled += vec3.length();
			AxisAlignedBB bb = new AxisAlignedBB(targetX-radius, targetY-radius, targetZ-radius, targetX+radius, targetY+radius, targetZ+radius);
			List<EntityLivingBase> list = seeker.world.getEntitiesWithinAABB(EntityLivingBase.class, bb);
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
