package com.bafomdad.uniquecrops.init;

import com.bafomdad.uniquecrops.UniqueCrops;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class UCSounds {

	public static final SoundEvent OOF = createSound("oof");
	public static final SoundEvent MUSIC1 = createSound("neonsigns");
	public static final SoundEvent MUSIC2 = createSound("faraway");
	public static final SoundEvent MUSIC3 = createSound("taxi");
	public static final SoundEvent MUSIC4 = createSound("simply");
	
	private static SoundEvent createSound(String name) {
		
		ResourceLocation res = new ResourceLocation(UniqueCrops.MOD_ID, name);
		return new SoundEvent(res).setRegistryName(res);
	}
}
