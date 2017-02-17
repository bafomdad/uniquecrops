package com.bafomdad.uniquecrops.proxies;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.entities.*;
import com.bafomdad.uniquecrops.events.UCEventHandlerServer;
import com.bafomdad.uniquecrops.init.*;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		
		UCBlocks.init();
		UCItems.init();
		EntityRegistry.registerModEntity(EntityCustomPotion.class, UniqueCrops.MOD_ID + "reversepotion", 0, UniqueCrops.instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntityItemWeepingEye.class, UniqueCrops.MOD_ID + "weepingeye", 1, UniqueCrops.instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntityItemHourglass.class, UniqueCrops.MOD_ID + "hourglass", 2, UniqueCrops.instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntityItemPlum.class, UniqueCrops.MOD_ID + "flyingplum", 3, UniqueCrops.instance, 64, 1, true);
	}
	
	public void init(FMLInitializationEvent event) {
		
		UCRecipes.init();
		MinecraftForge.addGrassSeed(new ItemStack(UCItems.seedsNormal), 4);
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		
		MinecraftForge.EVENT_BUS.register(new UCEventHandlerServer());
	}
	
	public void initAllModels() {}
	
	public void initEntityRender() {}
	
	public void checkResource() {}
	
	public boolean invisiTrace() { return true; }
}
