package com.bafomdad.uniquecrops.entities;

import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityItemPlum extends EntityItem {

	public EntityItemPlum(World world) {
		
		super(world);
	}
	
	public EntityItemPlum(World world, EntityItem oldEntity, ItemStack stack) {
		
		super(world, oldEntity.posX, oldEntity.posY, oldEntity.posZ, stack);
		this.motionX = oldEntity.motionX;
		this.motionY = oldEntity.motionY;
		this.motionZ = oldEntity.motionZ;
		this.lifespan = oldEntity.lifespan;
		this.setDefaultPickupDelay();
	}
	
	@Override
	public void onUpdate() {
		
		super.onUpdate();
		double velY = 0;
		if (this.ticksExisted > 40)
		{
			velY = 0.0625D;
			if (this.posY >= 256) {
				this.setDead();
			}
			this.addVelocity(0, velY, 0);
			if (this.ticksExisted % 10 == 0 && this.isCollided)
			{
				UCPacketHandler.sendToNearbyPlayers(world, getPosition(), new PacketUCEffect(EnumParticleTypes.EXPLOSION_NORMAL, this.posX, this.posY, this.posZ, 3));
				this.setDead();
			}
		}
	}
}
