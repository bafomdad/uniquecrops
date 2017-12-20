package com.bafomdad.uniquecrops.items.baubles;

import java.util.Collection;

import com.bafomdad.uniquecrops.core.EnumEmblems;
import com.google.common.collect.Multimap;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.item.ItemStack;
import baubles.api.BaubleType;

public class EmblemMelee extends ItemBauble {

	public EmblemMelee() {
		
		super(EnumEmblems.MELEE);
	}
	
	@Override
	public String getDescription() {
		
		return "melee";
	}

	@Override
	public BaubleType getBaubleType(ItemStack stack) {

		return BaubleType.CHARM;
	}
	
	@Override
	public void onEquippedOrLoadedIntoWorld(ItemStack stack, EntityLivingBase player) {
		
		attributes.clear();
		fillModifiers(attributes, stack);
		player.getAttributeMap().applyAttributeModifiers(attributes);
	}
	
	@Override
	public void onUnequipped(ItemStack stack, EntityLivingBase player) {
		
		attributes.clear();
		fillModifiers(attributes, stack);
		player.getAttributeMap().removeAttributeModifiers(attributes);
	}

	@Override
	void fillModifiers(Multimap<String, AttributeModifier> attributes, ItemStack stack) {

		attributes.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(getBaubleUUID(stack), "Bauble modifier", 1, 0));
	}
}
