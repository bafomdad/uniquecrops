package com.bafomdad.uniquecrops.entities;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

public class EntityItemWeepingEye extends EntityThrowable {

	public EntityItemWeepingEye(World world) {
		
		super(world);
	}
	public EntityItemWeepingEye(World worldIn, double x, double y, double z) {
		
		super(worldIn, x, y, z);
	}

	@Override
	protected void onImpact(RayTraceResult result) {

		if (!world.isRemote) {
			BlockPos pos;
			if (result.typeOfHit == RayTraceResult.Type.BLOCK)
				 pos = new BlockPos(result.hitVec).offset(result.sideHit);
			else
				pos = new BlockPos(result.hitVec);
			
			List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(this, new AxisAlignedBB(pos.add(-10, -5, -10), pos.add(10, 5, 10)));
			for (Entity ent : entities) {
				if (!ent.isDead && (ent instanceof EntityMob || ent instanceof EntitySlime))
				{
					((EntityLiving)ent).addPotionEffect(new PotionEffect(MobEffects.GLOWING, 300));
				}
			}
			UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticleTypes.CLOUD, pos.getX() - 0.5D, pos.getY() - 0.1D, pos.getZ() - 0.5D, 5));
		}
	}
}
