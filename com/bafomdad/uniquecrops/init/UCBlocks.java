package com.bafomdad.uniquecrops.init;

import com.bafomdad.uniquecrops.blocks.*;
import com.bafomdad.uniquecrops.blocks.tiles.TileArtisia;
import com.bafomdad.uniquecrops.core.EnumCrops;
import com.bafomdad.uniquecrops.crops.*;
import com.bafomdad.uniquecrops.entities.RenderCraftItem;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class UCBlocks {

	// CROPS
	public static BlockCropsBase
		cropNormal,
		cropPrecision,
		cropKnowledge,
		cropDirigible,
		cropMillennium,
		cropEnderlily,
		cropCollis,
		cropWeepingbells,
		cropInvisibilia,
		cropMaryjane,
		cropMusica,
		cropCinderbella,
		cropMerlinia,
		cropFeroxia,
		cropEula,
		cropCobblonia,
		cropDyeius,
		cropAbstract,
		cropWafflonia,
		cropDevilsnare,
		cropPixelsius,
		cropArtisia,
		cropPetramia,
		cropMalleatoris;
	
	public static Block oldCobble, oldCobbleMoss, oldGravel, oldGrass, oldBrick;
	public static Block hourglass, totemhead, lavalily, darkBlock;
	
	public static void init() {
		
		cropNormal = new Normal();
		cropPrecision = new Precision();
		cropKnowledge = new Knowledge();
		cropDirigible = new Dirigible();
		cropMillennium = new Millennium();
		cropEnderlily = new Enderlily();
		cropCollis = new Collis();
		cropInvisibilia = new Invisibilia();
		cropMaryjane = new MaryJane();
		cropWeepingbells = new WeepingBells();
		cropMusica = new Musica();
		cropCinderbella = new Cinderbella();
		cropMerlinia = new Merlinia();
		cropFeroxia = new Feroxia();
		cropEula = new Eula();
		cropCobblonia = new Cobblonia();
		cropDyeius = new Dyeius();
		cropAbstract = new Abstract();
		cropWafflonia = new Wafflonia();
		cropDevilsnare = new DevilSnare();
		cropPixelsius = new Pixelsius();
		cropArtisia = new Artisia();
		cropPetramia = new Petramia();
		cropMalleatoris = new Malleatoris();
		
		oldCobble = new BlockOldStone("cobble");
		oldCobbleMoss = new BlockOldStone("cobblemoss");
		oldBrick = new BlockOldStone("brick");
		oldGravel = new BlockOldGravel();
		oldGrass = new BlockOldGrass();
		hourglass = new BlockHourglass();
		totemhead = new BlockTotemhead();
		lavalily = new BlockLavaLily();
		darkBlock = new BlockDarkBlock();
	}
	
	@SideOnly(Side.CLIENT)
	public static void initModels() {
	
		registerBlockModel(oldCobble);
		registerBlockModel(oldCobbleMoss);
		registerBlockModel(oldBrick);
		registerBlockModel(oldGravel);
		registerBlockModel(oldGrass);
		registerBlockModel(hourglass);
		registerBlockModel(totemhead);
		registerBlockModel(lavalily);
		registerBlockModel(darkBlock);
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileArtisia.class, new RenderCraftItem());
	}
	
	private static void registerBlockModel(Block block) {
		
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}
}
