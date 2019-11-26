package com.bafomdad.uniquecrops.core.enums;

import com.bafomdad.uniquecrops.core.UCConfig.CropConfig;

import net.minecraft.util.IStringSerializable;

public enum EnumCrops implements IStringSerializable {
	
	NORMAL("normal", CropConfig.cropNormal),
	PRECISION("precision", CropConfig.cropPrecision),
	FLYINGPLANT("dirigible", CropConfig.cropDirigible),
	SHYPLANT("weepingbells", CropConfig.cropWeepingbells),
	BOOKPLANT("knowledge", CropConfig.cropKnowledge),
	TELEPLANT("enderlily", CropConfig.cropEnderlily),
	FOREVERPLANT("millennium", CropConfig.cropMillennium),
	BACKWARDSPLANT("merlinia", CropConfig.cropMerlinia),
	INVISIBLEPLANT("invisibilia", CropConfig.cropInvisibilia),
	MUSICAPLANT("musica", CropConfig.cropMusica),
	SAVAGEPLANT("feroxia", CropConfig.cropFeroxia),
	CINDERBELLA("cinderbella", CropConfig.cropCinderbella),
	HIGHPLANT("collis", CropConfig.cropCollis),
	BLAZINGPLANT("maryjane", CropConfig.cropMaryjane),
	EULA("eula", CropConfig.cropEula),
	DYE("dyeius", CropConfig.cropDyeius),
	COBBLEPLANT("cobblonia", CropConfig.cropCobblonia),
	ABSTRACT("abstract", CropConfig.cropAbstract),
	DEVILSNARE("devilsnare", CropConfig.cropDevilsnare),
	WAFFLE("wafflonia", CropConfig.cropWafflonia),
	PIXELS("pixelsius", CropConfig.cropPixelsius),
	CRAFTER("artisia", CropConfig.cropArtisia),
	BEDROCKIUM("petramia", CropConfig.cropPetramia),
	ANVILICIOUS("malleatoris", CropConfig.cropMalleatoris),
	IMPERIA("imperia", CropConfig.cropImperia),
	LACUSIA("lacusia", CropConfig.cropLacusia),
	HEXIS("hexis", CropConfig.cropHexis),
	ENERGY("industria", CropConfig.cropIndustria),
	DIGGER("quarry", CropConfig.cropQuarry),
	ORIGINAL("donutsteel", CropConfig.cropDonutSteel),
	UNSTABLE("instabilis", CropConfig.cropInstabilis),
	VAMPIRE("succo", CropConfig.cropVampire),
	ADVENTUS("adventus", CropConfig.cropAdventus),
	HOLY("blessed", CropConfig.cropHoly);
	
	final String name;
	final boolean config;
	
	EnumCrops(String name, boolean config) {
		
		this.name = name;
		this.config = config;
	}

	@Override
	public String getName() {

		return name;
	}
	
	public boolean getConfig() {
		
		return config;
	}
}
