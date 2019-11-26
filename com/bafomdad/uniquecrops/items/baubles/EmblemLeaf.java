package com.bafomdad.uniquecrops.items.baubles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import baubles.api.BaubleType;

import com.bafomdad.uniquecrops.core.EnumEmblems;
import com.google.common.collect.Multimap;

public class EmblemLeaf extends ItemBauble {
	
	private int armorCount;

	public EmblemLeaf() {
		
		super(EnumEmblems.LEAF);
	}

	@Override
	public String getDescription() {

		return "leaf";
	}
	
	@Override
	public void onEquippedOrLoadedIntoWorld(ItemStack stack, EntityLivingBase player) {
		
		attributes.clear();
		armorCount = 0;
		for (ItemStack armor : ((EntityPlayer)player).inventory.armorInventory) {
			if (armor != null && armor.getItem() instanceof ItemArmor) {
				if (((ItemArmor)armor.getItem()).getArmorMaterial() != ArmorMaterial.LEATHER)
					armorCount++;
			}
		}
		fillModifiers(attributes, stack);
		player.getAttributeMap().applyAttributeModifiers(attributes);
	}
	
	@Override
	public void onUnequipped(ItemStack stack, EntityLivingBase player) {
		
		attributes.clear();
		armorCount = 0;
		fillModifiers(attributes, stack);
		player.getAttributeMap().removeAttributeModifiers(attributes);
	}

	@Override
	void fillModifiers(Multimap<String, AttributeModifier> attributes, ItemStack stack) {

		if (armorCount > 0)
			attributes.put(SharedMonsterAttributes.ARMOR_TOUGHNESS.getName(), new AttributeModifier(getBaubleUUID(stack), "Bauble modifier", 0.5F * armorCount, 0));
	}
}
