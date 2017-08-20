package com.bafomdad.uniquecrops.init;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
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
	
	public static void init() {
		
		if (!UniqueCrops.baublesLoaded) return;
		
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
	
	@SideOnly(Side.CLIENT)
	public static void initModels() {
		
		if (!UniqueCrops.baublesLoaded) return;
		
		registerItemModel(emblemMelee);
		registerItemModel(emblemScarab);
		registerItemModel(emblemTransformation);
		registerItemModel(emblemPowerfist);
		registerItemModel(emblemRainbow);
		registerItemModel(emblemFood);
		registerItemModel(emblemIronstomach);
		registerItemModel(emblemDefense);
		registerItemModel(emblemLeaf);
		registerItemModel(emblemPacifism);
	}
	
	public static void initRecipes() {
		
		if (!UniqueCrops.baublesLoaded) return;
		
		GameRegistry.addRecipe(new ItemStack(emblemMelee), " d ", "gDi", " w " , 'd', Items.DIAMOND_SWORD, 'g', Items.GOLDEN_SWORD, 'i', Items.IRON_SWORD, 'w', Items.WOODEN_SWORD, 'D', UCBlocks.darkBlock);
		GameRegistry.addRecipe(new ItemStack(emblemScarab), "gGg", "GDG", "gGg", 'g', Items.GOLD_INGOT, 'G', Blocks.GOLD_BLOCK, 'D', UCBlocks.darkBlock);
		GameRegistry.addRecipe(new ItemStack(emblemTransformation), " P ", "fDf", " f ", 'P', UCItems.generic.createStack(EnumItems.POTIONSPLASH), 'f', Items.FEATHER, 'D', UCBlocks.darkBlock);
		GameRegistry.addRecipe(new ItemStack(emblemPowerfist), " p ", "BDB", " b ", 'p', Items.DIAMOND_PICKAXE, 'B', Items.BLAZE_ROD, 'b', Items.BLAZE_POWDER, 'D', UCBlocks.darkBlock);
		GameRegistry.addRecipe(new ItemStack(emblemRainbow), "RGB", "YDb", "PNW", 'R', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.RED.getMetadata()), 'G', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.GREEN.getMetadata()), 'B', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.BLUE.getMetadata()), 'Y', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.YELLOW.getMetadata()), 'b', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.BLACK.getMetadata()), 'P', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.PURPLE.getMetadata()), 'N', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.BROWN.getMetadata()), 'W', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.WHITE.getMetadata()), 'D', UCBlocks.darkBlock);
		GameRegistry.addRecipe(new ItemStack(emblemFood), "PPP", "PDP", "PPP", 'P', Items.POTATO, 'D', UCBlocks.darkBlock);
		GameRegistry.addRecipe(new ItemStack(emblemIronstomach), "SgP", "iDe", "RdC", 'S', Items.COOKED_BEEF, 'g', Items.GOLD_INGOT, 'P', Items.COOKED_PORKCHOP, 'i', Items.IRON_INGOT, 'D', UCBlocks.darkBlock, 'e', Items.EMERALD, 'R', Items.COOKED_RABBIT, 'd', Items.DIAMOND, 'C', Items.COOKED_CHICKEN);
		GameRegistry.addRecipe(new ItemStack(emblemDefense), "ABA", "SDS", "ABA", 'A', Items.ARROW, 'B', Items.BOW, 'D', UCBlocks.darkBlock, 'S', Items.SHIELD);
		GameRegistry.addRecipe(new ItemStack(emblemLeaf), "lLl", "LDL", "lLl", 'l', Blocks.LEAVES, 'L', Blocks.LOG, 'D', UCBlocks.darkBlock);
		GameRegistry.addShapelessRecipe(new ItemStack(emblemPacifism), emblemMelee, emblemScarab, emblemTransformation, emblemPowerfist, emblemRainbow, emblemFood, emblemIronstomach, emblemDefense, emblemLeaf);
	}
	
	private static void registerItemModel(Item item) {
		
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
