package com.bafomdad.uniquecrops.init;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.tiles.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class UCTiles {

    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, UniqueCrops.MOD_ID);

    public static final RegistryObject<BlockEntityType<TileArtisia>> ARTISIA = register("artisia", TileArtisia::new, set(UCBlocks.ARTISIA_CROP));
    public static final RegistryObject<BlockEntityType<TileFeroxia>> FEROXIA = register("feroxia", TileFeroxia::new, set(UCBlocks.FEROXIA_CROP));
    public static final RegistryObject<BlockEntityType<TileGoblet>> GOBLET = register("goblet", TileGoblet::new, set(UCBlocks.GOBLET));
    public static final RegistryObject<BlockEntityType<TileSunBlock>> SUNTILE = register("suntile", TileSunBlock::new, set(UCBlocks.SUN_BLOCK));
    public static final RegistryObject<BlockEntityType<TileSundial>> SUNDIAL = register("sundial", TileSundial::new, set(UCBlocks.SUN_DIAL));
    public static final RegistryObject<BlockEntityType<TileFascino>> FASCINO = register("fascino", TileFascino::new, set(UCBlocks.FASCINO));
    public static final RegistryObject<BlockEntityType<TileDigger>> QUARRY = register("digger", TileDigger::new, set(UCBlocks.QUARRY_CROP));
    public static final RegistryObject<BlockEntityType<TileMillennium>> MILLENNIUM = register("millennium", TileMillennium::new, set(UCBlocks.MILLENNIUM_CROP));
    public static final RegistryObject<BlockEntityType<TileBarrel>> BARREL = register("abstractbarrel", TileBarrel::new, set(UCBlocks.ABSTRACT_BARREL));
    public static final RegistryObject<BlockEntityType<TileWeatherflesia>> WEATHERFLESIA = register("weatherflesia", TileWeatherflesia::new, set(UCBlocks.WEATHERFLESIA));
    public static final RegistryObject<BlockEntityType<TileCraftyPlant>> CRAFTYPLANT = register("craftyplant", TileCraftyPlant::new, set(UCBlocks.STALK));
    public static final RegistryObject<BlockEntityType<TileLacusia>> LACUSIA = register("lacusia", TileLacusia::new, set(UCBlocks.LACUSIA_CROP));
    public static final RegistryObject<BlockEntityType<TileItero>> ITERO = register("itero", TileItero::new, set(UCBlocks.ITERO));
    public static final RegistryObject<BlockEntityType<TileExedo>> EXEDO = register("exedo", TileExedo::new, set(UCBlocks.EXEDO));
    public static final RegistryObject<BlockEntityType<TileWeepingBells>> WEEPINGBELLS = register("weepingbells", TileWeepingBells::new, set(UCBlocks.WEEPINGBELLS_CROP));
    public static final RegistryObject<BlockEntityType<TileIndustria>> INDUSTRIA = register("industria", TileIndustria::new, set(UCBlocks.INDUSTRIA_CROP));
    public static final RegistryObject<BlockEntityType<TileSucco>> SUCCO = register("succo", TileSucco::new, set(UCBlocks.SUCCO_CROP));
    public static final RegistryObject<BlockEntityType<TileMusica>> MUSICA = register("musica", TileMusica::new, set(UCBlocks.MUSICA_CROP));
    public static final RegistryObject<BlockEntityType<TileHarvestTrap>> HARVESTTRAP = register("harvesttrap", TileHarvestTrap::new, set(UCBlocks.HARVEST_TRAP));

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String id, BlockEntityType.BlockEntitySupplier<T> factory, Supplier<Block[]> blocks) {

        return TILES.register(id, () -> BlockEntityType.Builder.of(factory, blocks.get()).build(null));
    }

    private static <B extends Block> Supplier<Block[]> set(RegistryObject<? extends B> blockGetter) {

        return () -> new Block[] { blockGetter.get() };
    }
}
