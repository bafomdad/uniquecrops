package com.bafomdad.uniquecrops.core;

import com.bafomdad.uniquecrops.UniqueCrops;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Configuration;

@Config(modid=UniqueCrops.MOD_ID)
public class UCConfig {

	@Comment({"Drop weight for the mod's starting seed. The higher the number, the more often it'll drop."})
	public static int dropRate = 5;
	
	// CROPS
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
}
