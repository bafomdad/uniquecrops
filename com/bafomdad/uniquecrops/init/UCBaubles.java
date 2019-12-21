package com.bafomdad.uniquecrops.init;

import com.bafomdad.uniquecrops.items.baubles.*;

public class UCBaubles {

	public static ItemBauble
		emblemMelee,
		emblemScarab,
		emblemTransformation,
		emblemPowerfist,
		emblemRainbow,
		emblemFood,
		emblemIronstomach,
		emblemDefense,
		emblemLeaf,
		emblemPacifism,
		emblemBlacksmith,
		emblemWeight,
		emblemBookworm;

	public static void preInit() {
		
		emblemMelee = new EmblemMelee();
		emblemScarab = new EmblemScarab();
		emblemTransformation = new EmblemTransformation();
		emblemPowerfist = new EmblemPowerfist();
		emblemRainbow = new EmblemRainbow();
		emblemFood = new EmblemFood();
		emblemIronstomach = new EmblemIronstomach();
		emblemDefense = new EmblemDefense();
		emblemLeaf = new EmblemLeaf();
		emblemPacifism = new EmblemPacifism();
		emblemBlacksmith = new EmblemBlacksmith();
		emblemWeight = new EmblemWeight();
		emblemBookworm = new EmblemBookworm();
	}
}
