package com.bafomdad.uniquecrops.init;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.EnumItems;
import com.bafomdad.uniquecrops.items.baubles.*;

public class UCBaubles {

	public static ItemBauble
		emblemMelee,
		emblemScarab,
		emblemTransformation,
		emblemPowerfist,
		emblemRainbow,
		emblemFood,
		emblemIronstomach,
		emblemDefense,
		emblemLeaf,
		emblemPacifism;
	
	public static void preInit() {
		
		emblemMelee = new EmblemMelee();
		emblemScarab = new EmblemScarab();
		emblemTransformation = new EmblemTransformation();
		emblemPowerfist = new EmblemPowerfist();
		emblemRainbow = new EmblemRainbow();
		emblemFood = new EmblemFood();
		emblemIronstomach = new EmblemIronstomach();
		emblemDefense = new EmblemDefense();
		emblemLeaf = new EmblemLeaf();
		emblemPacifism = new EmblemPacifism();
	}
	
	public static void init(RegistryEvent.Register<Item> event) {
		
		event.getRegistry().register(emblemMelee);
		event.getRegistry().register(emblemScarab);
		event.getRegistry().register(emblemTransformation);
		event.getRegistry().register(emblemPowerfist);
		event.getRegistry().register(emblemRainbow);
		event.getRegistry().register(emblemFood);
		event.getRegistry().register(emblemIronstomach);
		event.getRegistry().register(emblemDefense);
		event.getRegistry().register(emblemLeaf);
		event.getRegistry().register(emblemPacifism);
	}
	
	private static void registerItemModel(Item item) {
		
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
