package com.bafomdad.uniquecrops.core.enums;

import net.minecraft.util.IStringSerializable;

public enum EnumSuperCrops implements IStringSerializable {

	EXEDO("exedo"),
	COCITO("cocito"),
	ITERO("itero"),
	FASCINO("fascino"),
	WEATHER("weatherflesia"),
	LIGNATOR("treecutter");
	
	final String name;
	
	EnumSuperCrops(String name) {
		
		this.name = name;
	}

	@Override
	public String getName() {

		return name;
	}
}
