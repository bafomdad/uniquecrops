package com.bafomdad.uniquecrops.items.baubles;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;

import com.bafomdad.uniquecrops.core.enums.EnumEmblems;
import com.google.common.collect.Multimap;

public class EmblemPacifism extends ItemBauble {

	public EmblemPacifism() {
		
		super(EnumEmblems.PACIFISM);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public String getDescription() {

		return "pacifism";
	}
	
	@SubscribeEvent
	public void noDamage(LivingAttackEvent event) {
	
		EntityPlayer player = null;
		if (event.getEntityLiving() instanceof EntityPlayer) player = (EntityPlayer)event.getEntityLiving();
		if (event.getSource().getImmediateSource() instanceof EntityPlayer) player = (EntityPlayer)event.getSource().getImmediateSource();
		if (player == null) return;
		
		ItemStack bauble = BaublesApi.getBaublesHandler(player).getStackInSlot(6);
		if (bauble.isEmpty() || (!bauble.isEmpty() && bauble.getItem() != this)) return;

		if (event.getEntityLiving() instanceof EntityPlayer && event.getSource().getImmediateSource() != null || event.getSource().getImmediateSource() == player)
			event.setCanceled(true);
	}

	@Override
	void fillModifiers(Multimap<String, AttributeModifier> attributes, ItemStack stack) {}
}
