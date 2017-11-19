package com.bafomdad.uniquecrops.init;

import java.util.ArrayList;
import java.util.List;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.EnumEdibleMetal;
import com.bafomdad.uniquecrops.core.EnumItems;
import com.bafomdad.uniquecrops.items.*;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

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
		seedsHexis;
	
	public static ItemGeneric generic;
	public static Item largeplum;
	public static Item teriyaki;
	public static Item heart;
	public static Item potionreverse;
	public static Item goldenbread;
	public static Item dietpills;
	public static Item waffle;
	public static Item endersnooker;
	public static Item handmirror;
	
	public static Item edibleDiamond;
	public static Item edibleLapis;
	public static Item edibleEmerald;
	public static Item edibleIngotIron;
	public static Item edibleIngotGold;
	public static Item edibleNuggetGold;
	
	public static ItemArmor glasses3D;
	public static ItemArmor pixelglasses;
	public static ItemArmor poncho;
	public static ItemArmor slippers;
	public static ItemTool precisionPick;
	public static ItemTool precisionAxe;
	public static ItemTool precisionShovel;
	public static ItemSword precisionSword;
	
	public static final ItemArmor.ArmorMaterial glassesmaterial = EnumHelper.addArmorMaterial("3dglasses", "uniquecrops:3dglasses", 200, new int[] { 0, 0, 0, 0 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0F);
	public static final ItemArmor.ArmorMaterial pixelmaterial = EnumHelper.addArmorMaterial("pixelglasses", "uniquecrops:pixelglasses", 200, new int[] { 0, 0, 0, 0 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0F);
	public static final ItemArmor.ArmorMaterial ponchomaterial = EnumHelper.addArmorMaterial("poncho", "uniquecrops:poncho", 112, new int[] { 1, 2, 1, 1 }, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0F);
	public static final ItemArmor.ArmorMaterial slippermaterial = EnumHelper.addArmorMaterial("slippers", "uniquecrops:slippers", 90, new int[] { 1, 1, 1, 1 }, 0, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0F);
	
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
		
		generic = new ItemGeneric();
		largeplum = new ItemGenericFood(1, 0.6F, true, "largeplum");
		teriyaki = new ItemGenericFood(16, 1.0F, false, "teriyaki").setContainerItem(Items.BOWL);
		heart = new ItemGenericFood(0, 0F, true, "heart");
		potionreverse = new ItemGenericFood(0, 0F, true, "reversepotion");
		goldenbread = new ItemGenericFood(4, 0.3F, false, "goldenbread");
		dietpills = new ItemGenericFood(-4, 0, true, "dietpills");
		waffle = new ItemGenericFood(8, 1.0F, true, "waffle");
		endersnooker = new ItemEnderSnooker();
		handmirror = new ItemHandMirror();
		
		edibleDiamond = new ItemEdibleMetal(EnumEdibleMetal.GEM, "diamond");
		edibleEmerald = new ItemEdibleMetal(EnumEdibleMetal.GEM, "emerald");
		edibleLapis = new ItemEdibleMetal(EnumEdibleMetal.NUGGET, "lapis");
		edibleIngotIron = new ItemEdibleMetal(EnumEdibleMetal.INGOT, "ironingot");
		edibleIngotGold = new ItemEdibleMetal(EnumEdibleMetal.INGOT, "goldingot");
		edibleNuggetGold = new ItemEdibleMetal(EnumEdibleMetal.NUGGET, "goldnugget");
		
		glasses3D = new Item3DGlasses(glassesmaterial, 1, EntityEquipmentSlot.HEAD);
		pixelglasses = new ItemPixelGlasses(pixelmaterial, 1, EntityEquipmentSlot.HEAD);
		poncho = new ItemPoncho(ponchomaterial, 1, EntityEquipmentSlot.CHEST);
		slippers = new ItemGlassSlippers(slippermaterial, 2, EntityEquipmentSlot.FEET);
		precisionPick = new ItemPrecisionPick();
		precisionAxe = new ItemPrecisionAxe();
		precisionShovel = new ItemPrecisionShovel();
		precisionSword = new ItemPrecisionSword();
	}
}
