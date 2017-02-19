package com.bafomdad.uniquecrops.init;

import com.bafomdad.uniquecrops.crafting.*;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class UCRecipes {

	public static void init() {
		
		GameRegistry.addRecipe(UCItems.generic.createStack("guidebook"), " N ", "WBM", " P ", 'N', new ItemStack(UCItems.seedsNormal), 'W', Items.WHEAT_SEEDS, 'M', Items.MELON_SEEDS, 'P', Items.PUMPKIN_SEEDS, 'B', Items.BOOK);
		GameRegistry.addRecipe(UCItems.generic.createStack("eggupgrade"), "IEI", "EME", "IEI", 'I', Items.IRON_INGOT, 'E', Items.EGG, 'M', UCItems.generic.createStack("millenniumeye"));
		GameRegistry.addRecipe(UCItems.generic.createStack("easybadge"), "GEG", "EQE", "GEG", 'G', Items.GOLD_INGOT, 'Q', Blocks.QUARTZ_BLOCK, 'E', UCItems.generic.createStack("millenniumeye"));
		GameRegistry.addRecipe(new ItemStack(UCBlocks.lavalily), " C ", "CLC", " C ", 'C', UCItems.generic.createStack("cinderleaf"), 'L', Blocks.WATERLILY);
		GameRegistry.addRecipe(UCItems.generic.createStack("upgradebook"), "FFF", "FBF", "FFF", 'F', UCItems.generic.createStack("invisifeather"), 'B', UCItems.generic.createStack("discountbook"));
		GameRegistry.addRecipe(new ItemStack(UCItems.largeplum), "PPP", "PPP", "PPP", 'P', UCItems.generic.createStack("dirigibleplum"));
		GameRegistry.addRecipe(new ItemStack(UCItems.goldenbread), "RRR", 'R', UCItems.generic.createStack("goldenrods"));
		GameRegistry.addRecipe(UCItems.generic.createStack("weepingeye"), "TTT", "TET", "TTT", 'T', UCItems.generic.createStack("weepingtear"), 'E', Items.ENDER_EYE);
		GameRegistry.addRecipe(new ItemStack(UCItems.poncho), "P P", "PPP", "PPP", 'P', UCItems.generic.createStack("invisifeather"));
		GameRegistry.addRecipe(UCItems.generic.createStack("invisifeather"), "TTT", "TFT", "TTT", 'T', UCItems.generic.createStack("invisitwine"), 'F', Items.FEATHER);
		GameRegistry.addRecipe(new ItemStack(UCItems.glasses3D), "I I", "I I", "BWR", 'I', Items.IRON_INGOT, 'R', new ItemStack(Blocks.STAINED_GLASS_PANE, 1, EnumDyeColor.RED.getMetadata()), 'W', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.WHITE.getMetadata()), 'B', new ItemStack(Blocks.STAINED_GLASS_PANE, 1, EnumDyeColor.BLUE.getMetadata()));
		GameRegistry.addRecipe(UCItems.generic.createStack("timemeal", 3), " B ", "BCB", " B ", 'B', new ItemStack(Items.DYE, 1, EnumDyeColor.WHITE.getDyeDamage()), 'C', Items.CLOCK);
		GameRegistry.addRecipe(new ItemStack(UCItems.endersnooker), "EPE", "PSP", "EPE", 'E', UCItems.generic.createStack("lilytwine"), 'S', Items.STICK, 'P', Items.ENDER_PEARL);
		GameRegistry.addRecipe(new ItemStack(Items.ENDER_PEARL), "EEE", "ESE", "EEE", 'E', UCItems.generic.createStack("lilytwine"), 'S', Items.SNOWBALL);
		GameRegistry.addRecipe(UCItems.generic.createStack("pregem"), " N ", "NDN", " N ", 'N', UCItems.generic.createStack("prenugget"), 'D', Items.DIAMOND);
		GameRegistry.addRecipe(new ItemStack(UCItems.precisionAxe), "GG ", "GS ", " S ", 'G', UCItems.generic.createStack("pregem"), 'S', Items.STICK);
		GameRegistry.addRecipe(new ItemStack(UCItems.precisionPick), "GGG", " S ", " S ", 'G', UCItems.generic.createStack("pregem"), 'S', Items.STICK);
		GameRegistry.addRecipe(new ItemStack(UCItems.precisionShovel), " G ", " S ", " S ", 'G', UCItems.generic.createStack("pregem"), 'S', Items.STICK);
		GameRegistry.addRecipe(new ItemStack(UCBlocks.hourglass), "DGD", "PDP", "DGD", 'G', Blocks.GOLD_BLOCK, 'P', Blocks.GLASS_PANE, 'D', UCItems.generic.createStack("timedust"));
		GameRegistry.addRecipe(new ItemStack(UCBlocks.totemhead), "LLL", "LML", " S ", 'L', Blocks.LAPIS_BLOCK, 'M', UCItems.generic.createStack("millenniumeye"), 'S', Items.STICK);
		for (EnumDyeColor dye : EnumDyeColor.values()) {
			GameRegistry.addRecipe(getDyeCraftingResult(dye.getDyeDamage()), "FFF", "FDF", "FFF", 'D', new ItemStack(Items.DYE, 1, dye.getMetadata()), 'F', new ItemStack(UCItems.generic, 1, 9));
		}
		GameRegistry.addRecipe(new DiscountBookRecipe());
		BrewingRecipeRegistry.addRecipe(BrewingRecipeRegistry.getOutput(new ItemStack(Items.POTIONITEM), new ItemStack(Items.NETHER_WART)), UCItems.generic.createStack("timedust"), new ItemStack(UCItems.potionreverse));
		BrewingRecipeRegistry.addRecipe(new ItemStack(UCItems.potionreverse), new ItemStack(Items.GUNPOWDER), UCItems.generic.createStack("potionreversesplash"));
		addSeedRecipe(UCItems.seedsCinderbella, UCItems.seedsNormal, Items.WHEAT_SEEDS);
		addSeedRecipe(UCItems.seedsCollis, UCItems.seedsCinderbella, UCItems.seedsNormal);
		addSeedRecipe(UCItems.seedsDirigible, UCItems.seedsCollis, Items.PUMPKIN_SEEDS);
		addSeedRecipe(UCItems.seedsEnderlily, Items.ENDER_EYE, UCItems.seedsDirigible);
		addSeedRecipe(UCItems.seedsInvisibilia, Item.getItemFromBlock(Blocks.GLASS), UCItems.seedsCinderbella);
		addSeedRecipe(UCItems.seedsKnowledge, Items.ENCHANTED_BOOK, UCItems.seedsInvisibilia);
		addSeedRecipe(UCItems.seedsMaryjane, Items.BLAZE_ROD, UCItems.seedsCollis);
		addSeedRecipe(UCItems.seedsMerlinia, UCItems.generic.createStack("timemeal").getItem(), UCItems.seedsEnderlily);
		addSeedRecipe(UCItems.seedsMillennium, Items.CLOCK, UCItems.seedsMerlinia);
		addSeedRecipe(UCItems.seedsMusica, Item.getItemFromBlock(Blocks.JUKEBOX), UCItems.seedsMaryjane);
		addSeedRecipe(UCItems.seedsPrecision, UCItems.seedsInvisibilia, UCItems.seedsNormal);
		addSeedRecipe(UCItems.seedsWeepingbells, Items.GHAST_TEAR, UCItems.seedsEnderlily);
		addSeedRecipe(UCItems.seedsFeroxia, UCItems.seedsKnowledge, UCItems.seedsWeepingbells);
	}
	
	private static ItemStack getDyeCraftingResult(int meta) {
		
		switch (meta) {
			default: return new ItemStack(Items.GHAST_TEAR);
			case 1: return new ItemStack(Items.FIRE_CHARGE, 8, 0);
			case 2: return new ItemStack(Items.DRAGON_BREATH);
			case 3: return new ItemStack(Items.DIAMOND);
			case 4: return new ItemStack(Items.GOLD_INGOT);
			case 5: return new ItemStack(Items.SLIME_BALL, 4, 0);
			case 6: return new ItemStack(Items.SADDLE);
			case 7: return new ItemStack(Blocks.CLAY, 8, 0);
			case 8: return new ItemStack(Items.IRON_INGOT, 2, 0);
			case 9: return new ItemStack(Blocks.STAINED_HARDENED_CLAY, 16, EnumDyeColor.CYAN.getMetadata());
			case 10: return new ItemStack(Items.CHORUS_FRUIT);
			case 11: return new ItemStack(Blocks.PRISMARINE, 8, 0);
			case 12: return new ItemStack(Blocks.DIRT, 3, 0);
			case 13: return new ItemStack(Items.EMERALD);
			case 14: return new ItemStack(Items.NETHER_WART);
			case 15: return new ItemStack(Items.SKULL, 1, 1);
		}
	}
	
	private static void addSeedRecipe(Item result, Item surrounding, Item center) {
		
		GameRegistry.addRecipe(new ItemStack(result), " S ", "SCS", " S ", 'S', surrounding, 'C', center);
	}
}
