package com.bafomdad.uniquecrops.init;

import java.util.ArrayList;
import java.util.List;

import com.bafomdad.uniquecrops.blocks.*;
import com.bafomdad.uniquecrops.blocks.tiles.*;
import com.bafomdad.uniquecrops.core.CropBuilder;
import com.bafomdad.uniquecrops.core.enums.EnumCrops;
import com.bafomdad.uniquecrops.crops.*;
import com.bafomdad.uniquecrops.crops.supercrops.*;
import com.bafomdad.uniquecrops.items.ItemGoodieBag;
import com.bafomdad.uniquecrops.render.entity.*;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
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
		cropSucco,
		cropAdventus,
		cropHoly;
	
	// SUPERCROPS
	public static Block 
		exedo, 
		cocito, 
		itero,
		fascino,
		weatherflesia;
	
	// OTHERS
	public static Block 
		oldCobble, 
		oldCobbleMoss, 
		oldGravel, 
		oldGrass, 
		oldBrick,
		hourglass, 
		totemHead, 
		lavaLily, 
		iceLily, 
		teleLily, 
		jungleLily, 
		darkBlock, 
		sundial,
		barrel,
		invisiGlass, 		
		demoCord, 
		mirror, 
		goblet, 
		sunBlock, 
		portal, 
		harvestTrap, 
		driedThatch, 
		normieCrate, 
		ruinedBricks, 
		ruinedBricksCarved, 
		ruinedBricksRed, 
		ruinedBricksGhost,
		stalk, 
		bucketRope,
		flywoodLog,
		flywoodLeaves,
		flywoodSapling,
		flywoodPlank;
	
	public static void init() {
		
		cropNormal = CropBuilder.create(new Normal()).build();
		cropArtisia = CropBuilder.create(new Artisia()).setExtraDrops(false).build();
		cropPrecision = CropBuilder.create(new Precision()).setExtraDrops(false).setBonemealable(false).setClickHarvest(false).build();
		cropKnowledge = CropBuilder.create(new Knowledge()).setBonemealable(false).setIgnoreGrowth(true).build();
		cropDirigible = CropBuilder.create(new Dirigible()).setExtraDrops(false).setBonemealable(false).build();
		cropMillennium = CropBuilder.create(new Millennium()).setBonemealable(false).build();
		cropEnderlily = CropBuilder.create(new Enderlily()).setExtraDrops(false).build();
		cropCollis = CropBuilder.create(new Collis()).setIgnoreGrowth(true).build();
		cropInvisibilia = CropBuilder.create(new Invisibilia()).setClickHarvest(false).build();
		cropMaryjane = CropBuilder.create(new MaryJane()).setClickHarvest(false).setIgnoreGrowth(true).build();
		cropWeepingbells = CropBuilder.create(new WeepingBells()).setExtraDrops(false).setBonemealable(false).build();
		cropMusica = CropBuilder.create(new Musica()).setExtraDrops(false).setBonemealable(false).setIgnoreGrowth(true).build();
		cropCinderbella = CropBuilder.create(new Cinderbella()).setBonemealable(false).setIgnoreGrowth(true).build();
		cropMerlinia = CropBuilder.create(new Merlinia()).build();
		cropEula = CropBuilder.create(new Eula()).setExtraDrops(false).build();
		cropCobblonia = CropBuilder.create(new Cobblonia()).setExtraDrops(false).setClickHarvest(false).build();
		cropDyeius = CropBuilder.create(new Dyeius()).build();
		cropAbstract = CropBuilder.create(new Abstract()).setExtraDrops(false).build();
		cropWafflonia = CropBuilder.create(new Wafflonia()).build();
		cropDevilsnare = CropBuilder.create(new DevilSnare()).setExtraDrops(false).setClickHarvest(false).build();
		cropPixelsius = CropBuilder.create(new Pixelsius()).setExtraDrops(false).build();
		cropPetramia = CropBuilder.create(new Petramia()).setExtraDrops(false).setIgnoreGrowth(true).build();
		cropMalleatoris = CropBuilder.create(new Malleatoris()).setExtraDrops(false).setClickHarvest(false).setBonemealable(false).build();
		cropImperia = CropBuilder.create(new Imperia()).setExtraDrops(false).setClickHarvest(false).setBonemealable(false).build();
		cropLacusia = CropBuilder.create(new Lacusia()).build();
		cropHexis = CropBuilder.create(new Hexis()).setClickHarvest(false).setBonemealable(false).build();
		cropIndustria = CropBuilder.create(new Industria()).setClickHarvest(false).setBonemealable(false).build();
		cropQuarry = CropBuilder.create(new Fossura()).setExtraDrops(false).setClickHarvest(false).setBonemealable(false).build();
		cropDonutSteel = CropBuilder.create(new DonutSteel()).setExtraDrops(false).setClickHarvest(false).setBonemealable(false).build();
		cropInstabilis = CropBuilder.create(new Instabilis()).setExtraDrops(true).setClickHarvest(false).build();
		cropSucco = CropBuilder.create(new Succo()).setExtraDrops(false).setBonemealable(false).setClickHarvest(false).build();
		cropAdventus = CropBuilder.create(new Adventus()).setExtraDrops(false).build();
		cropHoly = CropBuilder.create(new HolyCrop()).setExtraDrops(false).setClickHarvest(false).build();
		cropFeroxia = CropBuilder.create(new Feroxia()).setClickHarvest(false).setExtraDrops(false).setBonemealable(true).setIgnoreGrowth(true).build();
		
		exedo = new Exedo();
		cocito = new Cocito();
		itero = new Itero();
		fascino = new Fascino();
		weatherflesia = new Weatherflesia();
		
		oldCobble = new BlockOldStone("cobble");
		oldCobbleMoss = new BlockOldStone("cobblemoss");
		oldBrick = new BlockOldStone("brick");
		oldGravel = new BlockOldGravel();
		oldGrass = new BlockOldGrass();
		hourglass = new BlockHourglass();
		totemHead = new BlockTotemhead();
		lavaLily = new BlockLavaLily();
		iceLily = new BlockIceLily();
		teleLily = new BlockTeleLily();
		jungleLily = new BlockJungleLily();
		darkBlock = new BlockDarkBlock();
		sundial = new BlockSundial();
		barrel = new BlockBarrel();
		invisiGlass = new BlockPlantGlass();
		demoCord = new BlockDemoCord();
		mirror = new BlockMirror();
		goblet = new BlockGoblet();
		sunBlock = new BlockSun();
		portal = new BlockCropPortal();
		harvestTrap = new BlockHarvestTrap();
		normieCrate = new BlockBaseUC("normiecrate", Material.WOOD, SoundType.WOOD).setHardness(0.25F).setResistance(5.0F);
		driedThatch = new BlockBaseUC("driedthatch", Material.GRASS, SoundType.PLANT).setHardness(0.1F);
		ruinedBricks = new BlockBaseUC("ruinedbricks", Material.ROCK, SoundType.STONE).setHardness(1.15F).setResistance(30.0F);
		ruinedBricksCarved = new BlockBaseUC("ruinedbrickscarved", Material.ROCK, SoundType.STONE).setHardness(1.15F).setResistance(30.0F);
		ruinedBricksRed = new BlockRuinedBricksRed();
		ruinedBricksGhost = new BlockRuinedBricksGhost();
		stalk = new BlockStalk();
		bucketRope = new BlockBucketRope();
		flywoodLog = new BlockFlywoodLog();
		flywoodLeaves = new BlockFlywoodLeaves();
		flywoodSapling = new BlockFlywoodSapling();
		flywoodPlank = new BlockBaseUC("flywood_plank", Material.WOOD, SoundType.WOOD).setHardness(2.0F).setResistance(5.0F);
	}
	
	@SideOnly(Side.CLIENT)
	public static void initModels() {
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileArtisia.class, new RenderCraftItem.Artisia());
		ClientRegistry.bindTileEntitySpecialRenderer(TileLacusia.class, new RenderCraftItem.Lacusia());
		ClientRegistry.bindTileEntitySpecialRenderer(TileWeatherflesia.class, new RenderCraftItem.Weatherflesia());
		ClientRegistry.bindTileEntitySpecialRenderer(TileSundial.class, new RenderSundial());
		ClientRegistry.bindTileEntitySpecialRenderer(TileMirror.class, new RenderMirror());
		ClientRegistry.bindTileEntitySpecialRenderer(TileSucco.class, new RenderSucco());
		ClientRegistry.bindTileEntitySpecialRenderer(TileSunBlock.class, new RenderSunBlock());
		ClientRegistry.bindTileEntitySpecialRenderer(TileExedo.class, new RenderExedo());
		ClientRegistry.bindTileEntitySpecialRenderer(TileFascino.class, new RenderFascino());
	}
}
