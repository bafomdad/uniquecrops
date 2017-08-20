package com.bafomdad.uniquecrops.items.baubles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import baubles.api.BaubleType;

import com.bafomdad.uniquecrops.core.EnumEmblems;
import com.google.common.collect.Multimap;

public class EmblemFood extends ItemBauble {

	public EmblemFood() {
		
		super(EnumEmblems.FOOD);
		setMaxDamage(50);
	}

	@Override
	public String getDescription() {

		return "food";
	}

	@Override
	public BaubleType getBaubleType(ItemStack stack) {

		return BaubleType.CHARM;
	}
	
	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase player) {
		
		if (!player.worldObj.isRemote && player instanceof EntityPlayerMP) {
			EntityPlayerMP playerMP = (EntityPlayerMP)player;
			int diff = 20 - playerMP.getFoodStats().getFoodLevel();
			if (playerMP.getFoodStats().needFood() && diff >= 5) {
				playerMP.getFoodStats().addStats(6, 0.6F);
				stack.damageItem(1, player);
			}
		}
	}

	@Override
	void fillModifiers(Multimap<String, AttributeModifier> attributes, ItemStack stack) {}
}
