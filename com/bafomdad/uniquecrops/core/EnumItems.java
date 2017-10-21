package com.bafomdad.uniquecrops.core;

import net.minecraft.util.IStringSerializable;

public enum EnumItems implements IStringSerializable {
	
	GUIDE("guidebook"),
	DISCOUNT("discountbook"),
	PLUM("dirigibleplum"),
	CINDERLEAF("cinderleaf"),
	TIMEDUST("timedust"),
	LILYTWINE("lilytwine"),
	GOLDENRODS("goldenrods"),
	PRENUGGET("prenugget"),
	PREGEM("pregem"),
	ESSENCE("essence"),
	TIMEMEAL("timemeal"),
	INVISITWINE("invisitwine"),
	INVISIFEATHER("invisifeather"),
	POTIONSPLASH("potionreversesplash"),
	SLIPPER("slipperglass"),
	WEEPINGTEAR("weepingtear"),
	WEEPINGEYE("weepingeye"),
	MILLENNIUMEYE("millenniumeye"),
	UPGRADE("upgradebook"),
	EGGUPGRADE("eggupgrade"),
	EASYBADGE("easybadge"),
	DOGRESIDUE("dogresidue"),
	ABSTRACT("abstract"),
	LEGALSTUFF("legalstuff"),
	EULA("eulabook"),
	DUMMYITEM("dummy"),
	PIXELS("pixels"),
	BLUEDYE("bluedye"),
	ESCAPEROPE("escaperope");

	final String name;
	
	EnumItems(String name) {
		
		this.name = name;
	}
	
	@Override
	public String getName() {

		return name;
	}
}
