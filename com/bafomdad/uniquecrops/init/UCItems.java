package com.bafomdad.uniquecrops.init;

import com.bafomdad.uniquecrops.core.EnumItems;
import com.bafomdad.uniquecrops.items.*;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemTool;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class UCItems {

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
		seedsArtisia;
	
	public static ItemGeneric generic;
	public static Item largeplum;
	public static Item teriyaki;
	public static Item heart;
	public static Item potionreverse;
	public static Item goldenbread;
	public static Item dietpills;
	public static Item waffle;
	public static Item endersnooker;
	
	public static ItemArmor glasses3D;
	public static ItemArmor poncho;
	public static ItemArmor slippers;
	public static ItemArmor pixelglasses;
	public static ItemTool precisionPick;
	public static ItemTool precisionAxe;
	public static ItemTool precisionShovel;
	
	public static final ItemArmor.ArmorMaterial glassesmaterial = EnumHelper.addArmorMaterial("3dglasses", "uniquecrops:3dglasses", 200, new int[] { 0, 0, 0, 0 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0F);
	public static final ItemArmor.ArmorMaterial pixelmaterial = EnumHelper.addArmorMaterial("pixelglasses", "uniquecrops:pixelglasses", 200, new int[] { 0, 0, 0, 0 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0F);
	public static final ItemArmor.ArmorMaterial ponchomaterial = EnumHelper.addArmorMaterial("poncho", "uniquecrops:poncho", 112, new int[] { 1, 2, 1, 1 }, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0F);
	public static final ItemArmor.ArmorMaterial slippermaterial = EnumHelper.addArmorMaterial("slippers", "uniquecrops:slippers", 90, new int[] { 1, 1, 1, 1 }, 0, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0F);
	
	public static void init() {
		
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
		
		generic = new ItemGeneric();
		largeplum = new ItemGenericFood(1, 0.6F, true, "largeplum");
		teriyaki = new ItemGenericFood(16, 1.0F, false, "teriyaki").setContainerItem(Items.BOWL);
		heart = new ItemGenericFood(0, 0F, true, "heart");
		potionreverse = new ItemGenericFood(0, 0F, true, "reversepotion");
		goldenbread = new ItemGenericFood(4, 0.3F, false, "goldenbread");
		dietpills = new ItemGenericFood(-4, 0, true, "dietpills");
		waffle = new ItemGenericFood(8, 1.0F, true, "waffle");
		endersnooker = new ItemEnderSnooker();
		
		glasses3D = new Item3DGlasses(glassesmaterial, 1, EntityEquipmentSlot.HEAD);
		pixelglasses = new ItemPixelGlasses(pixelmaterial, 1, EntityEquipmentSlot.HEAD);
		poncho = new ItemPoncho(ponchomaterial, 1, EntityEquipmentSlot.CHEST);
		slippers = new ItemGlassSlippers(slippermaterial, 2, EntityEquipmentSlot.FEET);
		precisionPick = new ItemPrecisionPick();
		precisionAxe = new ItemPrecisionAxe();
		precisionShovel = new ItemPrecisionShovel();
	}
	
	@SideOnly(Side.CLIENT)
	public static void initModels() {
		
		registerItemModel(seedsNormal);
		registerItemModel(seedsPrecision);
		registerItemModel(seedsKnowledge);
		registerItemModel(seedsDirigible);
		registerItemModel(seedsMillennium);
		registerItemModel(seedsEnderlily);
		registerItemModel(seedsCollis);
		registerItemModel(seedsInvisibilia);
		registerItemModel(seedsMaryjane);
		registerItemModel(seedsWeepingbells);
		registerItemModel(seedsMusica);
		registerItemModel(seedsCinderbella);
		registerItemModel(seedsMerlinia);
		registerItemModel(seedsFeroxia);
		registerItemModel(seedsEula);
		registerItemModel(seedsCobblonia);
		registerItemModel(seedsDyeius);
		registerItemModel(seedsAbstract);
		registerItemModel(seedsWafflonia);
		registerItemModel(seedsDevilsnare);
		registerItemModel(seedsPixelsius);
		registerItemModel(seedsArtisia);
		
		for (int i = 0; i < EnumItems.values().length; ++i)
			ModelLoader.setCustomModelResourceLocation(generic, i, new ModelResourceLocation(generic.getRegistryName() + "." + EnumItems.values()[i].getName(), "inventory"));
			
		registerItemModel(largeplum);
		registerItemModel(teriyaki);
		registerItemModel(heart);
		registerItemModel(potionreverse);
		registerItemModel(goldenbread);
		registerItemModel(dietpills);
		registerItemModel(waffle);
		registerItemModel(endersnooker);
		registerItemModel(glasses3D);
		registerItemModel(poncho);
		registerItemModel(slippers);
		registerItemModel(pixelglasses);
		registerItemModel(precisionPick);
		registerItemModel(precisionAxe);
		registerItemModel(precisionShovel);
	}
	
	private static void registerItemModel(Item item) {
		
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
