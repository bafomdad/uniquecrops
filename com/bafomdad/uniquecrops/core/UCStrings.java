package com.bafomdad.uniquecrops.core;

import com.bafomdad.uniquecrops.UniqueCrops;

public class UCStrings {

	// GENERIC ITEMS
	public static String[] GENERIC = new String[] {
		"guidebook",
		"discountbook",
		"dirigibleplum",
		"cinderleaf",
		"timedust",
		"lilytwine",
		"goldenrods",
		"prenugget",
		"pregem",
		"essence",
		"timemeal",
		"invisitwine",
		"invisifeather",
		"potionreversesplash",
		"slipperglass",
		"weepingtear",
		"weepingeye",
		"millenniumeye",
		"upgradebook",
		"eggupgrade",
		"easybadge",
		"dogresidue"
	};
	
	// CATEGORIES
	public static String[] CROPCATS = new String[] {
		"Cinderbella",
		"Goldenrod",
		"Dirigible Plums",
		"Ender Lilies",
		"Invisibilia",
		"Knowledge Plant",
		"Maryjane",
		"Merlinia",
		"Millennium",
		"Musica",
		"Normal",
		"Precision",
		"Weeping Bells",
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
	
	public static final String CINDERBELLA = IMAGE + "cinderellapage.png";
	public static final String PAGE1 = BOOK + ".pagecinderbella";
	public static final String COLLIS = IMAGE + "collispage.png";
	public static final String PAGE2 = BOOK + ".pagecollis";
	public static final String DIRIGIBLE = IMAGE + "dirigiblepage.png";
	public static final String PAGE3 = BOOK + ".pagedirigible";
	public static final String ENDERLILY = IMAGE + "enderlilypage.png";
	public static final String PAGE4 = BOOK + ".pageenderlily";
	public static final String INVISIBILIA = IMAGE + "invisibiliapage.png";
	public static final String PAGE5 = BOOK + ".pageinvisibilia";
	public static final String KNOWLEDGE = IMAGE + "knowledgepage.png";
	public static final String PAGE6 = BOOK + ".pageknowledge";
	public static final String MARYJANE = IMAGE + "maryjanepage.png";
	public static final String PAGE7 = BOOK + ".pagemaryjane";
	public static final String MERLINIA = IMAGE + "merliniapage.png";
	public static final String PAGE8 = BOOK + ".pagemerlinia";
	public static final String MILLENNIUM = IMAGE + "millenniumpage.png";
	public static final String PAGE9 = BOOK + ".pagemillennium";
	public static final String MUSICA = IMAGE + "musicapage.png";
	public static final String PAGE10 = BOOK + ".pagemusica";
	public static final String NORMAL = IMAGE + "normalpage.png";
	public static final String PAGE11 = BOOK + ".pagenormal";
	public static final String PRECISION = IMAGE + "precisionpage.png";
	public static final String PAGE12 = BOOK + ".pageprecision";
	public static final String WEEPINGBELL = IMAGE + "weepingbellpage.png";
	public static final String PAGE13 = BOOK + ".pageweepingbell";
	public static final String FEROXIA = IMAGE + "feroxiapage.png";
	public static final String PAGE14 = BOOK + ".pageferoxia";
	
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
