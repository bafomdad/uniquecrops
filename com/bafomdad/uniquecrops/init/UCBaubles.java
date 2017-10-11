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
	
	public static void initRecipes() {
		
		GameRegistry.addShapedRecipe(emblemMelee.getRegistryName(), null, new ItemStack(emblemMelee), " d ", "gDi", " w " , 'd', Items.DIAMOND_SWORD, 'g', Items.GOLDEN_SWORD, 'i', Items.IRON_SWORD, 'w', Items.WOODEN_SWORD, 'D', UCBlocks.darkBlock);
		GameRegistry.addShapedRecipe(emblemScarab.getRegistryName(), null, new ItemStack(emblemScarab), "gGg", "GDG", "gGg", 'g', Items.GOLD_INGOT, 'G', Blocks.GOLD_BLOCK, 'D', UCBlocks.darkBlock);
		GameRegistry.addShapedRecipe(emblemTransformation.getRegistryName(), null, new ItemStack(emblemTransformation), " P ", "fDf", " f ", 'P', UCItems.generic.createStack(EnumItems.POTIONSPLASH), 'f', Items.FEATHER, 'D', UCBlocks.darkBlock);
		GameRegistry.addShapedRecipe(emblemPowerfist.getRegistryName(), null, new ItemStack(emblemPowerfist), " p ", "BDB", " b ", 'p', Items.DIAMOND_PICKAXE, 'B', Items.BLAZE_ROD, 'b', Items.BLAZE_POWDER, 'D', UCBlocks.darkBlock);
		GameRegistry.addShapedRecipe(emblemRainbow.getRegistryName(), null, new ItemStack(emblemRainbow), "RGB", "YDb", "PNW", 'R', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.RED.getMetadata()), 'G', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.GREEN.getMetadata()), 'B', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.BLUE.getMetadata()), 'Y', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.YELLOW.getMetadata()), 'b', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.BLACK.getMetadata()), 'P', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.PURPLE.getMetadata()), 'N', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.BROWN.getMetadata()), 'W', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.WHITE.getMetadata()), 'D', UCBlocks.darkBlock);
		GameRegistry.addShapedRecipe(emblemFood.getRegistryName(), null, new ItemStack(emblemFood), "PPP", "PDP", "PPP", 'P', Items.POTATO, 'D', UCBlocks.darkBlock);
		GameRegistry.addShapedRecipe(emblemIronstomach.getRegistryName(), null, new ItemStack(emblemIronstomach), "SgP", "iDe", "RdC", 'S', Items.COOKED_BEEF, 'g', Items.GOLD_INGOT, 'P', Items.COOKED_PORKCHOP, 'i', Items.IRON_INGOT, 'D', UCBlocks.darkBlock, 'e', Items.EMERALD, 'R', Items.COOKED_RABBIT, 'd', Items.DIAMOND, 'C', Items.COOKED_CHICKEN);
		GameRegistry.addShapedRecipe(emblemDefense.getRegistryName(), null, new ItemStack(emblemDefense), "ABA", "SDS", "ABA", 'A', Items.ARROW, 'B', Items.BOW, 'D', UCBlocks.darkBlock, 'S', Items.SHIELD);
		GameRegistry.addShapedRecipe(emblemLeaf.getRegistryName(), null, new ItemStack(emblemLeaf), "lLl", "LDL", "lLl", 'l', Blocks.LEAVES, 'L', Blocks.LOG, 'D', UCBlocks.darkBlock);
//		GameRegistry.addShapelessRecipe(emblemPacifism.getRegistryName(), null, new ItemStack(emblemPacifism), emblemMelee, emblemScarab, emblemTransformation, emblemPowerfist, emblemRainbow, emblemFood, emblemIronstomach, emblemDefense, emblemLeaf);
	}
	
	private static void registerItemModel(Item item) {
		
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
