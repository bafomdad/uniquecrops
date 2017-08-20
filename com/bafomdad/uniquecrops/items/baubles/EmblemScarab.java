package com.bafomdad.uniquecrops.items.baubles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import baubles.api.BaubleType;

import com.bafomdad.uniquecrops.core.EnumEmblems;
import com.google.common.collect.Multimap;

public class EmblemScarab extends ItemBauble {

	public EmblemScarab() {
		
		super(EnumEmblems.SCARAB);
	}
	
	@Override
	public String getDescription() {

		return "scarab";
	}

	@Override
	public BaubleType getBaubleType(ItemStack stack) {

		return BaubleType.CHARM;
	}

	@Override
	void fillModifiers(Multimap<String, AttributeModifier> attributes, ItemStack stack) {}
	
	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase player) {
		
		if (!player.getActivePotionEffects().isEmpty())
			player.getActivePotionEffects().clear();
	}
}
