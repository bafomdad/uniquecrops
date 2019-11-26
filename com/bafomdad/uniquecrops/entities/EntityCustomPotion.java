package com.bafomdad.uniquecrops.entities;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import com.bafomdad.uniquecrops.potions.PotionBehavior;

public class EntityCustomPotion extends EntityPotion {
	
	public EntityCustomPotion(World world) {
		
		super(world);
	}

	public EntityCustomPotion(World worldIn, EntityLivingBase throwerIn, ItemStack potionDamageIn) {
		
		super(worldIn, throwerIn, potionDamageIn);
	}
	
	@Override
    public void onImpact(RayTraceResult result) {
    	
		if (!world.isRemote) {
			applyPotion(result);
			this.setDead();
//			BlockPos pos = new BlockPos(result.hitVec);
//			if (result.typeOfHit == RayTraceResult.Type.ENTITY) {
//				Entity ent = result.entityHit;
//				if (!ent.isDead && ent instanceof EntityPlayer)
//					PotionBehavior.reverseEffects((EntityPlayer)ent);
//			}
//			UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticleTypes.SPELL, pos.getX() - 0.5D, pos.getY() - 0.5D, pos.getZ() - 0.5D, 5));
//			this.setDead();
		}
    }
	
	private void applyPotion(RayTraceResult result) {
		
		AxisAlignedBB aabb = this.getEntityBoundingBox().grow(5.0D, 5.0D, 5.0D);
		List<EntityPlayer> list = this.world.getEntitiesWithinAABB(EntityPlayer.class, aabb);
		
		if (!list.isEmpty()) {
			for (EntityPlayer player : list) {
				double d0 = this.getDistanceSq(player);
				if (d0 < 4.0D) {
					PotionBehavior.reverseEffects(player);
					if (this.world instanceof WorldServer) {
						((WorldServer)this.world).spawnParticle(EnumParticleTypes.SPELL_INSTANT, result.hitVec.x, result.hitVec.y, result.hitVec.z, this.world.rand.nextInt(3) + 2, 0.5, 0.5, 0.5, 0, 0);
					}
				}
			}
		}
	}
}
