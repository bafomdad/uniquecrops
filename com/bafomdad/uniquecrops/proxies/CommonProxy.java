package com.bafomdad.uniquecrops.proxies;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.events.UCEventHandlerServer;
import com.bafomdad.uniquecrops.init.*;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		
		UCBlocks.init();
		UCItems.init();
		UCEntities.init();
	}
	
	public void init(FMLInitializationEvent event) {
		
		UCRecipes.init();
		UCPacketHandler.initServer();
		MinecraftForge.addGrassSeed(new ItemStack(UCItems.seedsNormal), UCConfig.dropRate);
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		
		MinecraftForge.EVENT_BUS.register(new UCEventHandlerServer());
	}
	
	public void initAllModels() {}
	
	public void checkResource() {}
	
	public void flareFX(World world, double x, double y, double z, float mx, float my, float mz, float r, float g, float b, float scale, int age) {}
	
	public boolean invisiTrace() { return true; }
	
	public void enableBitsShader() {}
	
	public void disableBitsShader() {}
}
