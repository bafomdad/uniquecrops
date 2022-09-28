package com.bafomdad.uniquecrops.init;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.api.*;
import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.core.enums.EnumArmorMaterial;
import com.bafomdad.uniquecrops.items.*;
import com.bafomdad.uniquecrops.items.base.*;
import com.bafomdad.uniquecrops.items.curios.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class UCItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, UniqueCrops.MOD_ID);

    /**
     * GENERAL
     */
    public static final RegistryObject<Item> BOOK_GUIDE = register("book_guide", GuideBookItem::new);
    public static final RegistryObject<Item> BOOK_MULTIBLOCK = register("book_multiblock", MultiblockBookItem::new);
    public static final RegistryObject<Item> BOOK_DISCOUNT = register("book_discount", () -> new ItemBaseUC(unstackable()));
    public static final RegistryObject<Item> BOOK_UPGRADE = register("book_upgrade", () -> new ItemBaseUC(unstackable()));
    public static final RegistryObject<Item> BOOK_EULA = register("book_eula", EulaBookItem::new);
    public static final RegistryObject<Item> DIRIGIBLEPLUM = register("dirigibleplum", DirigiblePlumItem::new);
    public static final RegistryObject<Item> CINDERLEAF = register("cinderleaf", CinderleafItem::new);
    public static final RegistryObject<Item> TIMEDUST = register("timedust", ItemBaseUC::new);
    public static final RegistryObject<Item> LILYTWINE = register("lilytwine", ItemBaseUC::new);
    public static final RegistryObject<Item> GOLDENRODS = register("goldenrods", ItemBaseUC::new);
    public static final RegistryObject<Item> PRENUGGET = register("prenugget", ItemBaseUC::new);
    public static final RegistryObject<Item> PREGEM = register("pregem", ItemBaseUC::new);
    public static final RegistryObject<Item> SAVAGEESSENCE = register("savageessence", ItemBaseUC::new);
    public static final RegistryObject<Item> TIMEMEAL = register("timemeal", ItemBaseUC::new);
    public static final RegistryObject<Item> INVISITWINE = register("invisitwine", ItemBaseUC::new);
    public static final RegistryObject<Item> INVISIFEATHER = register("invisifeather", ItemBaseUC::new);
    public static final RegistryObject<Item> SLIPPERGLASS = register("slipperglass", ItemBaseUC::new);
    public static final RegistryObject<Item> WEEPINGTEAR = register("weepingtear", ItemBaseUC::new);
    public static final RegistryObject<Item> WEEPINGEYE = register("weepingeye", WeepingEyeItem::new);
    public static final RegistryObject<Item> MILLENNIUMEYE = register("millenniumeye", ItemBaseUC::new);
    public static final RegistryObject<Item> EGGUPGRADE = register("eggupgrade", EggUpgradeItem::new);
    public static final RegistryObject<Item> EASYBADGE = register("easybadge", EasyBadgeItem::new);
    public static final RegistryObject<Item> DOGRESIDUE = register("dogresidue", DogResidueItem::new);
    public static final RegistryObject<Item> ABSTRACT = register("abstract", ItemBaseUC::new);
    public static final RegistryObject<Item> LEGALSTUFF = register("legalstuff", ItemBaseUC::new);
    public static final RegistryObject<Item> PIXELS = register("pixels", ItemBaseUC::new);
    public static final RegistryObject<Item> ESCAPEROPE = register("escaperope", EscapeRopeItem::new);
    public static final RegistryObject<Item> CUBEYTHINGY = register("cubeythingy", ItemBaseUC::new);
    public static final RegistryObject<Item> FERROMAGNETICIRON = register("ferromagnetic_iron", ItemBaseUC::new);
    public static final RegistryObject<Item> SPIRITBAIT = register("spirit_bait", ItemBaseUC::new);
    public static final RegistryObject<Item> ENDERSNOOKER = register("endersnooker", EnderSnookerItem::new);
    public static final RegistryObject<Item> HANDMIRROR = register("handmirror", HandMirrorItem::new);
    public static final RegistryObject<Item> BATSTAFF = register("batstaff", StaffBatItem::new);
    public static final RegistryObject<Item> PHANTOMSTAFF = register("phantomstaff", StaffPhantomItem::new);
    public static final RegistryObject<Item> BEAN_BATTERY = register("bean_battery", BeanBatteryItem::new);
    public static final RegistryObject<Item> WILDWOOD_STAFF = register("wildwood_staff", StaffWildwoodItem::new);
    public static final RegistryObject<Item> VAMPIRIC_OINTMENT = register("vampiric_ointment", VampiricOintmentItem::new);
    public static final RegistryObject<Item> STEEL_DONUT = register("steel_donut", ItemBaseUC::new);
    public static final RegistryObject<Item> ANKH = register("ankh", AnkhItem::new);
    public static final RegistryObject<Item> GOODIE_BAG = register("goodie_bag", GoodieBagItem::new);
    public static final RegistryObject<Item> EMERADIC_DIAMOND = register("emeradic_diamond", EmeradicDiamondItem::new);
    public static final RegistryObject<Item> USELESS_LUMP = register("useless_lump", ItemBaseUC::new);
    public static final RegistryObject<Item> DIAMONDS = register("diamonds", DiamondBunchItem::new);
    public static final RegistryObject<Item> PIXEL_BRUSH = register("pixel_brush", PixelBrushItem::new);
    public static final RegistryObject<Item> RUBIKS_CUBE = register("rubiks_cube", RubiksCubeItem::new);
    public static final RegistryObject<Item> BOILED_MILK = register("boiled_milk", ItemBaseUC::new);
    public static final RegistryObject<Item> ITEM_MAGNET = register("item_magnet", MagnetItem::new);
    public static final RegistryObject<Item> IMPREGNATED_LEATHER = register("impregnated_leather", ImpregnatedLeatherItem::new);
    public static final RegistryObject<Item> ENCHANTED_LEATHER = register("enchanted_leather", EnchantedLeatherItem::new);
    public static final RegistryObject<Item> ZOMBIE_SLURRY = register("zombie_slurry", ItemBaseUC::new);

    /**
     * FOOD & POTIONS
     */
    public static final RegistryObject<Item> LARGE_PLUM = registerFood("large_plum", UCFoods.LARGE_PLUM);
    public static final RegistryObject<Item> TERIYAKI = registerFood("teriyaki", UCFoods.TERIYAKI);
    public static final RegistryObject<Item> STEVE_HEART = registerFood("steveheart", UCFoods.STEVE_HEART);
    public static final RegistryObject<Item> GOLDEN_BREAD = registerFood("golden_bread", UCFoods.GOLDEN_BREAD);
    public static final RegistryObject<Item> DIET_PILLS = registerFood("diet_pills", UCFoods.DIET_PILLS);
    public static final RegistryObject<Item> UNCOOKEDWAFFLE = register("uncookedwaffle", ItemBaseUC::new);
    public static final RegistryObject<Item> WAFFLE = registerFood("waffle", UCFoods.WAFFLE);
    public static final RegistryObject<Item> YOGURT = registerFood("yogurt", UCFoods.YOGURT);
    public static final RegistryObject<Item> EGGNOG = registerFood("eggnog", UCFoods.EGGNOG);
    public static final RegistryObject<Item> POTION_REVERSE = register("potionreverse", () -> new ItemBaseUC(unstackable().food(UCFoods.REVERSE_POTION).craftRemainder(Items.GLASS_BOTTLE)));
    public static final RegistryObject<Item> POTION_ENNUI = register("potionennui", () -> new ItemBaseUC(unstackable().food(UCFoods.ENNUI_POTION).craftRemainder(Items.GLASS_BOTTLE)));
    public static final RegistryObject<Item> POTION_IGNORANCE = register("potionignorance", () -> new ItemBaseUC(unstackable().food(UCFoods.IGNORANCE_POTION).craftRemainder(Items.GLASS_BOTTLE)));
    public static final RegistryObject<Item> POTION_ZOMBIFICATION = register("potionzombification", () -> new ItemBaseUC(unstackable().food(UCFoods.ZOMBIFICATION_POTION).craftRemainder(Items.GLASS_BOTTLE)));

    /**
     * GEAR
     */
    public static final RegistryObject<Item> GLASSES_3D = register("glasses_3d",  Glasses3DItem::new);
    public static final RegistryObject<Item> GLASSES_PIXELS = register("glasses_pixels", GlassesPixelItem::new);
    public static final RegistryObject<Item> PONCHO = register("poncho", PonchoItem::new);
    public static final RegistryObject<Item> GLASS_SLIPPERS = register("slippers", () -> new ItemArmorUC(EnumArmorMaterial.SLIPPERS, EquipmentSlot.FEET));
    public static final RegistryObject<Item> THUNDERPANTZ = register("thunderpantz", ThunderpantzItem::new);
    public static final RegistryObject<Item> CACTUS_HELM = register("cactus_helm", () -> new ItemArmorUC(EnumArmorMaterial.CACTUS, EquipmentSlot.HEAD));
    public static final RegistryObject<Item> CACTUS_CHESTPLATE = register("cactus_plate", () -> new ItemArmorUC(EnumArmorMaterial.CACTUS, EquipmentSlot.CHEST));
    public static final RegistryObject<Item> CACTUS_LEGGINGS = register("cactus_leggings", () -> new ItemArmorUC(EnumArmorMaterial.CACTUS, EquipmentSlot.LEGS));
    public static final RegistryObject<Item> CACTUS_BOOTS = register("cactus_boots", () -> new ItemArmorUC(EnumArmorMaterial.CACTUS, EquipmentSlot.FEET));
    public static final RegistryObject<Item> SEVEN_LEAGUE_BOOTS = register("seven_league_boots", LeagueBootsItem::new);
    public static final RegistryObject<Item> PRECISION_PICK = register("precision_pick", PrecisionPickaxeItem::new);
    public static final RegistryObject<Item> PRECISION_AXE = register("precision_axe", PrecisionAxeItem::new);
    public static final RegistryObject<Item> PRECISION_SHOVEL = register("precision_shovel", PrecisionShovelItem::new);
    public static final RegistryObject<Item> PRECISION_SWORD = register("precision_sword", PrecisionSwordItem::new);
    public static final RegistryObject<Item> PRECISION_HAMMER = register("precision_hammer", PrecisionHammerItem::new);
    public static final RegistryObject<Item> IMPACT_SHIELD = register("impact_shield", ImpactShieldItem::new);
    public static final RegistryObject<Item> BRASS_KNUCKLES = register("brass_knuckles", BrassKnucklesItem::new);

    /**
     * EMBLEMS
     */
    public static final RegistryObject<Item> EMBLEM_BLACKSMITH = register("emblem_blacksmith", EmblemBlacksmith::new);
    public static final RegistryObject<Item> EMBLEM_BOOKWORM = register("emblem_bookworm", EmblemBookworm::new);
    public static final RegistryObject<Item> EMBLEM_DEFENSE = register("emblem_defense", EmblemDefense::new);
    public static final RegistryObject<Item> EMBLEM_FOOD = register("emblem_food", EmblemFood::new);
    public static final RegistryObject<Item> EMBLEM_IRONSTOMACH = register("emblem_ironstomach", EmblemIronStomach::new);
    public static final RegistryObject<Item> EMBLEM_LEAF = register("emblem_leaf", EmblemLeaf::new);
    public static final RegistryObject<Item> EMBLEM_MELEE = register("emblem_melee", EmblemMelee::new);
    public static final RegistryObject<Item> EMBLEM_PACIFISM = register("emblem_pacifism", EmblemPacifism::new);
    public static final RegistryObject<Item> EMBLEM_POWERFIST = register("emblem_powerfist", EmblemPowerfist::new);
    public static final RegistryObject<Item> EMBLEM_RAINBOW = register("emblem_rainbow", EmblemRainbow::new);
    public static final RegistryObject<Item> EMBLEM_SCARAB = register("emblem_scarab", EmblemScarab::new);
    public static final RegistryObject<Item> EMBLEM_TRANSFORMATION = register("emblem_transformation", EmblemTransformation::new);
    public static final RegistryObject<Item> EMBLEM_WEIGHT = register("emblem_weight", EmblemWeight::new);

    /**
     * MUSIC DISCS
     */
    public static final RegistryObject<Item> RECORD_FARAWAY = register("record_faraway", () -> new ItemRecordUC(UCSounds.FAR_AWAY));
    public static final RegistryObject<Item> RECORD_NEONSIGNS = register("record_neonsigns", () -> new ItemRecordUC(UCSounds.NEON_SIGNS));
    public static final RegistryObject<Item> RECORD_SIMPLY = register("record_simply", () -> new ItemRecordUC(UCSounds.SIMPLY));
    public static final RegistryObject<Item> RECORD_TAXI = register("record_taxi", () -> new ItemRecordUC(UCSounds.TAXI));

    /**
     * SEEDS
     */
    public static final RegistryObject<BlockItem> ABSTRACT_SEED = registerSeed("abstract", UCBlocks.ABSTRACT_CROP);
    public static final RegistryObject<BlockItem> ADVENTUS_SEED = registerSeed("adventus", UCBlocks.ADVENTUS_CROP);
    public static final RegistryObject<BlockItem> ARTISIA_SEED = registerSeed("artisia", UCBlocks.ARTISIA_CROP);
    public static final RegistryObject<BlockItem> BLESSED_SEED = registerSeed("blessed", UCBlocks.HOLY_CROP);
    public static final RegistryObject<BlockItem> CINDERBELLA_SEED = registerSeed("cinderbella", UCBlocks.CINDERBELLA_CROP);
    public static final RegistryObject<BlockItem> COLLIS_SEED = registerSeed("collis", UCBlocks.COLLIS_CROP);
    public static final RegistryObject<BlockItem> COBBLONIA_SEED = registerSeed("cobblonia", UCBlocks.COBBLONIA_CROP);
    public static final RegistryObject<BlockItem> DEVILSNARE_SEED = registerSeed("devilsnare", UCBlocks.DEVILSNARE_CROP);
    public static final RegistryObject<BlockItem> DIRIGIBLE_SEED = registerSeed("dirigible", UCBlocks.DIRIGIBLE_CROP);
    public static final RegistryObject<BlockItem> DONUTSTEEL_SEED = registerSeed("donutsteel", UCBlocks.DONUTSTEEL_CROP);
    public static final RegistryObject<BlockItem> DYEIUS_SEED = registerSeed("dyeius", UCBlocks.DYEIUS_CROP);
    public static final RegistryObject<BlockItem> ENDERLILY_SEED = registerSeed("enderlily", UCBlocks.ENDERLILY_CROP);
    public static final RegistryObject<BlockItem> EULA_SEED = registerSeed("eula", UCBlocks.EULA_CROP);
    public static final RegistryObject<BlockItem> FEROXIA_SEED = registerSeed("feroxia", UCBlocks.FEROXIA_CROP);
    public static final RegistryObject<BlockItem> HEXIS_SEED = registerSeed("hexis", UCBlocks.HEXIS_CROP);
    public static final RegistryObject<BlockItem> IMPERIA_SEED = registerSeed("imperia", UCBlocks.IMPERIA_CROP);
    public static final RegistryObject<BlockItem> INDUSTRIA_SEED = registerSeed("industria", UCBlocks.INDUSTRIA_CROP);
    public static final RegistryObject<BlockItem> INSTABILIS_SEED = registerSeed("instabilis", UCBlocks.INSTABILIS_CROP);
    public static final RegistryObject<BlockItem> INVISIBILIA_SEED = registerSeed("invisibilia", UCBlocks.INVISIBILIA_CROP);
    public static final RegistryObject<BlockItem> KNOWLEDGE_SEED = registerSeed("knowledge", UCBlocks.KNOWLEDGE_CROP);
    public static final RegistryObject<BlockItem> LACUSIA_SEED = registerSeed("lacusia", UCBlocks.LACUSIA_CROP);
    public static final RegistryObject<BlockItem> MAGNES_SEED = registerSeed("magnes", UCBlocks.MAGNES_CROP);
    public static final RegistryObject<BlockItem> MALLEATORIS_SEED = registerSeed("malleatoris", UCBlocks.MALLEATORIS_CROP);
    public static final RegistryObject<BlockItem> MARYJANE_SEED = registerSeed("maryjane", UCBlocks.MARYJANE_CROP);
    public static final RegistryObject<BlockItem> MERLINIA_SEED = registerSeed("merlinia", UCBlocks.MERLINIA_CROP);
    public static final RegistryObject<BlockItem> MILLENNIUM_SEED = registerSeed("millennium", UCBlocks.MILLENNIUM_CROP);
    public static final RegistryObject<BlockItem> MUSICA_SEED = registerSeed("musica", UCBlocks.MUSICA_CROP);
    public static final RegistryObject<BlockItem> NORMAL_SEED = registerSeed("normal", UCBlocks.NORMAL_CROP);
    public static final RegistryObject<BlockItem> PETRAMIA_SEED = registerSeed("petramia", UCBlocks.PETRAMIA_CROP);
    public static final RegistryObject<BlockItem> PIXELSIUS_SEED = registerSeed("pixelsius", UCBlocks.PIXELSIUS_CROP);
    public static final RegistryObject<BlockItem> PRECISION_SEED = registerSeed("precision", UCBlocks.PRECISION_CROP);
    public static final RegistryObject<BlockItem> QUARRY_SEED = registerSeed("quarry", UCBlocks.QUARRY_CROP);
    public static final RegistryObject<BlockItem> SUCCO_SEED = registerSeed("succo", UCBlocks.SUCCO_CROP);
    public static final RegistryObject<BlockItem> WAFFLONIA_SEED = registerSeed("wafflonia", UCBlocks.WAFFLONIA_CROP);
    public static final RegistryObject<BlockItem> WEEPINGBELLS_SEED = registerSeed("weepingbells", UCBlocks.WEEPINGBELLS_CROP);

    /**
     * DYED BONEMEALS
     */
    public static final RegistryObject<Item> WHITE_BONEMEAL = register("dyedbonemeal.white", DyedBonemealItem::new);
    public static final RegistryObject<Item> ORANGE_BONEMEAL = register("dyedbonemeal.orange", DyedBonemealItem::new);
    public static final RegistryObject<Item> MAGENTA_BONEMEAL = register("dyedbonemeal.magenta", DyedBonemealItem::new);
    public static final RegistryObject<Item> LIGHTBLUE_BONEMEAL = register("dyedbonemeal.light_blue", DyedBonemealItem::new);
    public static final RegistryObject<Item> YELLOW_BONEMEAL = register("dyedbonemeal.yellow", DyedBonemealItem::new);
    public static final RegistryObject<Item> LIME_BONEMEAL = register("dyedbonemeal.lime", DyedBonemealItem::new);
    public static final RegistryObject<Item> PINK_BONEMEAL = register("dyedbonemeal.pink", DyedBonemealItem::new);
    public static final RegistryObject<Item> GRAY_BONEMEAL = register("dyedbonemeal.gray", DyedBonemealItem::new);
    public static final RegistryObject<Item> SILVER_BONEMEAL = register("dyedbonemeal.silver", DyedBonemealItem::new);
    public static final RegistryObject<Item> CYAN_BONEMEAL = register("dyedbonemeal.cyan", DyedBonemealItem::new);
    public static final RegistryObject<Item> PURPLE_BONEMEAL = register("dyedbonemeal.purple", DyedBonemealItem::new);
    public static final RegistryObject<Item> BLUE_BONEMEAL = register("dyedbonemeal.blue", DyedBonemealItem::new);
    public static final RegistryObject<Item> BROWN_BONEMEAL = register("dyedbonemeal.brown", DyedBonemealItem::new);
    public static final RegistryObject<Item> GREEN_BONEMEAL = register("dyedbonemeal.green", DyedBonemealItem::new);
    public static final RegistryObject<Item> RED_BONEMEAL = register("dyedbonemeal.red", DyedBonemealItem::new);
    public static final RegistryObject<Item> BLACK_BONEMEAL = register("dyedbonemeal.black", DyedBonemealItem::new);

    /**
     * DUMMIES
     */
    public static final RegistryObject<Item> DUMMY_ARTISIA = register("dummy_artisia", ItemDummyUC::new);
    public static final RegistryObject<Item> DUMMY_HEATER = register("dummy_heater", ItemDummyUC::new);
    public static final RegistryObject<Item> DUMMY_FASCINO = register("dummy_fascino", ItemRenderUC::new);

    public static <I extends Item> RegistryObject<I> register(String name, Supplier<I> supplier) {

        return ITEMS.register(name, supplier);
    }

    public static RegistryObject<BlockItem> register(String name, RegistryObject<Block> block) {

        return ITEMS.register(name, () -> new BlockItem(block.get(), defaultBuilder()));
    }

    public static RegistryObject<Item> registerFood(String name, FoodProperties food) {

        return ITEMS.register(name, () -> new Item(new Item.Properties().food(food).tab(UniqueCrops.TAB)));
    }

    public static RegistryObject<BlockItem> registerSeed(String name, RegistryObject<BaseCropsBlock> crop) {

        return ITEMS.register("seed" + name, () -> new ItemSeedsUC(crop.get()));
    }

    public static Item.Properties noTab() {

        return new Item.Properties();
    }

    public static Item.Properties defaultBuilder() {

        return new Item.Properties().tab(UniqueCrops.TAB);
    }

    public static Item.Properties unstackable() {

        return defaultBuilder().stacksTo(1);
    }

    /** custom recipes start here */
    public static final RecipeType<IArtisiaRecipe> ARTISIA_TYPE = new ModRecipeType<>();
    public static final RecipeType<IHourglassRecipe> HOURGLASS_TYPE = new ModRecipeType<>();
    public static final RecipeType<IEnchanterRecipe> ENCHANTER_TYPE = new ModRecipeType<>();
    public static final RecipeType<IHeaterRecipe> HEATER_TYPE = new ModRecipeType<>();
    public static final RecipeType<IMultiblockRecipe> MULTIBLOCK_TYPE = new ModRecipeType<>();

    public static void registerItemsButNotReally(RegistryEvent.Register<Item> event) {

        ResourceLocation id;

        id = new ResourceLocation(UniqueCrops.MOD_ID, "artisia");
        Registry.register(Registry.RECIPE_TYPE, id, ARTISIA_TYPE);

        id = new ResourceLocation(UniqueCrops.MOD_ID, "hourglass");
        Registry.register(Registry.RECIPE_TYPE, id, HOURGLASS_TYPE);

        id = new ResourceLocation(UniqueCrops.MOD_ID, "enchanter");
        Registry.register(Registry.RECIPE_TYPE, id, ENCHANTER_TYPE);

        id = new ResourceLocation(UniqueCrops.MOD_ID, "heater");
        Registry.register(Registry.RECIPE_TYPE, id, HEATER_TYPE);

        id = new ResourceLocation(UniqueCrops.MOD_ID, "multiblock");
        Registry.register(Registry.RECIPE_TYPE, id, MULTIBLOCK_TYPE);
    }

    private static class ModRecipeType<T extends Recipe<?>> implements RecipeType<T> {

        @Override
        public String toString() {

            return Registry.RECIPE_TYPE.getKey(this).toString();
        }
    }
}
