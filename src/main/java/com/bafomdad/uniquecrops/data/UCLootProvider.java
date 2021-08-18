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
    private static final ILootCondition.IBuilder SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));

    private final DataGenerator gen;
    private final Map<Block, Function<Block, LootTable.Builder>> functionTable = new HashMap<>();

    public UCLootProvider(DataGenerator gen) {

        this.gen = gen;
        functionTable.put(UCBlocks.STALK.get(), (block) -> LootTable.lootTable()
                .withPool(LootPool.lootPool()
                .add(ItemLootEntry.lootTableItem(Blocks.CRAFTING_TABLE)
                .apply(SetCount.setCount(ConstantRange.exactly(1)))
                .when(BlockStateProperty.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StalkBlock.STALKS, "up")))
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
        functionTable.put(UCBlocks.NORMAL_CROP.get(), (block) -> LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(AlternativesLootEntry.alternatives(TagLootEntry.expandTag(UCItemTagsProvider.NORMAL_DROP)
                        .when(fullyGrown(block)))))
                .withPool(seed(UCItems.NORMAL_SEED.get()))
        );
        functionTable.put(UCBlocks.INVISIBILIA_GLASS.get(), UCLootProvider::genSilk);
        functionTable.put(UCBlocks.FLYWOOD_SLAB.get(), UCLootProvider::genSlab);
        functionTable.put(UCBlocks.ROSEWOOD_SLAB.get(), UCLootProvider::genSlab);
        functionTable.put(UCBlocks.RUINEDBRICKS_SLAB.get(), UCLootProvider::genSlab);
        functionTable.put(UCBlocks.RUINEDBRICKSCARVED_SLAB.get(), UCLootProvider::genSlab);
        functionTable.put(UCBlocks.DYEIUS_CROP.get(), (block) -> LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .when(fullyGrown(block))
                        .add(ItemLootEntry.lootTableItem(Items.WHITE_DYE)
                                .apply(SetCount.setCount(ConstantRange.exactly(1)))
                                .when(new TimeCheckBuilder(24000L, RandomValueRange.between(0, 1500))))
                        .add(ItemLootEntry.lootTableItem(Items.ORANGE_DYE)
                                .apply(SetCount.setCount(ConstantRange.exactly(1)))
                                .when(new TimeCheckBuilder(24000L, RandomValueRange.between(1501, 3000))))
                        .add(ItemLootEntry.lootTableItem(Items.MAGENTA_DYE)
                                .apply(SetCount.setCount(ConstantRange.exactly(1)))
                                .when(new TimeCheckBuilder(24000L, RandomValueRange.between(3001, 4500))))
                        .add(ItemLootEntry.lootTableItem(Items.LIGHT_BLUE_DYE)
                                .apply(SetCount.setCount(ConstantRange.exactly(1)))
                                .when(new TimeCheckBuilder(24000L, RandomValueRange.between(4501, 6000))))
                        .add(ItemLootEntry.lootTableItem(Items.YELLOW_DYE)
                                .apply(SetCount.setCount(ConstantRange.exactly(1)))
                                .when(new TimeCheckBuilder(24000L, RandomValueRange.between(6001, 7500))))
                        .add(ItemLootEntry.lootTableItem(Items.LIME_DYE)
                                .apply(SetCount.setCount(ConstantRange.exactly(1)))
                                .when(new TimeCheckBuilder(24000L, RandomValueRange.between(7501, 9000))))
                        .add(ItemLootEntry.lootTableItem(Items.PINK_DYE)
                                .apply(SetCount.setCount(ConstantRange.exactly(1)))
                                .when(new TimeCheckBuilder(24000L, RandomValueRange.between(9001, 10500))))
                        .add(ItemLootEntry.lootTableItem(Items.GRAY_DYE)
                                .apply(SetCount.setCount(ConstantRange.exactly(1)))
                                .when(new TimeCheckBuilder(24000L, RandomValueRange.between(10501, 12000))))
                        .add(ItemLootEntry.lootTableItem(Items.LIGHT_GRAY_DYE)
                                .apply(SetCount.setCount(ConstantRange.exactly(1)))
                                .when(new TimeCheckBuilder(24000L, RandomValueRange.between(12001, 13500))))
                        .add(ItemLootEntry.lootTableItem(Items.CYAN_DYE)
                                .apply(SetCount.setCount(ConstantRange.exactly(1)))
                                .when(new TimeCheckBuilder(24000L, RandomValueRange.between(13501, 15000))))
                        .add(ItemLootEntry.lootTableItem(Items.PURPLE_DYE)
                                .apply(SetCount.setCount(ConstantRange.exactly(1)))
                                .when(new TimeCheckBuilder(24000L, RandomValueRange.between(15001, 16500))))
                        .add(ItemLootEntry.lootTableItem(Items.BLUE_DYE)
                                .apply(SetCount.setCount(ConstantRange.exactly(1)))
                                .when(new TimeCheckBuilder(24000L, RandomValueRange.between(16501, 18000))))
                        .add(ItemLootEntry.lootTableItem(Items.BROWN_DYE)
                                .apply(SetCount.setCount(ConstantRange.exactly(1)))
                                .when(new TimeCheckBuilder(24000L, RandomValueRange.between(18001, 19500))))
                        .add(ItemLootEntry.lootTableItem(Items.GREEN_DYE)
                                .apply(SetCount.setCount(ConstantRange.exactly(1)))
                                .when(new TimeCheckBuilder(24000L, RandomValueRange.between(19501, 21000))))
                        .add(ItemLootEntry.lootTableItem(Items.RED_DYE)
                                .apply(SetCount.setCount(ConstantRange.exactly(1)))
                                .when(new TimeCheckBuilder(24000L, RandomValueRange.between(21001, 22500))))
                        .add(ItemLootEntry.lootTableItem(Items.BLACK_DYE)
                                .apply(SetCount.setCount(ConstantRange.exactly(1)))
                                .when(new TimeCheckBuilder(24000L, RandomValueRange.between(22501, 23999))))
        )
        .withPool(seed(UCItems.DYEIUS_SEED.get())));
    }

    @Override
    public void run(DirectoryCache cache) throws IOException {

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
            IDataProvider.save(GSON, cache, LootTableManager.serialize(e.getValue().setParamSet(LootParameterSets.BLOCK).build()), path);
        }
    }

    private static Path getPath(Path root, ResourceLocation id) {

        return root.resolve("data/" + id.getNamespace() + "/loot_tables/blocks/" + id.getPath() + ".json");
    }

    private static LootTable.Builder genCrops(Block block) {

        ILootCondition.IBuilder condition = BlockStateProperty.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BaseCropsBlock.AGE, ((BaseCropsBlock)block).getHarvestAge()));
        BaseCropsBlock crop = (BaseCropsBlock)block;

        LootPool.Builder cropPool = LootPool.lootPool()
                .setRolls(ConstantRange.exactly(1))
                .add(ItemLootEntry.lootTableItem(crop.getCrop()))
                .when(condition);
        LootPool.Builder seedPool = LootPool.lootPool()
                .setRolls(ConstantRange.exactly(1))
                .add(ItemLootEntry.lootTableItem(crop.getSeed()))
                .when(SurvivesExplosion.survivesExplosion());

        return LootTable.lootTable().withPool(cropPool).withPool(seedPool);
    }

    private static LootTable.Builder genCropsWithBonus(Block block) {

        ILootCondition.IBuilder condition = BlockStateProperty.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BaseCropsBlock.AGE, ((BaseCropsBlock)block).getHarvestAge()));
        BaseCropsBlock crop = (BaseCropsBlock)block;

        LootTable.Builder builder = LootTable.lootTable()
                .withPool(LootPool.lootPool()
                .add(ItemLootEntry.lootTableItem(crop.getSeed())
                        .when(condition)
                        .otherwise(ItemLootEntry.lootTableItem(crop.getCrop()))))
                .withPool(LootPool.lootPool().when(condition)
                 .add(ItemLootEntry.lootTableItem(crop.getCrop())
                        .apply(ApplyBonus.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 1)))
                        .when(condition));

        return builder;
    }

    private static LootTable.Builder droppingAndBonusWhen(Block block, Item itemConditional, Item setValueBonus, ILootCondition.IBuilder conditionBuilder) {

        return LootTable.lootTable().withPool(LootPool.lootPool().add(ItemLootEntry.lootTableItem(itemConditional).when(conditionBuilder).otherwise(ItemLootEntry.lootTableItem(setValueBonus)))).withPool(LootPool.lootPool().when(conditionBuilder).add(ItemLootEntry.lootTableItem(setValueBonus).apply(ApplyBonus.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 1))));
    }

    private static LootTable.Builder genSlab(Block block) {

        LootEntry.Builder<?> entry = ItemLootEntry.lootTableItem(block)
                .apply(SetCount.setCount(ConstantRange.exactly(2))
                        .when(BlockStateProperty.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE))))
                .apply(ExplosionDecay.explosionDecay());
        return LootTable.lootTable().withPool(LootPool.lootPool().name("main").setRolls(ConstantRange.exactly(1)).add(entry));
    }

    private static LootTable.Builder genRegular(Block block) {

        LootEntry.Builder<?> entry = ItemLootEntry.lootTableItem(block);
        LootPool.Builder pool = LootPool.lootPool().name("main").setRolls(ConstantRange.exactly(1)).add(entry)
                .when(SurvivesExplosion.survivesExplosion());
        return LootTable.lootTable().withPool(pool);
    }

    private static LootTable.Builder genSilk(Block block) {

        return LootTable.lootTable().withPool(LootPool.lootPool().when(SILK_TOUCH).setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(block)));
    }

    private static LootPool.Builder seed(Item item) {

        return LootPool.lootPool().add(ItemLootEntry.lootTableItem(item).apply(ApplyBonus.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3))).when(SurvivesExplosion.survivesExplosion());
    }

    private static ILootCondition.IBuilder fullyGrown(Block block) {

        return BlockStateProperty.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BaseCropsBlock.AGE, ((BaseCropsBlock)block).getHarvestAge()));
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
