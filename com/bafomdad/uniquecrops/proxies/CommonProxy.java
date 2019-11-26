package com.bafomdad.uniquecrops.proxies;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.UniqueCropsAPI;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.core.UCDataFixer;
import com.bafomdad.uniquecrops.entities.*;
import com.bafomdad.uniquecrops.events.UCEventHandlerServer;
import com.bafomdad.uniquecrops.events.UCWorldEvents;
import com.bafomdad.uniquecrops.init.*;
import com.bafomdad.uniquecrops.integration.IEPlugin;
import com.bafomdad.uniquecrops.items.ItemGoodieBag;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import com.bafomdad.uniquecrops.world.*;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		
		MinecraftForge.EVENT_BUS.register(new UCEventRegistry());
		MinecraftForge.EVENT_BUS.register(new UCWorldEvents());
		UCConfig.init(event);
		UCDimension.init();
		UCDataFixer.init();
	}
	
	public void init(FMLInitializationEvent event) {
		
		MinecraftForge.addGrassSeed(new ItemStack(UCItems.seedsNormal), UCConfig.dropRate);
		if (ItemGoodieBag.isHoliday())
			MinecraftForge.addGrassSeed(new ItemStack(UCItems.seedsAdventus), UCConfig.dropRate);
		GameRegistry.registerWorldGenerator(new RuinsGenerator(), 1);
		GameRegistry.registerWorldGenerator(new CropIslandGenerator(), 0);
		MinecraftForge.ORE_GEN_BUS.register(new UCOreGen());
		UCConfig.syncMultiblocks();
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		
		MinecraftForge.EVENT_BUS.register(new UCEventHandlerServer());
		if (UniqueCrops.ieLoaded) {
			try {
				IEPlugin.init();
			} catch (Throwable t) {
				t.printStackTrace();
			}
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
	
	public void sparkFX(double x, double y, double z, float r, float g, float b, float size, float motionX, float motionY, float motionZ, float maxAgeMul) {}

	public World getClientWorld() { return null; }
}
