package com.bafomdad.uniquecrops.data;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.blocks.StalkBlock;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.*;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.loot.functions.ExplosionDecay;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class UCLootProvider implements IDataProvider {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final ILootCondition.IBuilder SILK_TOUCH = MatchTool.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));

    private final DataGenerator gen;
    private final Map<Block, Function<Block, LootTable.Builder>> functionTable = new HashMap<>();

    public UCLootProvider(DataGenerator gen) {

        this.gen = gen;
        functionTable.put(UCBlocks.STALK.get(), (block) -> LootTable.builder()
                .addLootPool(LootPool.builder()
                .addEntry(ItemLootEntry.builder(Blocks.CRAFTING_TABLE)
                .acceptFunction(SetCount.builder(ConstantRange.of(1)))
                .acceptCondition(BlockStateProperty.builder(block).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withStringProp(StalkBlock.STALKS, "up")))
                ))
        );
        functionTable.put(UCBlocks.ARTISIA_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.PRECISION_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.KNOWLEDGE_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.DIRIGIBLE_CROP.get(), UCLootProvider::genCropsWithBonus);
        functionTable.put(UCBlocks.MILLENNIUM_CROP.get(), UCLootProvider::genCropsWithBonus);
        functionTable.put(UCBlocks.ENDERLILY_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.COLLIS_CROP.get(), UCLootProvider::genCropsWithBonus);
        functionTable.put(UCBlocks.INVISIBILIA_CROP.get(), UCLootProvider::genCropsWithBonus);
        functionTable.put(UCBlocks.MARYJANE_CROP.get(), UCLootProvider::genCropsWithBonus);
        functionTable.put(UCBlocks.WEEPINGBELLS_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.MUSICA_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.EULA_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.COBBLONIA_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.ABSTRACT_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.WAFFLONIA_CROP.get(), UCLootProvider::genCropsWithBonus);
        functionTable.put(UCBlocks.DEVILSNARE_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.PIXELSIUS_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.PETRAMIA_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.MALLEATORIS_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.IMPERIA_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.LACUSIA_CROP.get(), UCLootProvider::genCropsWithBonus);
        functionTable.put(UCBlocks.HEXIS_CROP.get(), UCLootProvider::genCropsWithBonus);
        functionTable.put(UCBlocks.INDUSTRIA_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.QUARRY_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.DONUTSTEEL_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.INSTABILIS_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.SUCCO_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.ADVENTUS_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.HOLY_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.MAGNES_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.FEROXIA_CROP.get(), UCLootProvider::genCropsWithBonus);
        functionTable.put(UCBlocks.NORMAL_CROP.get(), (block) -> LootTable.builder()
                .addLootPool(LootPool.builder()
                        .rolls(ConstantRange.of(1))
                        .addEntry(AlternativesLootEntry.builder(TagLootEntry.getBuilder(UCItemTagsProvider.NORMAL_DROP)
                        .acceptCondition(fullyGrown(block)))))
                .addLootPool(seed(UCItems.NORMAL_SEED.get()))
        );
        functionTable.put(UCBlocks.INVISIBILIA_GLASS.get(), UCLootProvider::genSilk);
        functionTable.put(UCBlocks.FLYWOOD_SLAB.get(), UCLootProvider::genSlab);
        functionTable.put(UCBlocks.ROSEWOOD_SLAB.get(), UCLootProvider::genSlab);
        functionTable.put(UCBlocks.RUINEDBRICKS_SLAB.get(), UCLootProvider::genSlab);
        functionTable.put(UCBlocks.RUINEDBRICKSCARVED_SLAB.get(), UCLootProvider::genSlab);
        functionTable.put(UCBlocks.DYEIUS_CROP.get(), (block) -> LootTable.builder()
                .addLootPool(LootPool.builder()
                        .acceptCondition(fullyGrown(block))
                        .addEntry(ItemLootEntry.builder(Items.WHITE_DYE)
                                .acceptFunction(SetCount.builder(ConstantRange.of(1)))
                                .acceptCondition(new TimeCheckBuilder(24000L, RandomValueRange.of(0, 1500))))
                        .addEntry(ItemLootEntry.builder(Items.ORANGE_DYE)
                                .acceptFunction(SetCount.builder(ConstantRange.of(1)))
                                .acceptCondition(new TimeCheckBuilder(24000L, RandomValueRange.of(1501, 3000))))
                        .addEntry(ItemLootEntry.builder(Items.MAGENTA_DYE)
                                .acceptFunction(SetCount.builder(ConstantRange.of(1)))
                                .acceptCondition(new TimeCheckBuilder(24000L, RandomValueRange.of(3001, 4500))))
                        .addEntry(ItemLootEntry.builder(Items.LIGHT_BLUE_DYE)
                                .acceptFunction(SetCount.builder(ConstantRange.of(1)))
                                .acceptCondition(new TimeCheckBuilder(24000L, RandomValueRange.of(4501, 6000))))
                        .addEntry(ItemLootEntry.builder(Items.YELLOW_DYE)
                                .acceptFunction(SetCount.builder(ConstantRange.of(1)))
                                .acceptCondition(new TimeCheckBuilder(24000L, RandomValueRange.of(6001, 7500))))
                        .addEntry(ItemLootEntry.builder(Items.LIME_DYE)
                                .acceptFunction(SetCount.builder(ConstantRange.of(1)))
                                .acceptCondition(new TimeCheckBuilder(24000L, RandomValueRange.of(7501, 9000))))
                        .addEntry(ItemLootEntry.builder(Items.PINK_DYE)
                                .acceptFunction(SetCount.builder(ConstantRange.of(1)))
                                .acceptCondition(new TimeCheckBuilder(24000L, RandomValueRange.of(9001, 10500))))
                        .addEntry(ItemLootEntry.builder(Items.GRAY_DYE)
                                .acceptFunction(SetCount.builder(ConstantRange.of(1)))
                                .acceptCondition(new TimeCheckBuilder(24000L, RandomValueRange.of(10501, 12000))))
                        .addEntry(ItemLootEntry.builder(Items.LIGHT_GRAY_DYE)
                                .acceptFunction(SetCount.builder(ConstantRange.of(1)))
                                .acceptCondition(new TimeCheckBuilder(24000L, RandomValueRange.of(12001, 13500))))
                        .addEntry(ItemLootEntry.builder(Items.CYAN_DYE)
                                .acceptFunction(SetCount.builder(ConstantRange.of(1)))
                                .acceptCondition(new TimeCheckBuilder(24000L, RandomValueRange.of(13501, 15000))))
                        .addEntry(ItemLootEntry.builder(Items.PURPLE_DYE)
                                .acceptFunction(SetCount.builder(ConstantRange.of(1)))
                                .acceptCondition(new TimeCheckBuilder(24000L, RandomValueRange.of(15001, 16500))))
                        .addEntry(ItemLootEntry.builder(Items.BLUE_DYE)
                                .acceptFunction(SetCount.builder(ConstantRange.of(1)))
                                .acceptCondition(new TimeCheckBuilder(24000L, RandomValueRange.of(16501, 18000))))
                        .addEntry(ItemLootEntry.builder(Items.BROWN_DYE)
                                .acceptFunction(SetCount.builder(ConstantRange.of(1)))
                                .acceptCondition(new TimeCheckBuilder(24000L, RandomValueRange.of(18001, 19500))))
                        .addEntry(ItemLootEntry.builder(Items.GREEN_DYE)
                                .acceptFunction(SetCount.builder(ConstantRange.of(1)))
                                .acceptCondition(new TimeCheckBuilder(24000L, RandomValueRange.of(19501, 21000))))
                        .addEntry(ItemLootEntry.builder(Items.RED_DYE)
                                .acceptFunction(SetCount.builder(ConstantRange.of(1)))
                                .acceptCondition(new TimeCheckBuilder(24000L, RandomValueRange.of(21001, 22500))))
                        .addEntry(ItemLootEntry.builder(Items.BLACK_DYE)
                                .acceptFunction(SetCount.builder(ConstantRange.of(1)))
                                .acceptCondition(new TimeCheckBuilder(24000L, RandomValueRange.of(22501, 23999))))
        )
        .addLootPool(seed(UCItems.DYEIUS_SEED.get())));
    }

    @Override
    public void act(DirectoryCache cache) throws IOException {

        Map<ResourceLocation, LootTable.Builder> tables = new HashMap<>();

        for (Block block : Registry.BLOCK) {
            ResourceLocation id = Registry.BLOCK.getKey(block);

            if (!id.getNamespace().equals(UniqueCrops.MOD_ID)) continue;
            if (block == UCBlocks.FLYWOOD_LEAVES.get()) continue;
            if (block == UCBlocks.DUMMY_CROP.get()) continue;

//            if (block instanceof BaseCropsBlock && (block != UCBlocks.DYEIUS_CROP.get() || block != UCBlocks.NORMAL_CROP.get())) {
//                if (((BaseCropsBlock)block).isIncludeSeed())
//                    functionTable.put(block, UCLootProvider::genCropsWithBonus);
//                else
//                    functionTable.put(block, UCLootProvider::genCrops);
//            }
            Function<Block, LootTable.Builder> func = functionTable.getOrDefault(block, UCLootProvider::genRegular);
            tables.put(id, func.apply(block));
        }
        for (Map.Entry<ResourceLocation, LootTable.Builder> e : tables.entrySet()) {
            Path path = getPath(gen.getOutputFolder(), e.getKey());
            IDataProvider.save(GSON, cache, LootTableManager.toJson(e.getValue().setParameterSet(LootParameterSets.BLOCK).build()), path);
        }
    }

    private static Path getPath(Path root, ResourceLocation id) {

        return root.resolve("data/" + id.getNamespace() + "/loot_tables/blocks/" + id.getPath() + ".json");
    }

    private static LootTable.Builder genCrops(Block block) {

        ILootCondition.IBuilder condition = BlockStateProperty.builder(block).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(BaseCropsBlock.AGE, ((BaseCropsBlock)block).getHarvestAge()));
        BaseCropsBlock crop = (BaseCropsBlock)block;

        LootPool.Builder cropPool = LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(crop.getCrop()))
                .acceptCondition(condition);
        LootPool.Builder seedPool = LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(crop.getSeed()))
                .acceptCondition(SurvivesExplosion.builder());

        return LootTable.builder().addLootPool(cropPool).addLootPool(seedPool);
    }

    private static LootTable.Builder genCropsWithBonus(Block block) {

        ILootCondition.IBuilder condition = BlockStateProperty.builder(block).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(BaseCropsBlock.AGE, ((BaseCropsBlock)block).getHarvestAge()));
        BaseCropsBlock crop = (BaseCropsBlock)block;

        LootTable.Builder builder = LootTable.builder()
                .addLootPool(LootPool.builder()
                .addEntry(ItemLootEntry.builder(crop.getSeed())
                        .acceptCondition(condition)
                        .alternatively(ItemLootEntry.builder(crop.getCrop()))))
                .addLootPool(LootPool.builder().acceptCondition(condition)
                 .addEntry(ItemLootEntry.builder(crop.getCrop())
                        .acceptFunction(ApplyBonus.binomialWithBonusCount(Enchantments.FORTUNE, 0.5714286F, 1)))
                        .acceptCondition(condition));

        return builder;
    }

    private static LootTable.Builder droppingAndBonusWhen(Block block, Item itemConditional, Item withBonus, ILootCondition.IBuilder conditionBuilder) {

        return LootTable.builder().addLootPool(LootPool.builder().addEntry(ItemLootEntry.builder(itemConditional).acceptCondition(conditionBuilder).alternatively(ItemLootEntry.builder(withBonus)))).addLootPool(LootPool.builder().acceptCondition(conditionBuilder).addEntry(ItemLootEntry.builder(withBonus).acceptFunction(ApplyBonus.binomialWithBonusCount(Enchantments.FORTUNE, 0.5714286F, 1))));
    }

    private static LootTable.Builder genSlab(Block block) {

        LootEntry.Builder<?> entry = ItemLootEntry.builder(block)
                .acceptFunction(SetCount.builder(ConstantRange.of(2))
                        .acceptCondition(BlockStateProperty.builder(block).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(SlabBlock.TYPE, SlabType.DOUBLE))))
                .acceptFunction(ExplosionDecay.builder());
        return LootTable.builder().addLootPool(LootPool.builder().name("main").rolls(ConstantRange.of(1)).addEntry(entry));
    }

    private static LootTable.Builder genRegular(Block block) {

        LootEntry.Builder<?> entry = ItemLootEntry.builder(block);
        LootPool.Builder pool = LootPool.builder().name("main").rolls(ConstantRange.of(1)).addEntry(entry)
                .acceptCondition(SurvivesExplosion.builder());
        return LootTable.builder().addLootPool(pool);
    }

    private static LootTable.Builder genSilk(Block block) {

        return LootTable.builder().addLootPool(LootPool.builder().acceptCondition(SILK_TOUCH).rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(block)));
    }

    private static LootPool.Builder seed(Item item) {

        return LootPool.builder().addEntry(ItemLootEntry.builder(item).acceptFunction(ApplyBonus.binomialWithBonusCount(Enchantments.FORTUNE, 0.5714286F, 3))).acceptCondition(SurvivesExplosion.builder());
    }

    private static ILootCondition.IBuilder fullyGrown(Block block) {

        return BlockStateProperty.builder(block).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(BaseCropsBlock.AGE, ((BaseCropsBlock)block).getHarvestAge()));
    }

    @Override
    public String getName() {

        return "Unique crops block loot tables";
    }

    private static class TimeCheckBuilder implements ILootCondition.IBuilder {

        private final Long mod;
        private final RandomValueRange range;

        public TimeCheckBuilder(Long mod, RandomValueRange range) {

            this.mod = mod;
            this.range = range;
        }

        @Override
        public ILootCondition build() {

            return this.create(this.mod, this.range);
        }

        private TimeCheck create(Long mod, RandomValueRange range) {

            try {
                Constructor<TimeCheck> ctor = TimeCheck.class.getDeclaredConstructor(Long.class, RandomValueRange.class);
                ctor.setAccessible(true);
                TimeCheck check = ctor.newInstance(mod, range);
                ctor.setAccessible(false);
                return check;
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
