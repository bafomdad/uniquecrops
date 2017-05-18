package com.bafomdad.uniquecrops.proxies;

import net.minecraft.client.particle.Particle;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.entities.*;
import com.bafomdad.uniquecrops.events.UCEventHandlerServer;
import com.bafomdad.uniquecrops.init.*;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		
		UCBlocks.init();
		UCItems.init();
		EntityRegistry.registerModEntity(EntityCustomPotion.class, UniqueCrops.MOD_ID + "reversepotion", 0, UniqueCrops.instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntityItemWeepingEye.class, UniqueCrops.MOD_ID + "weepingeye", 1, UniqueCrops.instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntityItemHourglass.class, UniqueCrops.MOD_ID + "hourglass", 2, UniqueCrops.instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntityItemPlum.class, UniqueCrops.MOD_ID + "flyingplum", 3, UniqueCrops.instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntityEulaBook.class, UniqueCrops.MOD_ID + "eulabook", 4, UniqueCrops.instance, 64, 1, true);
	}
	
	public void init(FMLInitializationEvent event) {
		
		UCRecipes.init();
		MinecraftForge.addGrassSeed(new ItemStack(UCItems.seedsNormal), UCConfig.dropRate);
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		
		MinecraftForge.EVENT_BUS.register(new UCEventHandlerServer());
	}
	
	public void initAllModels() {}
	
	public void initEntityRender() {}
	
	public void checkResource() {}
	
	public void flareFX(World world, double x, double y, double z, float mx, float my, float mz, float r, float g, float b, float scale, int age) {}
	
	public boolean invisiTrace() { return true; }
	
	public void enableBitsShader() {}
	
	public void disableBitsShader() {}
}
