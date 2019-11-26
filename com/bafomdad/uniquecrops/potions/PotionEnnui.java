package com.bafomdad.uniquecrops.potions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PotionEnnui extends CustomPotion {

	public PotionEnnui() {
		
		super("ennui", true, 0xeef442);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public boolean isAfflicted(EntityLivingBase elb) {
		
		return elb.getActivePotionEffect(this) != null;
	}
	
	@SubscribeEvent
	public void onPlayerJump(LivingEvent.LivingJumpEvent event) {
		
		if (this.isAfflicted(event.getEntityLiving()))
			event.getEntityLiving().motionY = 0;
	}
	
	@SubscribeEvent
	public void onPlayerClickBlock(PlayerInteractEvent.RightClickBlock event) {
		
		if (this.isAfflicted(event.getEntityLiving()))
			event.setCanceled(true);
	}
	
	@SubscribeEvent
	public void onPlayerClickItem(PlayerInteractEvent.RightClickItem event) {
		
		if (this.isAfflicted(event.getEntityLiving()))
			event.setCanceled(true);
	}
}
