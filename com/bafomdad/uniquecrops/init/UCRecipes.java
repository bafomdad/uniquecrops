package com.bafomdad.uniquecrops.init;

import java.util.Arrays;

import com.bafomdad.uniquecrops.core.EnumItems;
import com.bafomdad.uniquecrops.crafting.*;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class UCRecipes {

	public static void init() {
		
		ItemStack precisionAxe = new ItemStack(UCItems.precisionAxe);
		precisionAxe.addEnchantment(Enchantment.getEnchantmentByID(33), 1);
		ItemStack precisionShovel = new ItemStack(UCItems.precisionShovel);
		precisionShovel.addEnchantment(Enchantment.getEnchantmentByID(33), 1);
		ItemStack precisionPick = new ItemStack(UCItems.precisionPick);
		precisionPick.addEnchantment(Enchantment.getEnchantmentByID(33), 1);
		GameRegistry.addRecipe(precisionAxe, "GG ", "GS ", " S ", 'G', UCItems.generic.createStack(EnumItems.PREGEM), 'S', Items.STICK);
		GameRegistry.addRecipe(precisionPick, "GGG", " S ", " S ", 'G', UCItems.generic.createStack(EnumItems.PREGEM), 'S', Items.STICK);
		GameRegistry.addRecipe(precisionShovel, " G ", " S ", " S ", 'G', UCItems.generic.createStack(EnumItems.PREGEM), 'S', Items.STICK);
		
		GameRegistry.addShapelessRecipe(new ItemStack(UCItems.dietpills), UCItems.generic.createStack(EnumItems.ABSTRACT), UCItems.generic.createStack(EnumItems.ABSTRACT), Items.GLASS_BOTTLE);
		GameRegistry.addShapelessRecipe(new ItemStack(UCItems.seedsAbstract), UCItems.generic.createStack(EnumItems.ABSTRACT));
		
		GameRegistry.addRecipe(UCItems.generic.createStack(EnumItems.GUIDE), " N ", "WBM", " P ", 'N', new ItemStack(UCItems.seedsNormal), 'W', Items.WHEAT_SEEDS, 'M', Items.MELON_SEEDS, 'P', Items.PUMPKIN_SEEDS, 'B', Items.BOOK);
		GameRegistry.addRecipe(UCItems.generic.createStack(EnumItems.EGGUPGRADE), "IEI", "EME", "IEI", 'I', Items.IRON_INGOT, 'E', Items.EGG, 'M', UCItems.generic.createStack(EnumItems.MILLENNIUMEYE));
		GameRegistry.addRecipe(UCItems.generic.createStack(EnumItems.EASYBADGE), "GEG", "EQE", "GEG", 'G', Items.GOLD_INGOT, 'Q', Blocks.QUARTZ_BLOCK, 'E', UCItems.generic.createStack(EnumItems.MILLENNIUMEYE));
		GameRegistry.addRecipe(new ItemStack(UCBlocks.lavalily), " C ", "CLC", " C ", 'C', UCItems.generic.createStack(EnumItems.CINDERLEAF), 'L', Blocks.WATERLILY);
		GameRegistry.addRecipe(UCItems.generic.createStack(EnumItems.UPGRADE), "FFF", "FBF", "FFF", 'F', UCItems.generic.createStack(EnumItems.INVISIFEATHER), 'B', UCItems.generic.createStack(EnumItems.DISCOUNT));
		GameRegistry.addRecipe(new ItemStack(UCItems.largeplum), "PPP", "PPP", "PPP", 'P', UCItems.generic.createStack(EnumItems.PLUM));
		GameRegistry.addRecipe(new ItemStack(UCItems.goldenbread), "RRR", 'R', UCItems.generic.createStack(EnumItems.GOLDENRODS));
		GameRegistry.addRecipe(UCItems.generic.createStack(EnumItems.WEEPINGEYE), "TTT", "TET", "TTT", 'T', UCItems.generic.createStack(EnumItems.WEEPINGTEAR), 'E', Items.ENDER_EYE);
		GameRegistry.addRecipe(new ItemStack(UCItems.poncho), "P P", "PPP", "PPP", 'P', UCItems.generic.createStack(EnumItems.INVISIFEATHER));
		GameRegistry.addRecipe(UCItems.generic.createStack(EnumItems.INVISIFEATHER), "TTT", "TFT", "TTT", 'T', UCItems.generic.createStack(EnumItems.INVISITWINE), 'F', Items.FEATHER);
		GameRegistry.addRecipe(new ItemStack(UCItems.glasses3D), "I I", "I I", "BWR", 'I', Items.IRON_INGOT, 'R', new ItemStack(Blocks.STAINED_GLASS_PANE, 1, EnumDyeColor.RED.getMetadata()), 'W', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.WHITE.getMetadata()), 'B', new ItemStack(Blocks.STAINED_GLASS_PANE, 1, EnumDyeColor.BLUE.getMetadata()));
		GameRegistry.addRecipe(new ItemStack(UCItems.pixelglasses), "PPP", "PGP", "PP", 'G', UCItems.glasses3D, 'P', UCItems.generic.createStack(EnumItems.PIXELS));
		GameRegistry.addRecipe(UCItems.generic.createStack(EnumItems.TIMEMEAL, 3), " B ", "BCB", " B ", 'B', new ItemStack(Items.DYE, 1, EnumDyeColor.WHITE.getDyeDamage()), 'C', Items.CLOCK);
		GameRegistry.addRecipe(new ItemStack(UCItems.endersnooker), "EPE", "PSP", "EPE", 'E', UCItems.generic.createStack(EnumItems.LILYTWINE), 'S', Items.STICK, 'P', Items.ENDER_PEARL);
		GameRegistry.addRecipe(new ItemStack(Items.ENDER_PEARL), "EEE", "ESE", "EEE", 'E', UCItems.generic.createStack(EnumItems.LILYTWINE), 'S', Items.SNOWBALL);
		GameRegistry.addRecipe(UCItems.generic.createStack(EnumItems.PREGEM), " N ", "NDN", " N ", 'N', UCItems.generic.createStack(EnumItems.PRENUGGET), 'D', Items.DIAMOND);
		GameRegistry.addRecipe(new ItemStack(UCBlocks.hourglass), "DGD", "PDP", "DGD", 'G', Blocks.GOLD_BLOCK, 'P', Blocks.GLASS_PANE, 'D', UCItems.generic.createStack(EnumItems.TIMEDUST));
		GameRegistry.addRecipe(new ItemStack(UCBlocks.totemhead), "LLL", "LML", " S ", 'L', Blocks.LAPIS_BLOCK, 'M', UCItems.generic.createStack(EnumItems.MILLENNIUMEYE), 'S', Items.STICK);
		GameRegistry.addRecipe(UCItems.generic.createStack(EnumItems.EULA), " L ", "LBL", " L ", 'L', UCItems.generic.createStack(EnumItems.LEGALSTUFF), 'B', Items.BOOK);
		GameRegistry.addRecipe(new ItemStack(UCItems.seedsArtisia), " S ", "SCS", " S ", 'S', UCItems.seedsNormal, 'C', Blocks.CRAFTING_TABLE);
		for (EnumDyeColor dye : EnumDyeColor.values()) {
			GameRegistry.addRecipe(getDyeCraftingResult(dye.getDyeDamage()), "FFF", "FDF", "FFF", 'D', new ItemStack(Items.DYE, 1, dye.getMetadata()), 'F', new ItemStack(UCItems.generic, 1, 9));
		}
		GameRegistry.addRecipe(new DiscountBookRecipe());
		BrewingRecipeRegistry.addRecipe(BrewingRecipeRegistry.getOutput(new ItemStack(Items.POTIONITEM), new ItemStack(Items.NETHER_WART)), UCItems.generic.createStack(EnumItems.TIMEDUST), new ItemStack(UCItems.potionreverse));
		BrewingRecipeRegistry.addRecipe(new ItemStack(UCItems.potionreverse), new ItemStack(Items.GUNPOWDER), UCItems.generic.createStack(EnumItems.POTIONSPLASH));
		
		addSeedRecipe(UCItems.seedsCinderbella, new ItemStack(Items.SUGAR), new ItemStack(Items.WHEAT_SEEDS), new ItemStack(UCItems.seedsNormal));
		addSeedRecipe(UCItems.seedsCollis, new ItemStack(Items.SUGAR), new ItemStack(UCItems.seedsNormal), new ItemStack(UCItems.seedsCinderbella));
		addSeedRecipe(UCItems.seedsDirigible, new ItemStack(Items.SUGAR), new ItemStack(Items.PUMPKIN_SEEDS), new ItemStack(UCItems.seedsCollis));
		addSeedRecipe(UCItems.seedsEnderlily, new ItemStack(Items.ENDER_EYE), new ItemStack(Items.ENDER_PEARL), new ItemStack(UCItems.seedsDirigible));
		addSeedRecipe(UCItems.seedsInvisibilia, new ItemStack(Items.SUGAR), new ItemStack(Blocks.GLASS), new ItemStack(UCItems.seedsCinderbella));
		addSeedRecipe(UCItems.seedsKnowledge, new ItemStack(Items.SUGAR), new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(UCItems.seedsInvisibilia));
		addSeedRecipe(UCItems.seedsMaryjane, new ItemStack(Items.BLAZE_ROD), new ItemStack(Items.BLAZE_POWDER), new ItemStack(UCItems.seedsCollis));
		addSeedRecipe(UCItems.seedsMerlinia, new ItemStack(Items.PUMPKIN_SEEDS), UCItems.generic.createStack(EnumItems.TIMEMEAL), new ItemStack(UCItems.seedsEnderlily));
		addSeedRecipe(UCItems.seedsMillennium, new ItemStack(Items.CLOCK), new ItemStack(Items.PUMPKIN_SEEDS), new ItemStack(UCItems.seedsMerlinia));
		addSeedRecipe(UCItems.seedsMusica, new ItemStack(Blocks.JUKEBOX), new ItemStack(UCItems.seedsNormal), new ItemStack(UCItems.seedsMaryjane));
		addSeedRecipe(UCItems.seedsPrecision, new ItemStack(Items.GOLD_NUGGET), new ItemStack(UCItems.seedsCollis), new ItemStack(UCItems.seedsInvisibilia));
		addSeedRecipe(UCItems.seedsWeepingbells, new ItemStack(Items.GHAST_TEAR), new ItemStack(Items.MELON_SEEDS), new ItemStack(UCItems.seedsEnderlily));
		addSeedRecipe(UCItems.seedsAbstract, new ItemStack(Items.REEDS), new ItemStack(Blocks.STAINED_HARDENED_CLAY), new ItemStack(Blocks.WOOL));
		addSeedRecipe(UCItems.seedsCobblonia, new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.STONEBRICK), new ItemStack(UCItems.seedsNormal));
		addSeedRecipe(UCItems.seedsDyeius, new ItemStack(Blocks.WOOL), new ItemStack(Items.DYE), new ItemStack(UCItems.seedsAbstract));
		addSeedRecipe(UCItems.seedsEula, new ItemStack(Items.PAPER), new ItemStack(Items.BOOK), new ItemStack(UCItems.seedsCobblonia));
		addSeedRecipe(UCItems.seedsFeroxia, new ItemStack(Items.CLAY_BALL), new ItemStack(UCItems.seedsKnowledge), new ItemStack(UCItems.seedsWeepingbells));
		addSeedRecipe(UCItems.seedsWafflonia, new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.BREAD), new ItemStack(Items.SUGAR));
		addSeedRecipe(UCItems.seedsPixelsius, new ItemStack(UCItems.seedsWafflonia), new ItemStack(Items.DYE, 1, EnumDyeColor.BLACK.getDyeDamage()), new ItemStack(Items.PAINTING));
		addSeedRecipe(UCItems.seedsDevilsnare, new ItemStack(UCItems.seedsPixelsius), new ItemStack(Items.STICK), new ItemStack(Blocks.LOG));
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
	
	private static void addSeedRecipe(Item result, ItemStack center, ItemStack corner, ItemStack edge) {
		
		UCrafting.addRecipe(new ItemStack(result), new ItemStack[] { corner, edge, corner, edge, center, edge, corner, edge, corner });
	}
}
