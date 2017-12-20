package com.bafomdad.uniquecrops.init;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.blocks.BlockIceLily;
import com.bafomdad.uniquecrops.blocks.BlockLavaLily;
import com.bafomdad.uniquecrops.blocks.tiles.TileArtisia;
import com.bafomdad.uniquecrops.core.EnumItems;
import com.bafomdad.uniquecrops.crafting.DiscountBookRecipe;
import com.bafomdad.uniquecrops.entities.RenderCraftItem;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

public class UCEventRegistry {
	
	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) {
	
		UCBlocks.init();
	
		for (Block block : UCBlocks.blocks)
			event.getRegistry().register(block);
	}

	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		
		UCItems.init();
		
		for (Item item : UCItems.items)
			event.getRegistry().register(item);

		// ITEMBLOCKS
		event.getRegistry().register(new BlockLavaLily.ItemLavaLily(UCBlocks.lavalily).setRegistryName(UCBlocks.lavalily.getRegistryName()));
		event.getRegistry().register(new BlockIceLily.ItemIceLily(UCBlocks.icelily).setRegistryName(UCBlocks.icelily.getRegistryName()));
		event.getRegistry().register(new ItemBlock(UCBlocks.hourglass).setRegistryName(UCBlocks.hourglass.getRegistryName()));
		event.getRegistry().register(new ItemBlock(UCBlocks.oldBrick).setRegistryName(UCBlocks.oldBrick.getRegistryName()));
		event.getRegistry().register(new ItemBlock(UCBlocks.oldCobble).setRegistryName(UCBlocks.oldCobble.getRegistryName()));
		event.getRegistry().register(new ItemBlock(UCBlocks.oldCobbleMoss).setRegistryName(UCBlocks.oldCobbleMoss.getRegistryName()));
		event.getRegistry().register(new ItemBlock(UCBlocks.oldGrass).setRegistryName(UCBlocks.oldGrass.getRegistryName()));
		event.getRegistry().register(new ItemBlock(UCBlocks.oldGravel).setRegistryName(UCBlocks.oldGravel.getRegistryName()));
		event.getRegistry().register(new ItemBlock(UCBlocks.totemhead).setRegistryName(UCBlocks.totemhead.getRegistryName()));
		event.getRegistry().register(new ItemBlock(UCBlocks.darkBlock).setRegistryName(UCBlocks.darkBlock.getRegistryName()));
		event.getRegistry().register(rib(UCBlocks.barrel));
	}
	
	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		
		event.getRegistry().register(new DiscountBookRecipe("discountbook"));
		UCRecipes.init();
		
		OreDictionary.registerOre("gemDiamond", UCItems.edibleDiamond);
		OreDictionary.registerOre("gemEmerald", UCItems.edibleEmerald);
		OreDictionary.registerOre("gemLapis", UCItems.edibleLapis);
		OreDictionary.registerOre("ingotIron", UCItems.edibleIngotIron);
		OreDictionary.registerOre("ingotGold", UCItems.edibleIngotGold);
		OreDictionary.registerOre("nuggetGold", UCItems.edibleNuggetGold);
		
		OreDictionary.registerOre("dyeBlue", new ItemStack(UCItems.generic, 1, EnumItems.BLUEDYE.ordinal()));
	}
	
	public Item rib(Block block) {
		
		return new ItemBlock(block).setRegistryName(block.getRegistryName());
	}
}
