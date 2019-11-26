package com.bafomdad.uniquecrops.integration;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.bafomdad.uniquecrops.core.enums.EnumItems;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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
		
		blusunrize.immersiveengineering.api.tool.BelljarHandler bellHandler = new blusunrize.immersiveengineering.api.tool.BelljarHandler();
		rFactory.registerCrop.invoke(bellHandler.cropHandler, new ItemStack(UCItems.seedsNormal), new ItemStack[]{ new ItemStack(Items.CARROT), new ItemStack(Items.WHEAT), new ItemStack(Items.POTATO), new ItemStack(Items.BEETROOT), new ItemStack(Items.MELON)}, new ItemStack(Blocks.DIRT), new IBlockState[]{UCBlocks.cropNormal.getDefaultState()});
		rFactory.registerCrop.invoke(bellHandler.cropHandler, new ItemStack(UCItems.seedsInvisibilia), new ItemStack[]{ EnumItems.INVISITWINE.createStack(2), new ItemStack(UCItems.seedsInvisibilia)}, new ItemStack(Blocks.DIRT), new IBlockState[]{UCBlocks.cropInvisibilia.getDefaultState()});
		rFactory.registerCrop.invoke(bellHandler.cropHandler, new ItemStack(UCItems.seedsMillennium), new ItemStack[]{ EnumItems.MILLENNIUMEYE.createStack() }, new ItemStack(Blocks.DIRT), new IBlockState[]{UCBlocks.cropMillennium.getDefaultState()});
		rFactory.registerCrop.invoke(bellHandler.cropHandler, new ItemStack(UCItems.seedsCollis), new ItemStack[]{ EnumItems.GOLDENRODS.createStack(3), new ItemStack(UCItems.seedsCollis)}, new ItemStack(Blocks.DIRT), new IBlockState[]{UCBlocks.cropCollis.getDefaultState()});
		rFactory.registerCrop.invoke(bellHandler.cropHandler, new ItemStack(UCItems.seedsPetramia), new ItemStack[]{ new ItemStack(Blocks.OBSIDIAN)}, new ItemStack(Blocks.DIRT), new IBlockState[]{UCBlocks.cropPetramia.getDefaultState()});
		rFactory.registerCrop.invoke(bellHandler.cropHandler, new ItemStack(UCItems.seedsCobblonia), new ItemStack[]{new ItemStack(Blocks.COBBLESTONE, 8)}, new ItemStack(Blocks.DIRT), new IBlockState[]{UCBlocks.cropCobblonia.getDefaultState()});
		rFactory.registerCrop.invoke(bellHandler.cropHandler, new ItemStack(UCItems.seedsFeroxia), new ItemStack[]{ ItemStack.EMPTY}, new ItemStack(Blocks.DIRT), new IBlockState[]{UCBlocks.cropFeroxia.getDefaultState()});
	}
}
