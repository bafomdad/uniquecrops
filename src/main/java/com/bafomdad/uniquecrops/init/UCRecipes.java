package com.bafomdad.uniquecrops.init;

import java.util.HashMap;
import java.util.Map;

import com.bafomdad.uniquecrops.UniqueCropsAPI;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;

import com.bafomdad.uniquecrops.core.EnumItems;

public class UCRecipes {
	
	public static Map<ItemStack, ItemStack> edibleMetals = new HashMap();

	public static void init() {
		
		initEdibleMetals();
		
		ItemStack awkwardpotion = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionType.getPotionTypeForName("awkward"));
		if (!awkwardpotion.isEmpty()) {
			BrewingRecipeRegistry.addRecipe(awkwardpotion, UCItems.generic.createStack(EnumItems.TIMEDUST), new ItemStack(UCItems.potionreverse));
			BrewingRecipeRegistry.addRecipe(new ItemStack(UCItems.potionreverse), new ItemStack(Items.GUNPOWDER), UCItems.generic.createStack(EnumItems.POTIONSPLASH));
		}
		
		for (EnumDyeColor dye : EnumDyeColor.values()) {
			UniqueCropsAPI.SEED_RECIPE_REGISTRY.addRecipe(getDyeCraftingResult(dye.getDyeDamage()), new ItemStack(Items.DYE, 1, dye.getMetadata()), UCItems.generic.createStack(EnumItems.ESSENCE), UCItems.generic.createStack(EnumItems.ESSENCE));
		}
		UniqueCropsAPI.SEED_RECIPE_REGISTRY.addRecipe(new ItemStack(UCItems.seedsCinderbella), new ItemStack(Items.SUGAR), new ItemStack(Items.WHEAT_SEEDS), new ItemStack(UCItems.seedsNormal));
		UniqueCropsAPI.SEED_RECIPE_REGISTRY.addRecipe(new ItemStack(UCItems.seedsCollis), new ItemStack(Items.SUGAR), new ItemStack(UCItems.seedsNormal), new ItemStack(UCItems.seedsCinderbella));
		UniqueCropsAPI.SEED_RECIPE_REGISTRY.addRecipe(new ItemStack(UCItems.seedsDirigible), new ItemStack(Items.SUGAR), new ItemStack(Items.PUMPKIN_SEEDS), new ItemStack(UCItems.seedsCollis));
		UniqueCropsAPI.SEED_RECIPE_REGISTRY.addRecipe(new ItemStack(UCItems.seedsEnderlily), new ItemStack(Items.ENDER_EYE), new ItemStack(Items.ENDER_PEARL), new ItemStack(UCItems.seedsDirigible));
		UniqueCropsAPI.SEED_RECIPE_REGISTRY.addRecipe(new ItemStack(UCItems.seedsInvisibilia), new ItemStack(Items.SUGAR), new ItemStack(Blocks.GLASS), new ItemStack(UCItems.seedsCinderbella));
		UniqueCropsAPI.SEED_RECIPE_REGISTRY.addRecipe(new ItemStack(UCItems.seedsKnowledge), new ItemStack(Items.SUGAR), new ItemStack(Items.ENCHANTED_BOOK), new ItemStack(UCItems.seedsInvisibilia));
		UniqueCropsAPI.SEED_RECIPE_REGISTRY.addRecipe(new ItemStack(UCItems.seedsMaryjane), new ItemStack(Items.BLAZE_ROD), new ItemStack(Items.BLAZE_POWDER), new ItemStack(UCItems.seedsCollis));
		UniqueCropsAPI.SEED_RECIPE_REGISTRY.addRecipe(new ItemStack(UCItems.seedsMerlinia), new ItemStack(Items.PUMPKIN_SEEDS), UCItems.generic.createStack(EnumItems.TIMEMEAL), new ItemStack(UCItems.seedsEnderlily));
		UniqueCropsAPI.SEED_RECIPE_REGISTRY.addRecipe(new ItemStack(UCItems.seedsMillennium), new ItemStack(Items.CLOCK), new ItemStack(Items.PUMPKIN_SEEDS), new ItemStack(UCItems.seedsMerlinia));
		UniqueCropsAPI.SEED_RECIPE_REGISTRY.addRecipe(new ItemStack(UCItems.seedsMusica), new ItemStack(Blocks.JUKEBOX), new ItemStack(UCItems.seedsNormal), new ItemStack(UCItems.seedsMaryjane));
		UniqueCropsAPI.SEED_RECIPE_REGISTRY.addRecipe(new ItemStack(UCItems.seedsPrecision), new ItemStack(Items.GOLD_NUGGET), new ItemStack(UCItems.seedsCollis), new ItemStack(UCItems.seedsInvisibilia));
		UniqueCropsAPI.SEED_RECIPE_REGISTRY.addRecipe(new ItemStack(UCItems.seedsWeepingbells), new ItemStack(Items.GHAST_TEAR), new ItemStack(Items.MELON_SEEDS), new ItemStack(UCItems.seedsEnderlily));
		UniqueCropsAPI.SEED_RECIPE_REGISTRY.addRecipe(new ItemStack(UCItems.seedsAbstract), new ItemStack(Items.REEDS), new ItemStack(Blocks.STAINED_HARDENED_CLAY), new ItemStack(Blocks.WOOL));
		UniqueCropsAPI.SEED_RECIPE_REGISTRY.addRecipe(new ItemStack(UCItems.seedsCobblonia), new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.STONEBRICK), new ItemStack(UCItems.seedsNormal));
		UniqueCropsAPI.SEED_RECIPE_REGISTRY.addRecipe(new ItemStack(UCItems.seedsDyeius), new ItemStack(Blocks.WOOL), new ItemStack(Items.DYE), new ItemStack(UCItems.seedsAbstract));
		UniqueCropsAPI.SEED_RECIPE_REGISTRY.addRecipe(new ItemStack(UCItems.seedsEula), new ItemStack(Items.PAPER), new ItemStack(Items.BOOK), new ItemStack(UCItems.seedsCobblonia));
		UniqueCropsAPI.SEED_RECIPE_REGISTRY.addRecipe(new ItemStack(UCItems.seedsFeroxia), new ItemStack(Items.CLAY_BALL), new ItemStack(UCItems.seedsKnowledge), new ItemStack(UCItems.seedsWeepingbells));
		UniqueCropsAPI.SEED_RECIPE_REGISTRY.addRecipe(new ItemStack(UCItems.seedsWafflonia), new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.BREAD), new ItemStack(Items.SUGAR));
		UniqueCropsAPI.SEED_RECIPE_REGISTRY.addRecipe(new ItemStack(UCItems.seedsPixelsius), new ItemStack(UCItems.seedsWafflonia), new ItemStack(Items.DYE, 1, EnumDyeColor.BLACK.getDyeDamage()), new ItemStack(Items.PAINTING));
		UniqueCropsAPI.SEED_RECIPE_REGISTRY.addRecipe(new ItemStack(UCItems.seedsDevilsnare), new ItemStack(UCItems.seedsPixelsius), new ItemStack(Items.STICK), new ItemStack(Blocks.LOG));
		UniqueCropsAPI.SEED_RECIPE_REGISTRY.addRecipe(new ItemStack(UCItems.seedsMalleatoris), new ItemStack(UCItems.seedsPrecision), new ItemStack(Blocks.ANVIL), new ItemStack(Items.IRON_INGOT));
		UniqueCropsAPI.SEED_RECIPE_REGISTRY.addRecipe(new ItemStack(UCItems.seedsPetramia), new ItemStack(UCItems.seedsCobblonia), new ItemStack(Blocks.OBSIDIAN), new ItemStack(Blocks.COBBLESTONE));
		UniqueCropsAPI.SEED_RECIPE_REGISTRY.addRecipe(new ItemStack(UCItems.seedsImperia), new ItemStack(UCItems.seedsPetramia), new ItemStack(Blocks.END_ROD), new ItemStack(Blocks.GLOWSTONE));
		UniqueCropsAPI.SEED_RECIPE_REGISTRY.addRecipe(new ItemStack(UCItems.seedsLacusia), new ItemStack(Blocks.HOPPER), new ItemStack(UCItems.seedsNormal), new ItemStack(Items.REDSTONE));
		UniqueCropsAPI.SEED_RECIPE_REGISTRY.addRecipe(new ItemStack(UCItems.seedsHexis), new ItemStack(UCItems.seedsMalleatoris), new ItemStack(Items.WOODEN_SWORD), new ItemStack(Items.DYE, 1, EnumDyeColor.BLUE.getDyeDamage()));
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
	
	private static void initEdibleMetals() {
		
		edibleMetals.put(new ItemStack(Items.DIAMOND), new ItemStack(UCItems.edibleDiamond));
		edibleMetals.put(new ItemStack(Items.IRON_INGOT), new ItemStack(UCItems.edibleIngotIron));
		edibleMetals.put(new ItemStack(Items.GOLD_INGOT), new ItemStack(UCItems.edibleIngotGold));
		edibleMetals.put(new ItemStack(Items.GOLD_NUGGET), new ItemStack(UCItems.edibleNuggetGold));
		edibleMetals.put(new ItemStack(Items.EMERALD), new ItemStack(UCItems.edibleEmerald));
		edibleMetals.put(new ItemStack(Items.DYE, 1, 4), new ItemStack(UCItems.edibleLapis));
	}
}
