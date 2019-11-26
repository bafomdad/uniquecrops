package com.bafomdad.uniquecrops.proxies;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.oredict.OreDictionary;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.entities.*;
import com.bafomdad.uniquecrops.events.UCEventHandlerServer;
import com.bafomdad.uniquecrops.init.*;
import com.bafomdad.uniquecrops.integration.IEPlugin;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		
		MinecraftForge.EVENT_BUS.register(new UCEventRegistry());
	}
	
	public void init(FMLInitializationEvent event) {
		
		MinecraftForge.addGrassSeed(new ItemStack(UCItems.seedsNormal), UCConfig.dropRate);
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		
		MinecraftForge.EVENT_BUS.register(new UCEventHandlerServer());
		if (UniqueCrops.ieLoaded)
			try {
				IEPlugin.init();
			} catch (Throwable t) {
				t.printStackTrace();
			}
	}
	
	public void initAllModels() {}
	
	public void initEntityRender() {}
	
	public void checkResource() {}
	
	public boolean invisiTrace() { return true; }
	
	public void enableBitsShader() {}
	
	public void disableBitsShader() {}
	
	public void killMirror(EntityMirror mirror) {}
	
	public EntityPlayer getPlayer() { return null; }
	
	public void openEulaBook(EntityPlayer player) {}
	
	public void spawnParticles(EnumParticleTypes particle, double x, double y, double z, int loopSize) {}
}
