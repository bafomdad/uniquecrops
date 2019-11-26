package com.bafomdad.uniquecrops.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.items.ItemGoodieBag;
import com.google.gson.reflect.TypeToken;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.RangeInt;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@LangKey(UniqueCrops.MOD_ID + ".general")
@Config(modid=UniqueCrops.MOD_ID)
public class UCConfig {

	@Comment({"Drop weight for the mod's starting seed. The higher the number, the more often it'll drop."})
	public static int dropRate = 5;
	
	@Comment({"Time (in minutes) it takes for Millennium crop to advance a stage."})
	public static int millenniumTime = 10;
	
	@Comment({"Lets the Petramia crop convert obsidian instead of bedrock. Use if there are no bedrock nearby to convert."})
	public static boolean convertObsidian = false;

	@Comment({"Skeleton spawners placed in the nether has a chance of spawning wither skeletons instead."})
	public static boolean witherSkeletonSpawner = false;
	
	@Comment({"Adjust placement of Wildwood staff GUI on the x axis."})
	public static int guiWidth = -191;
	
	@Comment({"Adjust placement of Wildwood staff GUI on the y axis."})
	public static int guiHeight = -50;
	
	@Comment({"Cooldown time (in ticks) for rubik's cube between successful teleports."})
	public static int cubeCooldown = 3600;
	
	@Comment({"Weight chance for underground ruins to generate in overworld. The lower the number, the higher the chance."})
	@RangeInt(min=6, max=600)
	public static int worldGenerationRuinsWeight = 40;
	
	@LangKey(UniqueCrops.MOD_ID + ".crops")
	@Config(modid=UniqueCrops.MOD_ID, category="crops")
	public static class CropConfig {
		
		@Comment({"Can plant Normie seeds"})
		public static boolean cropNormal = true;
		
		@Comment({"Can plant Precision seeds"})
		public static boolean cropPrecision = true;
		
		@Comment({"Can plant Dirigible plums"})
		public static boolean cropDirigible = true;
		
		@Comment({"Can plant Weeping Bells"})
		public static boolean cropWeepingbells = true;
		
		@Comment({"Can plant Knowledge seeds"})
		public static boolean cropKnowledge = true;
		
		@Comment({"Can plant Ender lily seeds"})
		public static boolean cropEnderlily = true;
		
		@Comment({"Can plant Millennium seeds"})
		public static boolean cropMillennium = true;
		
		@Comment({"Can plant Merlinia seeds"})
		public static boolean cropMerlinia = true;
		
		@Comment({"Can plant Invisibilia seeds"})
		public static boolean cropInvisibilia = true;
		
		@Comment({"Can plant Musica seeds"})
		public static boolean cropMusica = true;
		
		@Comment({"Can plant Feroxia seeds"})
		public static boolean cropFeroxia = true;
		
		@Comment({"Can plant Cinderbella shoots"})
		public static boolean cropCinderbella = true;
		
		@Comment({"Can plant Goldenrod seeds"})
		public static boolean cropCollis = true;
		
		@Comment({"Can plant Mary-jane seeds"})
		public static boolean cropMaryjane = true;
		
		@Comment({"Can plant EULA seeds"})
		public static boolean cropEula = true;
		
		@Comment({"Can plant Dyeius seeds"})
		public static boolean cropDyeius = true;
		
		@Comment({"Can plant Cobblonia seeds"})
		public static boolean cropCobblonia = true;
		
		@Comment({"Can plant Abstract seeds"})
		public static boolean cropAbstract = true;
		
		@Comment({"Can plant Wafflonia seeds"})
		public static boolean cropWafflonia = true;
		
		@Comment({"Can plant Devil's Snares"})
		public static boolean cropDevilsnare = true;
		
		@Comment({"Can plant Pixelsius seeds"})
		public static boolean cropPixelsius = true;
		
		@Comment({"Can plant Artisia seeds"})
		public static boolean cropArtisia = true;
		
		@Comment({"Can plant Malleatoris seeds"})
		public static boolean cropMalleatoris = true;
		
		@Comment({"Can plant Petramia seeds"})
		public static boolean cropPetramia = true;
		
		@Comment({"Can plant Imperia seeds"})
		public static boolean cropImperia = true;
		
		@Comment({"Can plant Lacusia seeds"})
		public static boolean cropLacusia = true;
		
		@Comment({"Can plant Hexis seeds"})
		public static boolean cropHexis = true;
		
		@Comment({"Can plant Industria seeds"})
		public static boolean cropIndustria = true;
		
		@Comment({"Can plant Quarry seeds"})
		public static boolean cropQuarry = true;
		
		@Comment({"Can plant Donut seeds"})
		public static boolean cropDonutSteel = true;
		
		@Comment({"Can plant Instabilis seeds"})
		public static boolean cropInstabilis = true;
		
		@Comment({"Can plant Succo seeds"})
		public static boolean cropVampire = true;
		
		@Comment({"Can plant Holiday Jellybeans"})
		public static boolean cropAdventus;
		
		@Comment({"Can plant Blessed Seeds"})
		public static boolean cropHoly = true;
	}
	
	@LangKey(UniqueCrops.MOD_ID + ".conditions")
	@Config(modid=UniqueCrops.MOD_ID, category="conditions")
	public static class GrowthConfig {
		
		public static boolean moonPhase = true;
		public static boolean hasTorch = true;
		public static boolean likesDarkness = true;
		public static boolean dryFarmland = true;
		public static boolean underFarmland = true;
		public static boolean burningPlayer = true;
		public static boolean hellWorld = true;
		public static boolean likesHeights = true;
		public static boolean likesLilypads = true;
		public static boolean thirstyPlant = true;
		public static boolean hungryPlant = true;
		public static boolean likesChicken = true;
		public static boolean likesRedstone = true;
		public static boolean vampirePlant = true;
		public static boolean fullBrightness = true;
		public static boolean likesWarts = true;
		public static boolean likesCooking = true;
		public static boolean likesBrewing = true;
		public static boolean likesCheckers = true;
		public static boolean dontBonemeal = true;
		public static boolean selfSacrifice = true;
	}
	
	// ------------------------------------------------------------------------- //
	private static File multiblockDirectory;
	private static List<MultiblockPattern> multiblockPatterns = new ArrayList();
	
	public static void init(FMLPreInitializationEvent event) {
		
		multiblockDirectory = new File(event.getModConfigurationDirectory().getParentFile(), File.separator + UniqueCrops.MOD_ID);
	}
	
	public static void syncMultiblocks() {
		
		multiblockPatterns.clear();
		if (getDirectoryListing() != null) {
			for (File file : getDirectoryListing()) {
				MultiblockPattern pattern = JsonUtils.fromJson(TypeToken.get(MultiblockPattern.class), file);
				if (pattern != null) {
					multiblockPatterns.add(pattern);
				}
			}
		}
//		if (multiblockPatterns.isEmpty())
//			multiblockPatterns.add(MultiblockPattern.DEFAULT);
	}
	
	public static List<MultiblockPattern> getPatterns() {
		
		return multiblockPatterns;
	}
	
	private static List<File> getDirectoryListing() {
		
		if (multiblockDirectory == null) return null;
		if (!multiblockDirectory.exists()) {
			fillDirectory();
		}
		List<File> files = new ArrayList<File>(Arrays.asList(multiblockDirectory.listFiles()));
		if (!files.isEmpty()) return files;
		
		return null;
	}
	
	private static void fillDirectory() {
		
		try {
			for (String fileName : UCStrings.DEFAULT_MULTIBLOCKS) {
				InputStream fis = UCConfig.class.getClassLoader().getResourceAsStream("assets/uniquecrops/multiblocks/" + fileName);
				FileUtils.copyToFile(fis, new File(multiblockDirectory, fileName));
				fis.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
