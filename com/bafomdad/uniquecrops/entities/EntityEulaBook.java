package com.bafomdad.uniquecrops.entities;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.PotionBehavior;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityEulaBook extends EntityPotion {

	
	public EntityEulaBook(World world) {
		
		super(world);
	}
	
	public EntityEulaBook(World worldIn, EntityLivingBase throwerIn, ItemStack potionDamageIn) {
		
		super(worldIn, throwerIn, potionDamageIn);
	}
	
	@Override
    protected void onImpact(RayTraceResult result) {
    	
		BlockPos pos = new BlockPos(result.hitVec);
		if (result.typeOfHit == RayTraceResult.Type.ENTITY) {
			Entity ent = result.entityHit;
			if (!ent.isDead && ent instanceof EntityPlayer && ent.worldObj.isRemote)
				((EntityPlayer)ent).openGui(UniqueCrops.instance, 1, worldObj, 0, 0, 0);
			
			UCPacketHandler.sendToNearbyPlayers(worldObj, pos, new PacketUCEffect(EnumParticleTypes.CRIT, pos.getX() - 0.5D, pos.getY() - 0.5D, pos.getZ() - 0.5D, 5));
		}
    }
}
