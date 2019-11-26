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

import com.bafomdad.uniquecrops.core.PotionBehavior;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

public class EntityCustomPotion extends EntityPotion {
	
	public EntityCustomPotion(World world) {
		
		super(world);
	}

	public EntityCustomPotion(World worldIn, EntityLivingBase throwerIn, ItemStack potionDamageIn) {
		
		super(worldIn, throwerIn, potionDamageIn);
	}
	
	@Override
    protected void onImpact(RayTraceResult result) {
    	
		if (!world.isRemote) {
			BlockPos pos = new BlockPos(result.hitVec);
			if (result.typeOfHit == RayTraceResult.Type.ENTITY) {
				Entity ent = result.entityHit;
				if (!ent.isDead && ent instanceof EntityPlayer)
					PotionBehavior.reverseEffects((EntityPlayer)ent);
			}
			UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticleTypes.SPELL, pos.getX() - 0.5D, pos.getY() - 0.5D, pos.getZ() - 0.5D, 5));
			this.setDead();
		}
    }
}
