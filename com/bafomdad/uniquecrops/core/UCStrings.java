package com.bafomdad.uniquecrops.core;

import com.bafomdad.uniquecrops.UniqueCrops;

public class UCStrings {
	
	// CATEGORIES
	public static String[] CROPCATS = new String[] {
		"Normal",
		"Artisia",
		"Goldenrod",
		"Dirigible Plums",
		"Ender Lilies",
		"Invisibilia",
		"Knowledge Plant",
		"Maryjane",
		"Merlinia",
		"Millennium",
		"Musica"
	};
	
	public static String[] CROPCATS2 = new String[] {
		"Cinderbella",
		"Precision",
		"Weeping Bells",
		"Eula",
		"Dyeius",
		"Cobblonia",
		"Abstract",
		"Pixelsius",
		"Devil's Snare",
		"Wafflonia",
		"Feroxia"
	};
	
	private static final String BASE = UniqueCrops.MOD_ID;
	public static final String TOOLTIP = BASE + ".tooltip.";
	
	// NBT TAGS
	public static String TAG_BEATLIST = "UC_tagBeatlist";
	public static String TAG_BEAT = "UC_tagBeat";
	public static String TAG_NOTE = "UC_tagNote";
	public static String TAG_INST = "UC_tagInstrument";
	public static String TAG_OCT = "UC_tagOctave";
	public static String TAG_TIME = "UC_tagWorldtime";
	
	// BOOK
	private static final String BOOK = BASE + ".book";
	private static final String IMAGE = UniqueCrops.MOD_ID + ":" + "textures/gui/";
	public static final String INTRO = BOOK + ".pageintro";
	
	public static final String NORMAL = IMAGE + "normalpage.png";
	public static final String PAGENORMAL = BOOK + ".pagenormal";
	public static final String COLLIS = IMAGE + "collispage.png";
	public static final String PAGECOLLIS = BOOK + ".pagecollis";
	public static final String DIRIGIBLE = IMAGE + "dirigiblepage.png";
	public static final String PAGEPLUM = BOOK + ".pagedirigible";
	public static final String ENDERLILY = IMAGE + "enderlilypage.png";
	public static final String PAGELILY = BOOK + ".pageenderlily";
	public static final String INVISIBILIA = IMAGE + "invisibiliapage.png";
	public static final String PAGEINVIS = BOOK + ".pageinvisibilia";
	public static final String KNOWLEDGE = IMAGE + "knowledgepage.png";
	public static final String PAGEKNOWLEDGE = BOOK + ".pageknowledge";
	public static final String MARYJANE = IMAGE + "maryjanepage.png";
	public static final String PAGEMARY = BOOK + ".pagemaryjane";
	public static final String MERLINIA = IMAGE + "merliniapage.png";
	public static final String PAGEMERLIN = BOOK + ".pagemerlinia";
	public static final String MILLENNIUM = IMAGE + "millenniumpage.png";
	public static final String PAGEMILL = BOOK + ".pagemillennium";
	public static final String MUSICA = IMAGE + "musicapage.png";
	public static final String PAGEMUSIC = BOOK + ".pagemusica";
	public static final String CINDERBELLA = IMAGE + "cinderellapage.png";
	public static final String PAGECINDER = BOOK + ".pagecinderbella";
	public static final String PRECISION = IMAGE + "precisionpage.png";
	public static final String PAGEPRECISE = BOOK + ".pageprecision";
	public static final String WEEPINGBELL = IMAGE + "weepingbellpage.png";
	public static final String PAGEWEEP = BOOK + ".pageweepingbell";
	public static final String EULA = IMAGE + "eulapage.png";
	public static final String PAGELEGAL = BOOK + ".pageeula";
	public static final String DYEIUS = IMAGE + "dyeiuspage.png";
	public static final String PAGEDYE = BOOK + ".pagedyeius";
	public static final String COBBLONIA = IMAGE + "cobbloniapage.png";
	public static final String PAGECOBBLE = BOOK + ".pagecobblonia";
	public static final String ABSTRACT = IMAGE + "abstractpage.png";
	public static final String PAGEABSTRACT = BOOK + ".pageabstract";
	public static final String CRAFTER = IMAGE + "artisiapage.png";
	public static final String PAGECRAFT = BOOK + ".pageartisia";
	public static final String WAFFLES = IMAGE + "waffloniapage.png";
	public static final String PAGEWAFFLE = BOOK + ".pagewafflonia";
	public static final String DEVILSNARE = IMAGE + "devilsnarepage.png";
	public static final String PAGESNARE = BOOK + ".pagedevilsnare";
	public static final String PIXELSIUS = IMAGE + "pixelsiuspage.png";
	public static final String PAGEPIXEL = BOOK + ".pagepixelsius";
	public static final String FEROXIA = IMAGE + "feroxiapage.png";
	public static final String PAGESAVAGE = BOOK + ".pageferoxia";
	
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
	public static final String SELFSACRIFICE = COND + ".selfsacrifice";
	
	// Test MD5
	public static String[] MD5 = new String[] {
		"F860A278CA7BAC76F492DBD6C8F36163".toLowerCase(),
		"CF63C9912EBF9A888B25D7BF0C0854AF".toLowerCase(),
		"F8E4529942FEEDC6FC84BE4DDB28C404".toLowerCase(),
		"F5B40BA19AEE796647C30C31376D3DF8".toLowerCase(),
		"51B8884D1B4565EFDC6DF440310D00F3".toLowerCase()
	};
}
