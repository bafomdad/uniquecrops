package com.bafomdad.uniquecrops.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.*;
import com.bafomdad.uniquecrops.core.EnumItems;
import com.bafomdad.uniquecrops.crafting.DiscountBookRecipe;
import com.bafomdad.uniquecrops.entities.*;

public class UCEventRegistry {
	
	public static int entityID;
	
	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) {
	
		UCBlocks.init();
	
		for (Block block : UCBlocks.blocks) {
//			if (block instanceof BlockCropsBase && !((BlockCropsBase)block).getClickHarvest())
//				FMLInterModComms.sendMessage("charset", "removeRightClickHarvest", block.getRegistryName());
			event.getRegistry().register(block);
		}
	}

	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		
		UCItems.init();
		IForgeRegistry<Item> reg = event.getRegistry();
		
		for (Item item : UCItems.items)
			event.getRegistry().register(item);

		// ITEMBLOCKS
		reg.register(new BlockLavaLily.ItemLavaLily(UCBlocks.lavalily).setRegistryName(UCBlocks.lavalily.getRegistryName()));
		reg.register(new BlockIceLily.ItemIceLily(UCBlocks.icelily).setRegistryName(UCBlocks.icelily.getRegistryName()));
		reg.register(rib(UCBlocks.hourglass));
		reg.register(rib(UCBlocks.oldBrick));
		reg.register(rib(UCBlocks.oldCobble));
		reg.register(rib(UCBlocks.oldCobbleMoss));
		reg.register(rib(UCBlocks.oldGrass));
		reg.register(rib(UCBlocks.oldGravel));
		reg.register(rib(UCBlocks.totemhead));
		reg.register(rib(UCBlocks.darkBlock));
		reg.register(rib(UCBlocks.barrel));
		reg.register(rib(UCBlocks.invisiGlass));
		reg.register(new BlockDemoCord.ItemDemocord(UCBlocks.demoCord).setRegistryName(UCBlocks.demoCord.getRegistryName()));
		reg.register(rib(UCBlocks.mirror));
		reg.register(rib(UCBlocks.goblet));
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
		OreDictionary.registerOre("ingotSteel", UCItems.steelDonut);
		
		OreDictionary.registerOre("dyeBlue", new ItemStack(UCItems.generic, 1, EnumItems.BLUEDYE.ordinal()));
	}
	
	@SubscribeEvent
	public void registerEntities(RegistryEvent.Register<EntityEntry> event) {
		
		event.getRegistry().register(entityBuilder("reversepotion", EntityCustomPotion.class));
		event.getRegistry().register(entityBuilder("weepingeye", EntityItemWeepingEye.class));
		event.getRegistry().register(entityBuilder("hourglass", EntityItemHourglass.class));
		event.getRegistry().register(entityBuilder("flyingplum", EntityItemPlum.class));
		event.getRegistry().register(entityBuilder("eulabook", EntityEulaBook.class));
		event.getRegistry().register(entityBuilder("legalstuff", EntityLegalStuff.class));
		event.getRegistry().register(entityBuilder("donker", EntityItemDonk.class));
		event.getRegistry().register(entityBuilder("mirror", EntityMirror.class, 64, 20, false));
		event.getRegistry().register(entityBuilder("battlecrop", EntityBattleCrop.class));
	}
	
	@SubscribeEvent
	public void registerSounds(RegistryEvent.Register<SoundEvent> event) {
		
		event.getRegistry().register(UCSounds.OOF);
	}
	
	public Item rib(Block block) {
		
		return new ItemBlock(block).setRegistryName(block.getRegistryName());
	}
	
	public EntityEntry entityBuilder(String entityName, Class entity) {
		
		EntityEntryBuilder builder = EntityEntryBuilder.create();
		builder.name(UniqueCrops.MOD_ID + ":" + entityName);
		builder.id(new ResourceLocation(UniqueCrops.MOD_ID, entityName), entityID++);
		builder.tracker(64, 1, true);
		builder.entity(entity);
		
		return builder.build();
	}
	
	public EntityEntry entityBuilder(String entityName, Class entity, int trackingRange, int updateFreq, boolean sendVelocityUpdates) {
		
		EntityEntryBuilder builder = EntityEntryBuilder.create();
		builder.name(UniqueCrops.MOD_ID + ":" + entityName);
		builder.id(new ResourceLocation(UniqueCrops.MOD_ID, entityName), entityID++);
		builder.tracker(trackingRange, updateFreq, sendVelocityUpdates);
		builder.entity(entity);
		
		return builder.build();
	}
}
