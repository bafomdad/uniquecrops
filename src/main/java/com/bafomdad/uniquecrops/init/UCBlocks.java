package com.bafomdad.uniquecrops.init;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.*;
import com.bafomdad.uniquecrops.blocks.crops.*;
import com.bafomdad.uniquecrops.blocks.supercrops.*;
import com.bafomdad.uniquecrops.core.enums.EnumLily;
import com.bafomdad.uniquecrops.items.base.ItemBlockUC;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.WaterLilyBlockItem;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.GravelBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;

public class UCBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, UniqueCrops.MOD_ID);
    public static final List<Block> CROPS = new ArrayList<>();

    /**
     * GENERAL
     */
    public static final RegistryObject<Block> ABSTRACT_BARREL = register("abstractbarrel", AbstractBarrelBlock::new);
    public static final RegistryObject<Block> BUCKET_ROPE = register("bucketrope", BucketRopeBlock::new);
    public static final RegistryObject<Block> CINDER_TORCH = register("cindertorch", CinderTorchBlock::new);
    public static final RegistryObject<Block> DARK_BLOCK = register("darkblock", () -> new Block(Properties.of(Material.STONE).sound(SoundType.STONE).strength(6000000.0F)));
    public static final RegistryObject<Block> DRIED_THATCH = register("driedthatch", () -> new Block(Properties.of(Material.DIRT).sound(SoundType.GRASS).strength(0.1F)));
    public static final RegistryObject<Block> EGG_BASKET = register("egg_basket", EggBasketBlock::new);
    public static final RegistryObject<Block> GOBLET = register("goblet", GobletBlock::new);
    public static final RegistryObject<Block> HOURGLASS = register("hourglass", HourglassBlock::new, true, true);
    public static final RegistryObject<Block> LILY_ENDER = registerLily("enderlily", () -> new BaseLilyBlock(EnumLily.ENDER));
    public static final RegistryObject<Block> LILY_ICE = registerLily("icelily", () -> new BaseLilyBlock(EnumLily.ICE));
    public static final RegistryObject<Block> LILY_JUNGLE = registerLily("junglelily", () -> new BaseLilyBlock(EnumLily.JUNGLE));
    public static final RegistryObject<Block> LILY_LAVA = registerLily("lavalily", () -> new BaseLilyBlock(EnumLily.LAVA));
    public static final RegistryObject<Block> NORMIECRATE = register("normiecrate", () -> new Block(Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(0.25F, 5.0F)));
    public static final RegistryObject<Block> OBTUSE_PLATFORM = register("obtuse_platform", ObtusePlatformBlock::new);
    public static final RegistryObject<Block> OLDCOBBLE = register("oldcobble", () -> new Block(Properties.copy(Blocks.COBBLESTONE)));
    public static final RegistryObject<Block> OLDBRICK = register("oldbrick", () -> new Block(Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> OLDCOBBLEMOSS = register("oldcobblemoss", () -> new Block(Properties.copy(Blocks.MOSSY_COBBLESTONE)));
    public static final RegistryObject<Block> OLDDIAMOND = register("olddiamond", () -> new Block(Properties.copy(Blocks.DIAMOND_BLOCK)));
    public static final RegistryObject<Block> OLDGOLD = register("oldgold", () -> new Block(Properties.copy(Blocks.GOLD_BLOCK)));
    public static final RegistryObject<Block> OLDGRASS = register("oldgrass", () -> new GrassBlock(Properties.copy(Blocks.GRASS_BLOCK)));
    public static final RegistryObject<Block> OLDGRAVEL = register("oldgravel", () -> new GravelBlock(Properties.copy(Blocks.GRAVEL)));
    public static final RegistryObject<Block> OLDIRON = register("oldiron", () -> new Block(Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> PRECISION_BLOCK = register("precision_block", () -> new Block(Properties.copy(Blocks.DIAMOND_BLOCK)));
    public static final RegistryObject<Block> RUINEDBRICKS = register("ruinedbricks", () -> new Block(Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> RUINEDBRICKSCARVED = register("ruinedbrickscarved", () -> new Block(Properties.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final RegistryObject<Block> RUINEDBRICKSRED = register("ruinedbricksred", () -> new RotatedPillarBlock(Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> DREAMCATCHER = register("dreamcatcher", DreamcatcherBlock::new);
    public static final RegistryObject<Block> HARVEST_TRAP = register("harvest_trap", HarvestTrapBlock::new);
    public static final RegistryObject<Block> CROP_PORTAL = register("crop_portal", CropPortalBlock::new, false, false);
    public static final RegistryObject<Block> DEMO_CORD = register("demo_cord", DemoCordBlock::new);
    public static final RegistryObject<Block> TOTEMHEAD = register("totemhead", TotemheadBlock::new);
    public static final RegistryObject<Block> SUN_DIAL = register("sun_dial", SundialBlock::new);
    public static final RegistryObject<Block> SUN_BLOCK = register("sun_block", SunBlock::new);
    public static final RegistryObject<Block> INVISIBILIA_GLASS = register("invisibilia_glass", InvisibiliaGlass::new);
    public static final RegistryObject<Block> FLYWOOD_LEAVES = register("flywood_leaves", () -> new LeavesBlock(Properties.copy(Blocks.OAK_LEAVES)));
    public static final RegistryObject<Block> FLYWOOD_LOG = register("flywood_log", () -> new RotatedPillarBlock(Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<Block> FLYWOOD_SAPLING = register("flywood_sapling", () -> new SaplingBlock(new UCFeatures.FlywoodTreeGrower(), Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistryObject<Block> FLYWOOD_PLANKS = register("flywood_planks", () -> new Block(Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> FLYWOOD_STAIRS = register("flywood_stairs", () -> new StairBlock(() -> FLYWOOD_PLANKS.get().defaultBlockState(), Properties.copy(FLYWOOD_PLANKS.get())));
    public static final RegistryObject<Block> RUINEDBRICKS_STAIRS = register("ruinedbricks_stairs", () -> new StairBlock(() -> RUINEDBRICKS.get().defaultBlockState(), Properties.copy(RUINEDBRICKS.get())));
    public static final RegistryObject<Block> FLYWOOD_SLAB = register("flywood_slab", () -> new SlabBlock(Properties.copy(FLYWOOD_PLANKS.get())));
    public static final RegistryObject<Block> RUINEDBRICKS_SLAB = register("ruinedbricks_slab", () -> new SlabBlock(Properties.copy(RUINEDBRICKS.get())));
    public static final RegistryObject<Block> RUINEDBRICKSCARVED_SLAB = register("ruinedbrickscarved_slab", () -> new SlabBlock(Properties.copy(RUINEDBRICKSCARVED.get())));
    public static final RegistryObject<Block> ROSEWOOD_PLANKS = register("rosewood_planks", () -> new Block(Properties.copy(FLYWOOD_PLANKS.get())));
    public static final RegistryObject<Block> ROSEWOOD_STAIRS = register("rosewood_stairs", () -> new StairBlock(() -> ROSEWOOD_PLANKS.get().defaultBlockState(), Properties.copy(ROSEWOOD_PLANKS.get())));
    public static final RegistryObject<Block> ROSEWOOD_SLAB = register("rosewood_slab", () -> new SlabBlock(Properties.copy(ROSEWOOD_PLANKS.get())));
    public static final RegistryObject<Block> FLYWOOD_TRAPDOOR = register("flywood_trapdoor", () -> new TrapDoorBlock(Properties.copy(Blocks.OAK_TRAPDOOR)));
    public static final RegistryObject<Block> ROSEWOOD_TRAPDOOR = register("rosewood_trapdoor", () -> new TrapDoorBlock(Properties.copy(Blocks.OAK_TRAPDOOR)));

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
    public static final RegistryObject<BaseCropsBlock> ADVENTUS_CROP = registerCrop("adventus", Adventus::new);
    public static final RegistryObject<BaseCropsBlock> HOLY_CROP = registerCrop("holy", HolyCrop::new);
    public static final RegistryObject<BaseCropsBlock> MAGNES_CROP = registerCrop("magnes", Magnes::new);
    public static final RegistryObject<BaseCropsBlock> FEROXIA_CROP = registerCrop("feroxia", Feroxia::new);

    /**
     * SUPER CROPS
     */
    public static final RegistryObject<Block> STALK = register("stalk", StalkBlock::new, false, false);
    public static final RegistryObject<Block> EXEDO = register("exedo", Exedo::new, false, false);
    public static final RegistryObject<Block> COCITO = register("cocito", Cocito::new, false, false);
    public static final RegistryObject<Block> ITERO = register("itero", Itero::new, false, false);
    public static final RegistryObject<Block> FASCINO = register("fascino", Fascino::new, false, false);
    public static final RegistryObject<Block> WEATHERFLESIA = register("weatherflesia", Weatherflesia::new, false, false);
    public static final RegistryObject<Block> LIGNATOR = register("lignator", Lignator::new, false, false);
    public static final RegistryObject<Block> SANALIGHT = register("sanalight", Sanalight::new, false, false);
    public static final RegistryObject<Block> FUNNY_LIGHT = register("funnylight", FunnyLightBlock::new, false, false);

    public static final RegistryObject<BaseCropsBlock> DUMMY_CROP = registerCrop("dummy", () -> new BaseCropsBlock(null, null));

    public static <B extends BaseCropsBlock> RegistryObject<B> registerCrop(String name, Supplier<? extends B> supplier) {

        return BLOCKS.register("crop_" + name, supplier);
    }

    public static <B extends Block> RegistryObject<B> registerLily(String name, Supplier<? extends B> supplier) {

        RegistryObject<B> block = BLOCKS.register(name, supplier);
        UCItems.ITEMS.register(name, () -> new WaterLilyBlockItem(block.get(), UCItems.defaultBuilder()));

        return block;
    }

    private static <B extends Block> RegistryObject<B> register(String name, Supplier<? extends B> supplier) {

        return register(name, supplier, true, false);
    }

    private static <B extends Block> RegistryObject<B> register(String name, Supplier<? extends B> supplier, boolean itemBlock, boolean custom) {

        RegistryObject<B> block = BLOCKS.register(name, supplier);
        if (itemBlock) {
            if (!custom)
                UCItems.ITEMS.register(name, () -> new BlockItem(block.get(), UCItems.defaultBuilder()));
            else
                UCItems.ITEMS.register(name, () -> new ItemBlockUC(block.get()));
        }
        return block;
    }
}
