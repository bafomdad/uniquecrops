package com.bafomdad.uniquecrops.core;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class UCConfig {

	public static int dropRate;
	
	// CROPS
	public static boolean cropnormal;
	public static boolean cropprecision;
	public static boolean cropdirigible;
	public static boolean cropweepingbells;
	public static boolean cropknowledge;
	public static boolean cropenderlily;
	public static boolean cropmillennium;
	public static boolean cropmerlinia;
	public static boolean cropinvisibilia;
	public static boolean cropmusica;
	public static boolean cropferoxia;
	public static boolean cropcinderbella;
	public static boolean cropcollis;
	public static boolean cropmaryjane;
	public static boolean cropEula;
	public static boolean cropDyeius;
	public static boolean cropCobblonia;
	public static boolean cropAbstract;
	public static boolean cropWafflonia;
	public static boolean cropDevilsnare;
	public static boolean cropPixelsius;
	public static boolean cropArtisia;
	
	public static void loadConfig(FMLPreInitializationEvent event) {
		
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		dropRate = config.get("misc", "startingSeedDropRate", 5, "Drop weight for the mod's starting seed. The higher the number, the more often it'll drop.").getInt();
		
		cropnormal = config.get("plants", "canPlantNormieSeeds", true).getBoolean();
		cropprecision = config.get("plants", "canPlantPrecisionSeeds", true).getBoolean();
		cropdirigible = config.get("plants", "canPlantDirigibleSeeds", true).getBoolean();
		cropweepingbells = config.get("plants", "canPlantWeepingbellSeeds", true).getBoolean();
		cropknowledge = config.get("plants", "canPlantKnowledgeSeeds", true).getBoolean();
		cropenderlily = config.get("plants", "canPlantEnderlilySeeds", true).getBoolean();
		cropmillennium = config.get("plants", "canPlantMillenniumSeeds", true).getBoolean();
		cropmerlinia = config.get("plants", "canPlantMerliniaSeeds", true).getBoolean();
		cropinvisibilia = config.get("plants", "canPlantInvisibiliaSeeds", true).getBoolean();
		cropmusica = config.get("plants", "canPlantMusicaSeeds", true).getBoolean();
		cropferoxia = config.get("plants", "canPlantFeroxiaSeeds", true).getBoolean();
		cropcinderbella = config.get("plants", "canPlantCinderbellaSeeds", true).getBoolean();
		cropcollis = config.get("plants", "canPlantCollisSeeds", true).getBoolean();
		cropmaryjane = config.get("plants", "canPlantMaryjaneSeeds", true).getBoolean();
		cropEula = config.get("plants", "canPlantEulaSeeds", true).getBoolean();
		cropDyeius = config.get("plants", "canPlantDyeiusSeeds", true).getBoolean();
		cropCobblonia = config.get("plants", "canPlantCobbloniaSeeds", true).getBoolean();
		cropAbstract = config.get("plants", "canPlantAbstractSeeds", true).getBoolean();
		cropWafflonia = config.get("plants", "canPlantCliqiaSeeds", true).getBoolean();
		cropDevilsnare = config.get("plants", "canPlantDevilSnare", true).getBoolean();
		cropPixelsius = config.get("plants", "canPlantPixelsius", true).getBoolean();
		cropArtisia = config.get("plants", "canPlantArtisia", true).getBoolean();
		
		config.save();
	}
}
