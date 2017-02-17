package com.bafomdad.uniquecrops.core;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class UCUtils {
	
	public static EntityPlayer getPlayerFromUsername(String username) {
		
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
			return null;
		
		return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(username);
	}

	public static EntityPlayer getPlayerFromUUID(String uuid) {
		
		return getPlayerFromUsername(getUsernameFromUUID(uuid));
	}
	
	public static String getUsernameFromUUID(String uuid) {
		
		return UsernameCache.getLastKnownUsername(UUID.fromString(uuid));
	}
	
	public static NBTTagList getServerTaglist(int id) {
		
		MinecraftServer ms = FMLCommonHandler.instance().getMinecraftServerInstance();
		EntityPlayer player = (EntityPlayer)ms.getEntityWorld().getEntityByID(id);
		if (player != null) {
			NBTTagCompound tag = player.getEntityData();
			if (tag.hasKey(GrowthSteps.TAG_GROWTHSTAGES)) 
			{
				return tag.getTagList(GrowthSteps.TAG_GROWTHSTAGES, 10);
			}
		}
		return null;
	}
}
