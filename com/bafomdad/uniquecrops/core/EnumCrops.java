package com.bafomdad.uniquecrops.core;

import net.minecraft.util.IStringSerializable;

public enum EnumCrops implements IStringSerializable {
	
	NORMAL("normal", UCConfig.cropNormal),
	PRECISION("precision", UCConfig.cropPrecision),
	FLYINGPLANT("dirigible", UCConfig.cropDirigible),
	SHYPLANT("weepingbells", UCConfig.cropWeepingbells),
	BOOKPLANT("knowledge", UCConfig.cropKnowledge),
	TELEPLANT("enderlily", UCConfig.cropEnderlily),
	FOREVERPLANT("millennium", UCConfig.cropMillennium),
	BACKWARDSPLANT("merlinia", UCConfig.cropMerlinia),
	INVISIBLEPLANT("invisibilia", UCConfig.cropInvisibilia),
	MUSICAPLANT("musica", UCConfig.cropMusica),
	SAVAGEPLANT("feroxia", UCConfig.cropFeroxia),
	CINDERBELLA("cinderbella", UCConfig.cropCinderbella),
	HIGHPLANT("collis", UCConfig.cropCollis),
	BLAZINGPLANT("maryjane", UCConfig.cropMaryjane),
	EULA("eula", UCConfig.cropEula),
	DYE("dyeius", UCConfig.cropDyeius),
	COBBLEPLANT("cobblonia", UCConfig.cropCobblonia),
	ABSTRACT("abstract", UCConfig.cropAbstract),
	DEVILSNARE("devilsnare", UCConfig.cropDevilsnare),
	WAFFLE("wafflonia", UCConfig.cropWafflonia),
	PIXELS("pixelsius", UCConfig.cropPixelsius),
	CRAFTER("artisia", UCConfig.cropArtisia),
	BEDROCKIUM("petramia", UCConfig.cropPetramia),
	ANVILICIOUS("malleatoris", UCConfig.cropMalleatoris),
	IMPERIA("imperia", UCConfig.cropImperia),
	LACUSIA("lacusia", UCConfig.cropLacusia),
	HEXIS("hexis", UCConfig.cropHexis),
	ENERGY("industria", UCConfig.cropIndustria),
	DIGGER("quarry", UCConfig.cropQuarry),
	ORIGINAL("donutsteel", UCConfig.cropDonutSteel),
	UNSTABLE("instabilis", UCConfig.cropInstabilis),
	VAMPIRE("succo", UCConfig.cropVampire);
	
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
