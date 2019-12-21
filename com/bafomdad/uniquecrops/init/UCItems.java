package com.bafomdad.uniquecrops.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.enums.EnumEdibleMetal;
import com.bafomdad.uniquecrops.core.enums.EnumFoodstuffs;
import com.bafomdad.uniquecrops.items.*;

public class UCItems {
	
	public static List<Item> items = new ArrayList<Item>();

	// SEEDS
	public static Item 
		seedsNormal,
		seedsPrecision,
		seedsKnowledge,
		seedsDirigible,
		seedsMillennium,
		seedsEnderlily,
		seedsCollis,
		seedsInvisibilia,
		seedsMaryjane,
		seedsWeepingbells,
		seedsMusica,
		seedsCinderbella,
		seedsMerlinia,
		seedsFeroxia,
		seedsEula,
		seedsCobblonia,
		seedsDyeius,
		seedsAbstract,
		seedsWafflonia,
		seedsDevilsnare,
		seedsPixelsius,
		seedsArtisia,
		seedsPetramia,
		seedsMalleatoris,
		seedsImperia,
		seedsLacusia,
		seedsHexis,
		seedsIndustria,
		seedsQuarry,
		seedsDonuts,
		seedsInstabilis,
		seedsSucco,
		seedsAdventus,
		seedsBlessed;
	
	public static ItemGeneric generic;
	
	// FOODS
	public static Item
		largeplum,
		teriyaki,
		heart,
		potionReverse,
		goldenBread,
		dietpills,
		waffle,
		potionEnnui,
		yogurt,
		eggnog,
		edibleDiamond,
		edibleIngotIron,
		edibleIngotGold,
		edibleLapis,
		edibleEmerald,
		edibleNuggetGold,
		edibleBook;
	
	public static Item 
		enderSnooker,
		handMirror,
		batStaff,
		beanBattery,
		wildwoodStaff,
		vampiricOintment,
		steelDonut,
		ankh,
		goodieBag,
		emeradicDiamond,
		uselessLump,
		diamonds,
		bookMultiblock,
		pixelBrush,
		rubiksCube,
		boiledMilk;
	
	public static ItemArmor glasses3D;
	public static ItemArmor pixelGlasses;
	public static ItemArmor poncho;
	public static ItemArmor slippers;
	public static ItemArmor thunderPantz;
	public static ItemTool precisionPick;
	public static ItemTool precisionAxe;
	public static ItemTool precisionShovel;
	public static ItemSword precisionSword;
	public static Item precisionHammer;
	public static ItemArmor cactusBoots, cactusLeggings, cactusPlate, cactusHelm;
	public static Item impactShield;
	public static ItemSword brassKnuckles;
	
	public static ItemVaporRecord music1;
	public static ItemVaporRecord music2;
	public static ItemVaporRecord music3;
	public static ItemVaporRecord music4;
	
	public static final ItemArmor.ArmorMaterial glassesMaterial = EnumHelper.addArmorMaterial("3dglasses", "uniquecrops:3dglasses", 200, new int[] { 0, 0, 0, 0 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0F);
	public static final ItemArmor.ArmorMaterial pixelMaterial = EnumHelper.addArmorMaterial("pixelglasses", "uniquecrops:pixelglasses", 200, new int[] { 0, 0, 0, 0 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0F);
	public static final ItemArmor.ArmorMaterial ponchoMaterial = EnumHelper.addArmorMaterial("poncho", "uniquecrops:poncho", 112, new int[] { 1, 2, 1, 1 }, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0F);
	public static final ItemArmor.ArmorMaterial slipperMaterial = EnumHelper.addArmorMaterial("slippers", "uniquecrops:slippers", 90, new int[] { 1, 1, 1, 1 }, 0, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0F);
	public static final ItemArmor.ArmorMaterial cactusMaterial = EnumHelper.addArmorMaterial("cactus", "uniquecrops:cactus", 13, new int[] { 1, 4, 5, 2 }, 8, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0F);
	public static final ItemArmor.ArmorMaterial thunderMaterial = EnumHelper.addArmorMaterial("thunder", "uniquecrops:thunderpantz", 15, new int[] { 1, 4, 5, 2 }, 6, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0F);
	
	public static void init() {
		
		if (UniqueCrops.baublesLoaded)
			UCBaubles.preInit();
		
		seedsNormal = new ItemSeedsUC(UCBlocks.cropNormal);
		seedsPrecision = new ItemSeedsUC(UCBlocks.cropPrecision);
		seedsKnowledge = new ItemSeedsUC(UCBlocks.cropKnowledge);
		seedsDirigible = new ItemSeedsUC(UCBlocks.cropDirigible);
		seedsMillennium = new ItemSeedsUC(UCBlocks.cropMillennium);
		seedsEnderlily = new ItemSeedsUC(UCBlocks.cropEnderlily);
		seedsCollis = new ItemSeedsUC(UCBlocks.cropCollis);
		seedsInvisibilia = new ItemSeedsUC(UCBlocks.cropInvisibilia);
		seedsMaryjane = new ItemSeedsUC(UCBlocks.cropMaryjane);
		seedsWeepingbells = new ItemSeedsUC(UCBlocks.cropWeepingbells);
		seedsMusica = new ItemSeedsUC(UCBlocks.cropMusica);
		seedsCinderbella = new ItemSeedsUC(UCBlocks.cropCinderbella);
		seedsMerlinia = new ItemSeedsUC(UCBlocks.cropMerlinia);
		seedsFeroxia = new ItemSeedsUC(UCBlocks.cropFeroxia);
		seedsEula = new ItemSeedsUC(UCBlocks.cropEula);
		seedsCobblonia = new ItemSeedsUC(UCBlocks.cropCobblonia);
		seedsDyeius = new ItemSeedsUC(UCBlocks.cropDyeius);
		seedsAbstract = new ItemSeedsUC(UCBlocks.cropAbstract);
		seedsWafflonia = new ItemSeedsUC(UCBlocks.cropWafflonia);
		seedsDevilsnare = new ItemSeedsUC(UCBlocks.cropDevilsnare);
		seedsPixelsius = new ItemSeedsUC(UCBlocks.cropPixelsius);
		seedsArtisia = new ItemSeedsUC(UCBlocks.cropArtisia);
		seedsPetramia = new ItemSeedsUC(UCBlocks.cropPetramia);
		seedsMalleatoris = new ItemSeedsUC(UCBlocks.cropMalleatoris);
		seedsImperia = new ItemSeedsUC(UCBlocks.cropImperia);
		seedsLacusia = new ItemSeedsUC(UCBlocks.cropLacusia);
		seedsHexis = new ItemSeedsUC(UCBlocks.cropHexis);
		seedsIndustria = new ItemSeedsUC(UCBlocks.cropIndustria);
		seedsDonuts = new ItemSeedsUC(UCBlocks.cropDonutSteel);
		seedsInstabilis = new ItemSeedsUC(UCBlocks.cropInstabilis);
		seedsSucco = new ItemSeedsUC(UCBlocks.cropSucco);
		seedsQuarry = new ItemSeedsUC(UCBlocks.cropQuarry);
		seedsAdventus = new ItemSeedsUC(UCBlocks.cropAdventus);
		seedsBlessed = new ItemSeedsUC(UCBlocks.cropHoly);
		
		generic = new ItemGeneric();
		largeplum = new ItemGenericFood(EnumFoodstuffs.LARGEPLUM);
		teriyaki = new ItemGenericFood(EnumFoodstuffs.TERIYAKI).setContainerItem(Items.BOWL);
		heart = new ItemGenericFood(EnumFoodstuffs.HEART);
		potionReverse = new ItemGenericFood(EnumFoodstuffs.REVERSEPOTION);
		goldenBread = new ItemGenericFood(EnumFoodstuffs.GOLDENBREAD);
		dietpills = new ItemGenericFood(EnumFoodstuffs.DIETPILLS);
		waffle = new ItemGenericFood(EnumFoodstuffs.WAFFLE);
		potionEnnui = new ItemGenericFood(EnumFoodstuffs.ENNUIPOTION);
		yogurt = new ItemGenericFood(EnumFoodstuffs.YOGURT).setContainerItem(Items.BOWL);
		eggnog = new ItemGenericFood(EnumFoodstuffs.EGGNOG);
		edibleDiamond = new ItemEdibleMetal(EnumEdibleMetal.GEM, "diamond");
		edibleEmerald = new ItemEdibleMetal(EnumEdibleMetal.GEM, "emerald");
		edibleLapis = new ItemEdibleMetal(EnumEdibleMetal.NUGGET, "lapis");
		edibleIngotIron = new ItemEdibleMetal(EnumEdibleMetal.INGOT, "ironingot");
		edibleIngotGold = new ItemEdibleMetal(EnumEdibleMetal.INGOT, "goldingot");
		edibleNuggetGold = new ItemEdibleMetal(EnumEdibleMetal.NUGGET, "goldnugget");
		edibleBook = new ItemEdibleBook();
		
		enderSnooker = new ItemEnderSnooker();
		handMirror = new ItemHandMirror();
		batStaff = new ItemBatStaff();
		beanBattery = new ItemBeanBattery();
		wildwoodStaff = new ItemWildwoodStaff();
		vampiricOintment = new ItemVampireOintment();
		steelDonut = new ItemSteelDonut();
		ankh = new ItemAnkh();
		goodieBag = new ItemGoodieBag();
		emeradicDiamond = new ItemEmeradicDiamond();
		uselessLump = new Item().setRegistryName("useless_lump").setTranslationKey(UniqueCrops.MOD_ID + ".useless_lump").setCreativeTab(UniqueCrops.TAB);
		items.add(uselessLump);
		diamonds = new ItemDiamondBunch();
		bookMultiblock = new ItemBookMultiblock();
		boiledMilk = new Item().setRegistryName("boiled_milk").setTranslationKey(UniqueCrops.MOD_ID + ".boiled_milk").setCreativeTab(UniqueCrops.TAB).setMaxStackSize(1).setContainerItem(Items.BUCKET);
		items.add(boiledMilk);
		
		glasses3D = new Item3DGlasses(glassesMaterial, 1, EntityEquipmentSlot.HEAD);
		pixelGlasses = new ItemPixelGlasses(pixelMaterial, 1, EntityEquipmentSlot.HEAD);
		poncho = new ItemPoncho(ponchoMaterial, 1, EntityEquipmentSlot.CHEST);
		slippers = new ItemGlassSlippers(slipperMaterial, 2, EntityEquipmentSlot.FEET);
		thunderPantz = new ItemThunderpants(thunderMaterial, 2, EntityEquipmentSlot.LEGS);
		precisionPick = new ItemPrecisionPick();
		precisionAxe = new ItemPrecisionAxe();
		precisionShovel = new ItemPrecisionShovel();
		precisionSword = new ItemPrecisionSword();
		precisionHammer = new ItemPrecisionHammer();
		impactShield = new ItemImpactShield();
		brassKnuckles = new ItemBrassKnuckles();
		pixelBrush = new ItemPixelBrush();
		rubiksCube = new ItemColorfulCube();
		
		music1 = new ItemVaporRecord("neonsigns", UCSounds.MUSIC1);
		music2 = new ItemVaporRecord("faraway", UCSounds.MUSIC2);
		music3 = new ItemVaporRecord("taxi", UCSounds.MUSIC3);
		music4 = new ItemVaporRecord("simply", UCSounds.MUSIC4);
		
		cactusBoots = (ItemArmor)new ItemArmor(cactusMaterial, 2, EntityEquipmentSlot.FEET).setRegistryName("cactusboots").setTranslationKey(UniqueCrops.MOD_ID + ".cactusboots").setCreativeTab(UniqueCrops.TAB);
		items.add(cactusBoots);
		cactusLeggings = (ItemArmor)new ItemArmor(cactusMaterial, 2, EntityEquipmentSlot.LEGS).setRegistryName("cactuslegs").setTranslationKey(UniqueCrops.MOD_ID + ".cactuslegs").setCreativeTab(UniqueCrops.TAB);
		items.add(cactusLeggings);
		cactusPlate = (ItemArmor)new ItemArmor(cactusMaterial, 1, EntityEquipmentSlot.CHEST).setRegistryName("cactusplate").setTranslationKey(UniqueCrops.MOD_ID + ".cactusplate").setCreativeTab(UniqueCrops.TAB);
		items.add(cactusPlate);
		cactusHelm = (ItemArmor)new ItemArmor(cactusMaterial, 1, EntityEquipmentSlot.HEAD).setRegistryName("cactushelm").setTranslationKey(UniqueCrops.MOD_ID + ".cactushelm").setCreativeTab(UniqueCrops.TAB);
		items.add(cactusHelm);
	}
}
