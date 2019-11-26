package com.bafomdad.uniquecrops.items.baubles;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;

import com.bafomdad.uniquecrops.core.EnumEmblems;
import com.google.common.collect.Multimap;

public class EmblemBlacksmith extends ItemBauble {
	
	public EmblemBlacksmith() {
		
		super(EnumEmblems.BLACKSMITH);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public String getDescription() {

		return "blacksmith";
	}

	@Override
	void fillModifiers(Multimap<String, AttributeModifier> attributes, ItemStack stack) {}
	
	@SubscribeEvent
	public void blacksmithAnvil(AnvilRepairEvent event) {
		
		if (event.getEntityPlayer() == null) return;
		
		ItemStack blacksmith = BaublesApi.getBaublesHandler(event.getEntityPlayer()).getStackInSlot(6);
		if (blacksmith.isEmpty() || (!blacksmith.isEmpty() && blacksmith.getItem() != this)) return;
		
		event.setBreakChance(0.0F);
	}
}
