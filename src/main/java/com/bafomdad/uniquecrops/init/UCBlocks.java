package com.bafomdad.uniquecrops.init;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.*;
import com.bafomdad.uniquecrops.blocks.crops.*;
import com.bafomdad.uniquecrops.blocks.supercrops.*;
import com.bafomdad.uniquecrops.core.enums.EnumLily;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.LilyPadItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.block.AbstractBlock.Properties;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class UCBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, UniqueCrops.MOD_ID);
    public static final List<Block> CROPS = new ArrayList<>();

    /**
     * GENERAL
     */
    public static final RegistryObject<Block> ABSTRACT_BARREL = register("abstractbarrel", AbstractBarrelBlock::new);
    public static final RegistryObject<Block> BUCKET_ROPE = register("bucketrope", BucketRopeBlock::new);
    public static final RegistryObject<Block> CINDER_TORCH = register("cindertorch", CinderTorchBlock::new);
    public static final RegistryObject<Block> DARK_BLOCK = register("darkblock", () -> new Block(Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(6000000.0F)));
    public static final RegistryObject<Block> DRIED_THATCH = register("driedthatch", () -> new Block(Properties.create(Material.EARTH).sound(SoundType.PLANT).hardnessAndResistance(0.1F)));
    public static final RegistryObject<Block> EGG_BASKET = register("egg_basket", EggBasketBlock::new);
    public static final RegistryObject<Block> GOBLET = register("goblet", GobletBlock::new);
    public static final RegistryObject<Block> HOURGLASS = register("hourglass", HourglassBlock::new);
    public static final RegistryObject<Block> LILY_ENDER = registerLily("enderlily", () -> new BaseLilyBlock(EnumLily.ENDER));
    public static final RegistryObject<Block> LILY_ICE = registerLily("icelily", () -> new BaseLilyBlock(EnumLily.ICE));
    public static final RegistryObject<Block> LILY_JUNGLE = registerLily("junglelily", () -> new BaseLilyBlock(EnumLily.JUNGLE));
    public static final RegistryObject<Block> LILY_LAVA = registerLily("lavalily", () -> new BaseLilyBlock(EnumLily.LAVA));
    public static final RegistryObject<Block> NORMIECRATE = register("normiecrate", () -> new Block(Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(0.25F, 5.0F)));
    public static final RegistryObject<Block> OBTUSE_PLATFORM = register("obtuse_platform", ObtusePlatformBlock::new);
    public static final RegistryObject<Block> OLDCOBBLE = register("oldcobble", () -> new Block(Properties.from(Blocks.COBBLESTONE)));
    public static final RegistryObject<Block> OLDBRICK = register("oldbrick", () -> new Block(Properties.from(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> OLDCOBBLEMOSS = register("oldcobblemoss", () -> new Block(Properties.from(Blocks.MOSSY_COBBLESTONE)));
    public static final RegistryObject<Block> OLDDIAMOND = register("olddiamond", () -> new Block(Properties.from(Blocks.DIAMOND_BLOCK)));
    public static final RegistryObject<Block> OLDGOLD = register("oldgold", () -> new Block(Properties.from(Blocks.GOLD_BLOCK)));
    public static final RegistryObject<Block> OLDGRASS = register("oldgrass", () -> new GrassBlock(Properties.from(Blocks.GRASS_BLOCK)));
    public static final RegistryObject<Block> OLDGRAVEL = register("oldgravel", () -> new GravelBlock(Properties.from(Blocks.GRAVEL)));
    public static final RegistryObject<Block> OLDIRON = register("oldiron", () -> new Block(Properties.from(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> PRECISION_BLOCK = register("precision_block", () -> new Block(Properties.from(Blocks.DIAMOND_BLOCK)));
    public static final RegistryObject<Block> RUINEDBRICKS = register("ruinedbricks", () -> new Block(Properties.from(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> RUINEDBRICKSCARVED = register("ruinedbrickscarved", () -> new Block(Properties.from(Blocks.CHISELED_STONE_BRICKS)));
    public static final RegistryObject<Block> RUINEDBRICKSRED = register("ruinedbricksred", () -> new Block(Properties.from(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> HARVEST_TRAP = register("harvest_trap", HarvestTrapBlock::new);
    public static final RegistryObject<Block> CROP_PORTAL = register("crop_portal", CropPortalBlock::new, false);
    public static final RegistryObject<Block> DEMO_CORD = register("demo_cord", DemoCordBlock::new);
    public static final RegistryObject<Block> TOTEMHEAD = register("totemhead", TotemheadBlock::new);
    public static final RegistryObject<Block> SUN_DIAL = register("sun_dial", SundialBlock::new);
    public static final RegistryObject<Block> SUN_BLOCK = register("sun_block", SunBlock::new);
    public static final RegistryObject<Block> INVISIBILIA_GLASS = register("invisibilia_glass", InvisibiliaGlass::new);
    public static final RegistryObject<Block> FLYWOOD_LEAVES = register("flywood_leaves", () -> new LeavesBlock(Properties.from(Blocks.OAK_LEAVES)));
    public static final RegistryObject<Block> FLYWOOD_LOG = register("flywood_log", () -> new RotatedPillarBlock(Properties.from(Blocks.OAK_LOG)));
    public static final RegistryObject<Block> FLYWOOD_SAPLING = register("flywood_sapling", () -> new SaplingBlock(new UCFeatures.FlywoodTree(), Properties.from(Blocks.OAK_SAPLING)));
    public static final RegistryObject<Block> FLYWOOD_PLANKS = register("flywood_planks", () -> new Block(Properties.from(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> FLYWOOD_STAIRS = register("flywood_stairs", () -> new StairsBlock(() -> FLYWOOD_PLANKS.get().getDefaultState(), Properties.from(FLYWOOD_PLANKS.get())));
    public static final RegistryObject<Block> RUINEDBRICKS_STAIRS = register("ruinedbricks_stairs", () -> new StairsBlock(() -> RUINEDBRICKS.get().getDefaultState(), Properties.from(RUINEDBRICKS.get())));
    public static final RegistryObject<Block> FLYWOOD_SLAB = register("flywood_slab", () -> new SlabBlock(Properties.from(FLYWOOD_PLANKS.get())));
    public static final RegistryObject<Block> RUINEDBRICKS_SLAB = register("ruinedbricks_slab", () -> new SlabBlock(Properties.from(RUINEDBRICKS.get())));
    public static final RegistryObject<Block> RUINEDBRICKSCARVED_SLAB = register("ruinedbrickscarved_slab", () -> new SlabBlock(Properties.from(RUINEDBRICKSCARVED.get())));
    public static final RegistryObject<Block> ROSEWOOD_PLANKS = register("rosewood_planks", () -> new Block(Properties.from(FLYWOOD_PLANKS.get())));
    public static final RegistryObject<Block> ROSEWOOD_STAIRS = register("rosewood_stairs", () -> new StairsBlock(() -> ROSEWOOD_PLANKS.get().getDefaultState(), Properties.from(ROSEWOOD_PLANKS.get())));
    public static final RegistryObject<Block> ROSEWOOD_SLAB = register("rosewood_slab", () -> new SlabBlock(Properties.from(ROSEWOOD_PLANKS.get())));

    /**
     * CROPS
     */
    public static final RegistryObject<BaseCropsBlock> NORMAL_CROP = registerCrop("normal", Normal::new);
    public static final RegistryObject<BaseCropsBlock> ARTISIA_CROP = registerCrop("artisia", Artisia::new);
    public static final RegistryObject<BaseCropsBlock> PRECISION_CROP = registerCrop("precision", Precision::new);
    public static final RegistryObject<BaseCropsBlock> KNOWLEDGE_CROP = registerCrop("knowledge", Knowledge::new);
    public static final RegistryObject<BaseCropsBlock> DIRIGIBLE_CROP = registerCrop("dirigible", Dirigible::new);
    public static final RegistryObject<BaseCropsBlock> MILLENNIUM_CROP = registerCrop("millennium", Millennium::new);
    public static final RegistryObject<BaseCropsBlock> ENDERLILY_CROP = registerCrop("enderlily", Enderlily::new);
    public static final RegistryObject<BaseCropsBlock> COLLIS_CROP = registerCrop("collis", Collis::new);
    public static final RegistryObject<BaseCropsBlock> INVISIBILIA_CROP = registerCrop("invisibilia", Invisibilia::new);
    public static final RegistryObject<BaseCropsBlock> MARYJANE_CROP = registerCrop("maryjane", MaryJane::new);
    public static final RegistryObject<BaseCropsBlock> WEEPINGBELLS_CROP = registerCrop("weepingbells", WeepingBells::new);
    public static final RegistryObject<BaseCropsBlock> MUSICA_CROP = registerCrop("musica", Musica::new);
    public static final RegistryObject<BaseCropsBlock> CINDERBELLA_CROP = registerCrop("cinderbella", Cinderbella::new);
    public static final RegistryObject<BaseCropsBlock> MERLINIA_CROP = registerCrop("merlinia", Merlinia::new);
    public static final RegistryObject<BaseCropsBlock> EULA_CROP = registerCrop("eula", Eula::new);
    public static final RegistryObject<BaseCropsBlock> COBBLONIA_CROP = registerCrop("cobblonia", Cobblonia::new);
    public static final RegistryObject<BaseCropsBlock> DYEIUS_CROP = registerCrop("dyeius", Dyeius::new);
    public static final RegistryObject<BaseCropsBlock> ABSTRACT_CROP = registerCrop("abstract", Abstract::new);
    public static final RegistryObject<BaseCropsBlock> WAFFLONIA_CROP = registerCrop("wafflonia", Wafflonia::new);
    public static final RegistryObject<BaseCropsBlock> DEVILSNARE_CROP = registerCrop("devilsnare", DevilSnare::new);
    public static final RegistryObject<BaseCropsBlock> PIXELSIUS_CROP = registerCrop("pixelsius", () -> new BaseCropsBlock(UCItems.PIXELS, UCItems.PIXELSIUS_SEED));
    public static final RegistryObject<BaseCropsBlock> PETRAMIA_CROP = registerCrop("petramia", Petramia::new);
    public static final RegistryObject<BaseCropsBlock> MALLEATORIS_CROP = registerCrop("malleatoris", Malleatoris::new);
    public static final RegistryObject<BaseCropsBlock> IMPERIA_CROP = registerCrop("imperia", Imperia::new);
    public static final RegistryObject<BaseCropsBlock> LACUSIA_CROP = registerCrop("lacusia", Lacusia::new);
    public static final RegistryObject<BaseCropsBlock> HEXIS_CROP = registerCrop("hexis", Hexis::new);
    public static final RegistryObject<BaseCropsBlock> INDUSTRIA_CROP = registerCrop("industria", Industria::new);
    public static final RegistryObject<BaseCropsBlock> QUARRY_CROP = registerCrop("quarry", Fossura::new);
    public static final RegistryObject<BaseCropsBlock> DONUTSTEEL_CROP = registerCrop("donutsteel", DonutSteel::new);
    public static final RegistryObject<BaseCropsBlock> INSTABILIS_CROP = registerCrop("instabilis", Instabilis::new);
    public static final RegistryObject<BaseCropsBlock> SUCCO_CROP = registerCrop("succo", Succo::new);
    public static final RegistryObject<BaseCropsBlock> ADVENTUS_CROP = registerCrop("adventus", () -> new BaseCropsBlock(UCItems.GOODIE_BAG, UCItems.ADVENTUS_SEED));
    public static final RegistryObject<BaseCropsBlock> HOLY_CROP = registerCrop("holy", HolyCrop::new);
    public static final RegistryObject<BaseCropsBlock> MAGNES_CROP = registerCrop("magnes", Magnes::new);
    public static final RegistryObject<BaseCropsBlock> FEROXIA_CROP = registerCrop("feroxia", Feroxia::new);

    /**
     * SUPER CROPS
     */
    public static final RegistryObject<Block> STALK = register("stalk", StalkBlock::new, false);
    public static final RegistryObject<Block> EXEDO = register("exedo", Exedo::new, false);
    public static final RegistryObject<Block> COCITO = register("cocito", Cocito::new, false);
    public static final RegistryObject<Block> ITERO = register("itero", Itero::new, false);
    public static final RegistryObject<Block> FASCINO = register("fascino", Fascino::new, false);
    public static final RegistryObject<Block> WEATHERFLESIA = register("weatherflesia", Weatherflesia::new, false);
    public static final RegistryObject<Block> LIGNATOR = register("lignator", Lignator::new, false);

    public static final RegistryObject<BaseCropsBlock> DUMMY_CROP = registerCrop("dummy", () -> new BaseCropsBlock(null, null));

    public static <B extends BaseCropsBlock> RegistryObject<B> registerCrop(String name, Supplier<? extends B> supplier) {

        return BLOCKS.register("crop_" + name, supplier);
    }

    public static <B extends Block> RegistryObject<B> registerLily(String name, Supplier<? extends B> supplier) {

        RegistryObject<B> block = BLOCKS.register(name, supplier);
        UCItems.ITEMS.register(name, () -> new LilyPadItem(block.get(), UCItems.defaultBuilder()));

        return block;
    }

    private static <B extends Block> RegistryObject<B> register(String name, Supplier<? extends B> supplier) {

        return register(name, supplier, true);
    }

    private static <B extends Block> RegistryObject<B> register(String name, Supplier<? extends B> supplier, boolean itemBlock) {

        RegistryObject<B> block = BLOCKS.register(name, supplier);
        if (itemBlock)
            UCItems.ITEMS.register(name, () -> new BlockItem(block.get(), UCItems.defaultBuilder()));

        return block;
    }
}
