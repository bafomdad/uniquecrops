package com.bafomdad.uniquecrops.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.potions.CustomPotion;
import com.bafomdad.uniquecrops.potions.PotionEnnui;

public class UCPotions {
	
	public static List<Potion> potions = new ArrayList();

	public static final CustomPotion ENNUI = new PotionEnnui();
	
	public static void init() {
		
		registerPotionEffect(ENNUI);
	}
	
	private static void registerPotionEffect(CustomPotion potion) {
		
		potion.setIcon(potion.getIcon());
		potion.setRegistryName(new ResourceLocation(UniqueCrops.MOD_ID, potion.getName()));
		potions.add(potion);
	}
}
