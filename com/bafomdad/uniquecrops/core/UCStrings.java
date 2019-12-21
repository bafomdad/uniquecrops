package com.bafomdad.uniquecrops.core;

import com.bafomdad.uniquecrops.UniqueCrops;

public class UCStrings {
	
	private static final String BASE = UniqueCrops.MOD_ID;
	public static final String TOOLTIP = BASE + ".tooltip.";
	public static final String INFOJEI = "info.jei." + BASE + ".";
	
	// MUSICA PLANT TAGS
	public static final String TAG_BEATLIST = "UC_tagBeatlist";
	public static final String TAG_BEAT = "UC_tagBeat";
	public static final String TAG_NOTE = "UC_tagNote";
	public static final String TAG_INST = "UC_tagInstrument";
	public static final String TAG_OCT = "UC_tagOctave";
	public static final String TAG_TIME = "UC_tagWorldtime";
	
	// MISC
	public static final String BLACKLIST_EFFECT = "blacklistPotionEffect";
	public static final String TAG_LOCK = "UC_tagLock";
	public static final String TAG_GROWTHSTAGES = "UC_FeroxiaGrowth";
	public static final String TAG_ABSTRACT = "UC_tagAbstractGrowth";
	public static final String TAG_EULA = "UC_tagAgreedToEula";
	public static final String TAG_BIOME = "UC_tagSavedBiome";
	public static final String TAG_ENCHANTSTAGE = "UC_EnchantStage";
	public static final String TAG_ENCHANT_TIMER = "UC_EnchantingTimer";
	public static final String TAG_ENCHANT_COST = "UC_EnchantmentCost";
	public static final String TAG_CUBE_ROTATION = "UC_RubiksCubeRotation";
	public static final String TAG_CUBE_SAVEDPOS = "UC_RubiksCubeSavedPos";
	public static final String LAST_POSITION = "UC:LastTeleportPosition";
	
	// BOOK
	private static final String BOOK = BASE + ".book";
	public static final String INTRO = BOOK + ".pageintro";
	
	// CONDITIONS
	private static final String COND = BASE + ".condition";
	public static final String MOON = COND + ".fullmoon";
	public static final String BURNINGPLAYER = COND + ".burningplayer";
	public static final String DRYFARMLAND = COND + ".dryfarmland";
	public static final String HASTORCH = COND + ".hastorch";
	public static final String HELLWORLD = COND + ".hellworld";
	public static final String DARKNESS = COND + ".darkness";
	public static final String LIKESHEIGHTS = COND + ".likesheights";
	public static final String UNDERFARMLAND = COND + ".underfarmland";
	public static final String LILYPADS = COND + ".likeslilypads";
	public static final String HUNGRYPLANT = COND + ".hungryplant";
	public static final String THIRSTYPLANT = COND + ".thirstyplant";
	public static final String LIKESCHICKEN = COND + ".likeschicken";
	public static final String REDSTONE = COND + ".redstone";
	public static final String VAMPIREPLANT = COND + ".vampire";
	public static final String FULLBRIGHTNESS = COND + ".fullbrightness";
	public static final String LIKESWARTS = COND + ".likeswarts";
	public static final String LIKESCOOKING = COND + ".likescooking";
	public static final String LIKESBREWING = COND + ".likesbrewing";
	public static final String LIKESCHECKERS = COND + ".likescheckers";
	public static final String DONTBONEMEAL = COND + ".dontbonemeal";
	public static final String SELFSACRIFICE = COND + ".selfsacrifice";
	
	// Test MD5
	public static final String[] MD5 = new String[] {
		"FA7C27A064D1697162FDE479C389E57D".toLowerCase(),
		"8C958A33F39EEB1D4CBA8882640F83C3".toLowerCase(),
		"4A991BEB80CB60883BB9E440D81ED1B7".toLowerCase(),
		"B3B847DF6200864E7766F0FACBDDA8C8".toLowerCase(),
		"930E44CD55FD29745EDF84B068282F28".toLowerCase()
	};
	public static final String MD5_GLASS = "4AED5AFD5333591CFD505049A39F4551".toLowerCase();
	
	public static final String[] DEFAULT_MULTIBLOCKS = new String[] {
		"artisia.json",
		"cocito.json",
		"exedo.json",
		"fascino.json",
		"itero.json",
		"portal.json",
		"weatherflesia.json"
	};
}
