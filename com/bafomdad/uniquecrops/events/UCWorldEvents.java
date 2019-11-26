package com.bafomdad.uniquecrops.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.bafomdad.uniquecrops.init.UCDimension;

public class UCWorldEvents {

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onBlockRightClick(PlayerInteractEvent.RightClickBlock event) {
		
		if (event.getWorld().provider.getDimension() != UCDimension.dimID) return;
		
		if (event.getWorld().getBlockState(event.getPos()).getBlock() != Blocks.CHEST)
			event.setCanceled(true);
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onItemRightClick(PlayerInteractEvent.RightClickItem event) {
		
		if (event.getWorld().provider.getDimension() != UCDimension.dimID) return;
		
		event.setCanceled(true);
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onPlayerUpdate(LivingEvent.LivingUpdateEvent event) {
		
		if (event.getEntityLiving().world.provider.getDimension() != UCDimension.dimID) return;
		
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)event.getEntityLiving();
			if (!player.capabilities.isCreativeMode) {
				if (player.capabilities.allowFlying)
					player.capabilities.allowFlying = false;
				if (player.capabilities.isFlying)
					player.capabilities.isFlying = false;
			}
		}
	}
}
