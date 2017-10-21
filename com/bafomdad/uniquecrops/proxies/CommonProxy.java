package com.bafomdad.uniquecrops.proxies;

import net.minecraft.item.ItemStack;
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

		EntityRegistry.registerModEntity(new ResourceLocation(UniqueCrops.MOD_ID, "reversepotion"), EntityCustomPotion.class, UniqueCrops.MOD_ID + "reversepotion", 0, UniqueCrops.instance, 64, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(UniqueCrops.MOD_ID, "weepingeye"), EntityItemWeepingEye.class, UniqueCrops.MOD_ID + "weepingeye", 1, UniqueCrops.instance, 64, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(UniqueCrops.MOD_ID, "hourglass"), EntityItemHourglass.class, UniqueCrops.MOD_ID + "hourglass", 2, UniqueCrops.instance, 64, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(UniqueCrops.MOD_ID, "flyingplum"), EntityItemPlum.class, UniqueCrops.MOD_ID + "flyingplum", 3, UniqueCrops.instance, 64, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(UniqueCrops.MOD_ID, "eulabook"), EntityEulaBook.class, UniqueCrops.MOD_ID + "eulabook", 4, UniqueCrops.instance, 64, 1, true);
	}
	
	public void init(FMLInitializationEvent event) {
		
		UCPacketHandler.initServer();
		MinecraftForge.addGrassSeed(new ItemStack(UCItems.seedsNormal), UCConfig.dropRate);
//		if (UniqueCrops.ieLoaded)
//			try {
//				IEPlugin.init();
//			} catch (Throwable t) {
//				t.printStackTrace();
//			}
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		
		MinecraftForge.EVENT_BUS.register(new UCEventHandlerServer());
	}
	
	public void initAllModels() {}
	
	public void initEntityRender() {}
	
	public void checkResource() {}
	
	public boolean invisiTrace() { return true; }
	
	public void enableBitsShader() {}
	
	public void disableBitsShader() {}
	
	public void registerColors() {}
}
