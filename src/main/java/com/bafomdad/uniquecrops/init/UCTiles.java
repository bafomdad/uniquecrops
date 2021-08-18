package com.bafomdad.uniquecrops.init;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.tiles.*;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class UCTiles {

    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, UniqueCrops.MOD_ID);

    public static final RegistryObject<TileEntityType<TileArtisia>> ARTISIA = register("artisia", TileArtisia::new, set(UCBlocks.ARTISIA_CROP));
    public static final RegistryObject<TileEntityType<TileFeroxia>> FEROXIA = register("feroxia", TileFeroxia::new, set(UCBlocks.FEROXIA_CROP));
    public static final RegistryObject<TileEntityType<TileGoblet>> GOBLET = register("goblet", TileGoblet::new, set(UCBlocks.GOBLET));
    public static final RegistryObject<TileEntityType<TileSunBlock>> SUNTILE = register("suntile", TileSunBlock::new, set(UCBlocks.SUN_BLOCK));
    public static final RegistryObject<TileEntityType<TileSundial>> SUNDIAL = register("sundial", TileSundial::new, set(UCBlocks.SUN_DIAL));
    public static final RegistryObject<TileEntityType<TileFascino>> FASCINO = register("fascino", TileFascino::new, set(UCBlocks.FASCINO));
    public static final RegistryObject<TileEntityType<TileDigger>> QUARRY = register("digger", TileDigger::new, set(UCBlocks.QUARRY_CROP));
    public static final RegistryObject<TileEntityType<TileMillennium>> MILLENNIUM = register("millennium", TileMillennium::new, set(UCBlocks.MILLENNIUM_CROP));
    public static final RegistryObject<TileEntityType<TileBarrel>> BARREL = register("abstractbarrel", TileBarrel::new, set(UCBlocks.ABSTRACT_BARREL));
    public static final RegistryObject<TileEntityType<TileWeatherflesia>> WEATHERFLESIA = register("weatherflesia", TileWeatherflesia::new, set(UCBlocks.WEATHERFLESIA));
    public static final RegistryObject<TileEntityType<TileCraftyPlant>> CRAFTYPLANT = register("craftyplant", TileCraftyPlant::new, set(UCBlocks.STALK));
    public static final RegistryObject<TileEntityType<TileLacusia>> LACUSIA = register("lacusia", TileLacusia::new, set(UCBlocks.LACUSIA_CROP));
    public static final RegistryObject<TileEntityType<TileItero>> ITERO = register("itero", TileItero::new, set(UCBlocks.ITERO));
    public static final RegistryObject<TileEntityType<TileExedo>> EXEDO = register("exedo", TileExedo::new, set(UCBlocks.EXEDO));
    public static final RegistryObject<TileEntityType<TileWeepingBells>> WEEPINGBELLS = register("weepingbells", TileWeepingBells::new, set(UCBlocks.WEEPINGBELLS_CROP));
    public static final RegistryObject<TileEntityType<TileIndustria>> INDUSTRIA = register("industria", TileIndustria::new, set(UCBlocks.INDUSTRIA_CROP));
    public static final RegistryObject<TileEntityType<TileSucco>> SUCCO = register("succo", TileSucco::new, set(UCBlocks.SUCCO_CROP));
    public static final RegistryObject<TileEntityType<TileMusica>> MUSICA = register("musica", TileMusica::new, set(UCBlocks.MUSICA_CROP));
    public static final RegistryObject<TileEntityType<TileHarvestTrap>> HARVESTTRAP = register("harvesttrap", TileHarvestTrap::new, set(UCBlocks.HARVEST_TRAP));

    private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String id, Supplier<T> factory, Supplier<Block[]> blocks) {

        return TILES.register(id, () -> TileEntityType.Builder.of(factory, blocks.get()).build(null));
    }

    private static <B extends Block> Supplier<Block[]> set(RegistryObject<? extends B> blockGetter) {

        return () -> new Block[] { blockGetter.get() };
    }
}
