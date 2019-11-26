package com.bafomdad.uniquecrops.items.baubles;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;

import com.bafomdad.uniquecrops.core.enums.EnumEmblems;
import com.google.common.collect.Multimap;

public class EmblemPowerfist extends ItemBauble {

	public EmblemPowerfist() {
		
		super(EnumEmblems.POWERFIST);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public String getDescription() {

		return "powerfist";
	}

	@SubscribeEvent
	public void fistingSpeed(BreakSpeed event) {
		
		ItemStack powerfist = BaublesApi.getBaublesHandler(event.getEntityPlayer()).getStackInSlot(6);
		if (powerfist.isEmpty() || (!powerfist.isEmpty() && powerfist.getItem() != this)) return;
		
		ItemStack mininghand = event.getEntityPlayer().getHeldItemMainhand();
		if (!mininghand.isEmpty()) return;
		
		if (event.getNewSpeed() < 8.0F)
			event.setNewSpeed(8.0F);
	}

	@Override
	void fillModifiers(Multimap<String, AttributeModifier> attributes, ItemStack stack) {}
}
