package com.bafomdad.uniquecrops.core;

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class PotionBehavior {

	private static Map<Potion, Potion> reverseMap = new IdentityHashMap<Potion, Potion>();
	
	static {
		reverseMap.put(MobEffects.NIGHT_VISION, MobEffects.BLINDNESS);
		reverseMap.put(MobEffects.SPEED, MobEffects.SLOWNESS);
		reverseMap.put(MobEffects.REGENERATION, MobEffects.POISON);
		reverseMap.put(MobEffects.SATURATION, MobEffects.HUNGER);
		reverseMap.put(MobEffects.HASTE, MobEffects.MINING_FATIGUE);
		reverseMap.put(MobEffects.STRENGTH, MobEffects.WEAKNESS);
		reverseMap.put(MobEffects.ABSORPTION, MobEffects.WITHER);
		reverseMap.put(MobEffects.LUCK, MobEffects.UNLUCK);
		reverseMap.put(MobEffects.JUMP_BOOST, MobEffects.LEVITATION);
		reverseMap.put(MobEffects.FIRE_RESISTANCE, MobEffects.WATER_BREATHING);
		reverseMap.put(MobEffects.INVISIBILITY, MobEffects.GLOWING);
	}
	
	public static void reverseEffects(EntityPlayer player) {
		
		if (!player.getActivePotionEffects().isEmpty()) {
			Iterator it = player.getActivePotionEffects().iterator();
			while (it.hasNext()) {
				PotionEffect pot = (PotionEffect)it.next();
				setReverseEffects(pot, player);
			}
		}
	}
	
	private static boolean hasMappedKey(Potion pot) {
		
		return reverseMap.containsKey(pot);
	}
	
	private static boolean hasMappedValue(Potion pot) {
		
		return reverseMap.containsValue(pot);
	}
	
	private static void setReverseEffects(PotionEffect pot, EntityPlayer player) {
		
		if (hasMappedKey(pot.getPotion())) {
			player.addPotionEffect(new PotionEffect(reverseMap.get(pot), pot.getDuration(), pot.getAmplifier()));
			player.removePotionEffect(pot.getPotion());
			return;
		}
		else if (hasMappedValue(pot.getPotion())) {
			player.addPotionEffect(new PotionEffect(reverseMap.get(pot), pot.getDuration(), pot.getAmplifier()));
			player.removePotionEffect(pot.getPotion());
			return;
		}
	}
}
