package com.bafomdad.uniquecrops.core;

import java.time.LocalDateTime;
import java.time.Month;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class AdventTracker {

	public static void trackAdvent(EntityPlayer player) {
		
		NBTTagCompound data = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
		if (data != null) {
			LocalDateTime current = LocalDateTime.now();
			int day = data.getInteger(UCStrings.TAG_ADVENT);
			
			if (day < current.getDayOfMonth()) {
				data.setInteger(UCStrings.TAG_ADVENT, day++);
				player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, data);
				return;
			}
			else {
				data.setInteger(UCStrings.TAG_ADVENT, 0);
				player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, data);
			}
		}
	}
	
	public static int getTrackedDay(EntityPlayer player) {
		
		NBTTagCompound data = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
		if (data != null) {
			return data.getInteger(UCStrings.TAG_ADVENT);
		}
		return -1;
	}
	
	private static boolean isHoliday(LocalDateTime current) {
		
		return current.getMonth() == Month.DECEMBER && current.getDayOfMonth() <= 25;
	}
}
