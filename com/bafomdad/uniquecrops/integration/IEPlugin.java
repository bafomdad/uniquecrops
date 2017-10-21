package com.bafomdad.uniquecrops.integration;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class IEPlugin {

	private static class ReflectionFactory {
		
		private Class bellJarHandler;
		private Field cropHandler;
		private Method registerCrop;
		private Class plantHandler;
		private Object instanceAnonymousClass;
		private Class anonymousClass;
		
		public boolean init() {
			try {
				bellJarHandler = Class.forName("blusunrize.immersiveengineering.api.tool.BelljarHandler");
				
				cropHandler = bellJarHandler.getDeclaredField("cropHandler");
				
				plantHandler = Class.forName("blusunrize.immersiveengineering.api.tool.BelljarHandler$DefaultPlantHandler");
				instanceAnonymousClass = cropHandler.get(plantHandler);
				anonymousClass = instanceAnonymousClass.getClass();
				
				registerCrop = anonymousClass.getSuperclass().getMethod("register", ItemStack.class, ItemStack[].class, Object.class, IBlockState[].class);
				
				return true;
			} catch (Throwable t) {
				t.printStackTrace();
				return false;
			}
		}
	}
	
	public static void init() throws Throwable {
		
		ReflectionFactory rFactory = new ReflectionFactory();
		if (!rFactory.init())
			throw new Exception("No valid BellJarHandler class to hook into.");
		
		//System.out.println(rFactory.registerCrop.toString() + " / " + rFactory.registerCrop.getDefaultValue() + " / " + rFactory.registerCrop.getModifiers());
		rFactory.registerCrop.setAccessible(true);
		rFactory.registerCrop.invoke(rFactory.anonymousClass.newInstance(), new ItemStack(UCItems.seedsPetramia), new ItemStack[]{new ItemStack(Blocks.OBSIDIAN), new ItemStack(UCItems.seedsPetramia)}, new ItemStack(Blocks.DIRT), UCBlocks.cropPetramia.getDefaultState());
	}
}
