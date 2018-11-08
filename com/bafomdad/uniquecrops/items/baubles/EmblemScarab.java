package com.bafomdad.uniquecrops.items.baubles;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import baubles.api.BaubleType;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.EnumEmblems;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

public class EmblemScarab extends ItemBauble {
	
	private static final List<String> BLACKLIST = Lists.newArrayList();
			
	public EmblemScarab() {
		
		super(EnumEmblems.SCARAB);
		FMLInterModComms.sendMessage(UniqueCrops.MOD_ID, UCStrings.BLACKLIST_EFFECT, "potion.awkward");
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
			player.getActivePotionEffects().removeIf(effect -> !BLACKLIST.equals(effect.getEffectName()));
	}
	
	public static void addPotionEffectToBlacklist(String effect) {
		
		BLACKLIST.add(effect);
	}
}
