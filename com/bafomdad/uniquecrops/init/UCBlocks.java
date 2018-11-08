package com.bafomdad.uniquecrops.init;

import java.util.ArrayList;
import java.util.List;

import com.bafomdad.uniquecrops.blocks.*;
import com.bafomdad.uniquecrops.blocks.tiles.TileArtisia;
import com.bafomdad.uniquecrops.blocks.tiles.TileLacusia;
import com.bafomdad.uniquecrops.blocks.tiles.TileMirror;
import com.bafomdad.uniquecrops.blocks.tiles.TileMusicaPlant;
import com.bafomdad.uniquecrops.blocks.tiles.TileSucco;
import com.bafomdad.uniquecrops.blocks.tiles.TileSundial;
import com.bafomdad.uniquecrops.core.CropBuilder;
import com.bafomdad.uniquecrops.core.EnumCrops;
import com.bafomdad.uniquecrops.crops.*;
import com.bafomdad.uniquecrops.render.RenderCraftItem;
import com.bafomdad.uniquecrops.render.RenderMirror;
import com.bafomdad.uniquecrops.render.RenderSucco;
import com.bafomdad.uniquecrops.render.RenderSundial;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class UCBlocks {
	
	public static List<Block> blocks = new ArrayList();

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
		cropMalleatoris,
		cropImperia,
		cropLacusia,
		cropHexis,
		cropIndustria,
		cropQuarry,
		cropDonutSteel,
		cropInstabilis,
		cropSucco;
	
	public static Block oldCobble, oldCobbleMoss, oldGravel, oldGrass, oldBrick;
	public static Block hourglass, totemhead, lavalily, icelily, darkBlock, sundial, barrel, invisiGlass, 
						demoCord, mirror, goblet;
	public static Block stalk, centerstalk, topstalk;
	
	public static void init() {
		
		cropNormal = CropBuilder.create(new Normal()).build();
		cropArtisia = CropBuilder.create(new Artisia()).setExtraDrops(false).build();
		cropPrecision = CropBuilder.create(new Precision()).setExtraDrops(false).setBonemealable(false).setClickHarvest(false).build();
		cropKnowledge = CropBuilder.create(new Knowledge()).setBonemealable(false).build();
		cropDirigible = CropBuilder.create(new Dirigible()).setExtraDrops(false).setBonemealable(false).build();
		cropMillennium = CropBuilder.create(new Millennium()).setBonemealable(false).build();
		cropEnderlily = CropBuilder.create(new Enderlily()).setExtraDrops(false).build();
		cropCollis = CropBuilder.create(new Collis()).build();
		cropInvisibilia = CropBuilder.create(new Invisibilia()).setClickHarvest(false).build();
		cropMaryjane = CropBuilder.create(new MaryJane()).setClickHarvest(false).build();
		cropWeepingbells = CropBuilder.create(new WeepingBells()).setExtraDrops(false).setBonemealable(false).build();
		cropMusica = CropBuilder.create(new Musica()).setExtraDrops(false).setBonemealable(false).build();
		cropCinderbella = CropBuilder.create(new Cinderbella()).build();
		cropMerlinia = CropBuilder.create(new Merlinia()).build();
		cropEula = CropBuilder.create(new Eula()).setExtraDrops(false).build();
		cropCobblonia = CropBuilder.create(new Cobblonia()).setExtraDrops(false).setClickHarvest(false).build();
		cropDyeius = CropBuilder.create(new Dyeius()).build();
		cropAbstract = CropBuilder.create(new Abstract()).setExtraDrops(false).build();
		cropWafflonia = CropBuilder.create(new Wafflonia()).build();
		cropDevilsnare = CropBuilder.create(new DevilSnare()).setExtraDrops(false).setClickHarvest(false).build();
		cropPixelsius = CropBuilder.create(new Pixelsius()).setExtraDrops(false).build();
		cropPetramia = CropBuilder.create(new Petramia()).setExtraDrops(false).build();
		cropMalleatoris = CropBuilder.create(new Malleatoris()).setExtraDrops(false).setClickHarvest(false).setBonemealable(false).build();
		cropImperia = CropBuilder.create(new Imperia()).setExtraDrops(false).setClickHarvest(false).setBonemealable(false).build();
		cropLacusia = CropBuilder.create(new Lacusia()).build();
		cropHexis = CropBuilder.create(new Hexis()).setClickHarvest(false).setBonemealable(false).build();
		cropIndustria = CropBuilder.create(new Industria()).setClickHarvest(false).setBonemealable(false).build();
		cropQuarry = CropBuilder.create(new Fossura()).setExtraDrops(false).setClickHarvest(false).setBonemealable(false).build();
		cropDonutSteel = CropBuilder.create(new DonutSteel()).setExtraDrops(false).setBonemealable(false).build();
		cropInstabilis = CropBuilder.create(new Instabilis()).setExtraDrops(true).setClickHarvest(false).build();
		cropSucco = CropBuilder.create(new Succo()).setExtraDrops(false).setBonemealable(false).setClickHarvest(false).build();
		cropFeroxia = CropBuilder.create(new Feroxia()).setClickHarvest(false).setExtraDrops(false).setBonemealable(false).build();
		
		oldCobble = new BlockOldStone("cobble");
		oldCobbleMoss = new BlockOldStone("cobblemoss");
		oldBrick = new BlockOldStone("brick");
		oldGravel = new BlockOldGravel();
		oldGrass = new BlockOldGrass();
		hourglass = new BlockHourglass();
		totemhead = new BlockTotemhead();
		lavalily = new BlockLavaLily();
		icelily = new BlockIceLily();
		darkBlock = new BlockDarkBlock();
		sundial = new BlockSundial();
		barrel = new BlockBarrel();
		invisiGlass = new BlockPlantGlass();
		demoCord = new BlockDemoCord();
		mirror = new BlockMirror();
		goblet = new BlockGoblet();
		
		stalk = new BlockStalk();
		centerstalk = new BlockCenterStalk();
		topstalk = new BlockCraftStalk();
	}
	
	@SideOnly(Side.CLIENT)
	public static void initModels() {
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileArtisia.class, new RenderCraftItem.Artisia());
		ClientRegistry.bindTileEntitySpecialRenderer(TileLacusia.class, new RenderCraftItem.Lacusia());
		ClientRegistry.bindTileEntitySpecialRenderer(TileSundial.class, new RenderSundial());
		ClientRegistry.bindTileEntitySpecialRenderer(TileMirror.class, new RenderMirror());
		ClientRegistry.bindTileEntitySpecialRenderer(TileSucco.class, new RenderSucco());
	}
}
