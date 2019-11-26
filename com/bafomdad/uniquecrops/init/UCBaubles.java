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
import net.minecraftforge.registries.IForgeRegistry;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.enums.EnumItems;
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
		emblemPacifism,
		emblemBlacksmith,
		emblemWeight;
	
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
		emblemBlacksmith = new EmblemBlacksmith();
		emblemWeight = new EmblemWeight();
	}
	
	public static void init(RegistryEvent.Register<Item> event) {
		
		IForgeRegistry<Item> r = event.getRegistry();
		r.register(emblemMelee);
		r.register(emblemScarab);
		r.register(emblemTransformation);
		r.register(emblemPowerfist);
		r.register(emblemRainbow);
		r.register(emblemFood);
		r.register(emblemIronstomach);
		r.register(emblemDefense);
		r.register(emblemLeaf);
		r.register(emblemPacifism);
		r.register(emblemBlacksmith);
		r.register(emblemWeight);
	}
}
