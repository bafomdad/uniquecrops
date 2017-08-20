package com.bafomdad.uniquecrops.core;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class UCConfig {

	public static int dropRate;
	
	// CROPS
	public static boolean cropNormal;
	public static boolean cropPrecision;
	public static boolean cropDirigible;
	public static boolean cropWeepingbells;
	public static boolean cropKnowledge;
	public static boolean cropEnderlily;
	public static boolean cropMillennium;
	public static boolean cropMerlinia;
	public static boolean cropInvisibilia;
	public static boolean cropMusica;
	public static boolean cropFeroxia;
	public static boolean cropCinderbella;
	public static boolean cropCollis;
	public static boolean cropMaryjane;
	public static boolean cropEula;
	public static boolean cropDyeius;
	public static boolean cropCobblonia;
	public static boolean cropAbstract;
	public static boolean cropWafflonia;
	public static boolean cropDevilsnare;
	public static boolean cropPixelsius;
	public static boolean cropArtisia;
	public static boolean cropMalleatoris;
	public static boolean cropPetramia;
	
	public static void loadConfig(FMLPreInitializationEvent event) {
		
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		dropRate = config.get("misc", "startingSeedDropRate", 5, "Drop weight for the mod's starting seed. The higher the number, the more often it'll drop.").getInt();
		
		cropNormal = config.get("plants", "canPlantNormieSeeds", true).getBoolean();
		cropPrecision = config.get("plants", "canPlantPrecisionSeeds", true).getBoolean();
		cropDirigible = config.get("plants", "canPlantDirigibleSeeds", true).getBoolean();
		cropWeepingbells = config.get("plants", "canPlantWeepingbellSeeds", true).getBoolean();
		cropKnowledge = config.get("plants", "canPlantKnowledgeSeeds", true).getBoolean();
		cropEnderlily = config.get("plants", "canPlantEnderlilySeeds", true).getBoolean();
		cropMillennium = config.get("plants", "canPlantMillenniumSeeds", true).getBoolean();
		cropMerlinia = config.get("plants", "canPlantMerliniaSeeds", true).getBoolean();
		cropInvisibilia = config.get("plants", "canPlantInvisibiliaSeeds", true).getBoolean();
		cropMusica = config.get("plants", "canPlantMusicaSeeds", true).getBoolean();
		cropFeroxia = config.get("plants", "canPlantFeroxiaSeeds", true).getBoolean();
		cropCinderbella = config.get("plants", "canPlantCinderbellaSeeds", true).getBoolean();
		cropCollis = config.get("plants", "canPlantCollisSeeds", true).getBoolean();
		cropMaryjane = config.get("plants", "canPlantMaryjaneSeeds", true).getBoolean();
		cropEula = config.get("plants", "canPlantEulaSeeds", true).getBoolean();
		cropDyeius = config.get("plants", "canPlantDyeiusSeeds", true).getBoolean();
		cropCobblonia = config.get("plants", "canPlantCobbloniaSeeds", true).getBoolean();
		cropAbstract = config.get("plants", "canPlantAbstractSeeds", true).getBoolean();
		cropWafflonia = config.get("plants", "canPlantCliqiaSeeds", true).getBoolean();
		cropDevilsnare = config.get("plants", "canPlantDevilSnare", true).getBoolean();
		cropPixelsius = config.get("plants", "canPlantPixelsius", true).getBoolean();
		cropArtisia = config.get("plants", "canPlantArtisia", true).getBoolean();
		cropMalleatoris = config.get("plants", "canPlantMalleatoris", true).getBoolean();
		cropPetramia = config.get("plants", "canPlantPetramia", true).getBoolean();
		
		config.save();
	}
}
