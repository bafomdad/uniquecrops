package com.bafomdad.uniquecrops.core;

import net.minecraft.util.IStringSerializable;

public enum EnumCrops implements IStringSerializable {
	
	NORMAL("normal"),
	PRECISION("precision"),
	FLYINGPLANT("dirigible"),
	SHYPLANT("weepingbells"),
	BOOKPLANT("knowledge"),
	TELEPLANT("enderlily"),
	FOREVERPLANT("millennium"),
	BACKWARDSPLANT("merlinia"),
	INVISIBLEPLANT("invisibilia"),
	MUSICAPLANT("musica"),
	SAVAGEPLANT("feroxia"),
	CINDERBELLA("cinderbella"),
	HIGHPLANT("collis"),
	BLAZINGPLANT("maryjane");
	
	final String name;
	
	EnumCrops(String name) {
		
		this.name = name;
	}

	@Override
	public String getName() {

		return name;
	}
}
