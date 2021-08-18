package com.bafomdad.uniquecrops.init;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.UCOreHandler;
import net.minecraft.block.Blocks;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.BitSet;
import java.util.Random;

public class UCFeatures {

    public static final DeferredRegister<Feature<?>> FEATURE = DeferredRegister.create(ForgeRegistries.FEATURES, UniqueCrops.MOD_ID);

    public static ConfiguredFeature<?, ?> ORE_PIXEL_CONFIG;
    public static ConfiguredFeature<BaseTreeFeatureConfig, ?> FLYWOOD_CONFIG;
    public static final RegistryObject<Feature<OreFeatureConfig>> UC_OREFEATURE = FEATURE.register("invisibleore", UCOreFeature::new);

    public static void registerOre() {

        ORE_PIXEL_CONFIG = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, "ore_pixel",
                UC_OREFEATURE.get().configured(
                        new OreFeatureConfig(
                            OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                            Blocks.STONE.defaultBlockState(), 4)
                        ).range(16).squared()
                );
        FLYWOOD_CONFIG = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, "flywood_tree",
                Feature.TREE.configured(
                        new BaseTreeFeatureConfig.Builder(
                            new SimpleBlockStateProvider(UCBlocks.FLYWOOD_LOG.get().defaultBlockState()),
                            new SimpleBlockStateProvider(UCBlocks.FLYWOOD_LEAVES.get().defaultBlockState()),
                            new BlobFoliagePlacer(FeatureSpread.fixed(2),FeatureSpread.fixed(0), 3),
                            new StraightTrunkPlacer(5, 2, 0),
                            new TwoLayerFeature(1, 0, 1)).ignoreVines()
                        .build()
                ));
    }

    public static class FlywoodTree extends Tree {

        @Nullable
        @Override
        public ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random rand, boolean largeHive) {

            return FLYWOOD_CONFIG;
        }
    }

    private static class UCOreFeature extends OreFeature {

        public UCOreFeature() {

            super(OreFeatureConfig.CODEC);
        }

        @Override
        protected boolean doPlace(IWorld p_207803_1_, Random p_207803_2_, OreFeatureConfig p_207803_3_, double p_207803_4_, double p_207803_6_, double p_207803_8_, double p_207803_10_, double p_207803_12_, double p_207803_14_, int p_207803_16_, int p_207803_17_, int p_207803_18_, int p_207803_19_, int p_207803_20_) {

            int lvt_21_1_ = 0;
            BitSet lvt_22_1_ = new BitSet(p_207803_19_ * p_207803_20_ * p_207803_19_);
            BlockPos.Mutable lvt_23_1_ = new BlockPos.Mutable();
            int lvt_24_1_ = p_207803_3_.size;
            double[] lvt_25_1_ = new double[lvt_24_1_ * 4];

            int lvt_26_2_;
            double lvt_28_2_;
            double lvt_30_2_;
            double lvt_32_2_;
            double lvt_34_2_;
            for(lvt_26_2_ = 0; lvt_26_2_ < lvt_24_1_; ++lvt_26_2_) {
                float lvt_27_1_ = (float)lvt_26_2_ / (float)lvt_24_1_;
                lvt_28_2_ = MathHelper.lerp((double)lvt_27_1_, p_207803_4_, p_207803_6_);
                lvt_30_2_ = MathHelper.lerp((double)lvt_27_1_, p_207803_12_, p_207803_14_);
                lvt_32_2_ = MathHelper.lerp((double)lvt_27_1_, p_207803_8_, p_207803_10_);
                lvt_34_2_ = p_207803_2_.nextDouble() * (double)lvt_24_1_ / 16.0D;
                double lvt_36_1_ = ((double)(MathHelper.sin(3.1415927F * lvt_27_1_) + 1.0F) * lvt_34_2_ + 1.0D) / 2.0D;
                lvt_25_1_[lvt_26_2_ * 4 + 0] = lvt_28_2_;
                lvt_25_1_[lvt_26_2_ * 4 + 1] = lvt_30_2_;
                lvt_25_1_[lvt_26_2_ * 4 + 2] = lvt_32_2_;
                lvt_25_1_[lvt_26_2_ * 4 + 3] = lvt_36_1_;
            }

            for(lvt_26_2_ = 0; lvt_26_2_ < lvt_24_1_ - 1; ++lvt_26_2_) {
                if (lvt_25_1_[lvt_26_2_ * 4 + 3] > 0.0D) {
                    for(int lvt_27_2_ = lvt_26_2_ + 1; lvt_27_2_ < lvt_24_1_; ++lvt_27_2_) {
                        if (lvt_25_1_[lvt_27_2_ * 4 + 3] > 0.0D) {
                            lvt_28_2_ = lvt_25_1_[lvt_26_2_ * 4 + 0] - lvt_25_1_[lvt_27_2_ * 4 + 0];
                            lvt_30_2_ = lvt_25_1_[lvt_26_2_ * 4 + 1] - lvt_25_1_[lvt_27_2_ * 4 + 1];
                            lvt_32_2_ = lvt_25_1_[lvt_26_2_ * 4 + 2] - lvt_25_1_[lvt_27_2_ * 4 + 2];
                            lvt_34_2_ = lvt_25_1_[lvt_26_2_ * 4 + 3] - lvt_25_1_[lvt_27_2_ * 4 + 3];
                            if (lvt_34_2_ * lvt_34_2_ > lvt_28_2_ * lvt_28_2_ + lvt_30_2_ * lvt_30_2_ + lvt_32_2_ * lvt_32_2_) {
                                if (lvt_34_2_ > 0.0D) {
                                    lvt_25_1_[lvt_27_2_ * 4 + 3] = -1.0D;
                                } else {
                                    lvt_25_1_[lvt_26_2_ * 4 + 3] = -1.0D;
                                }
                            }
                        }
                    }
                }
            }

            for(lvt_26_2_ = 0; lvt_26_2_ < lvt_24_1_; ++lvt_26_2_) {
                double lvt_27_3_ = lvt_25_1_[lvt_26_2_ * 4 + 3];
                if (lvt_27_3_ >= 0.0D) {
                    double lvt_29_1_ = lvt_25_1_[lvt_26_2_ * 4 + 0];
                    double lvt_31_1_ = lvt_25_1_[lvt_26_2_ * 4 + 1];
                    double lvt_33_1_ = lvt_25_1_[lvt_26_2_ * 4 + 2];
                    int lvt_35_1_ = Math.max(MathHelper.floor(lvt_29_1_ - lvt_27_3_), p_207803_16_);
                    int lvt_36_2_ = Math.max(MathHelper.floor(lvt_31_1_ - lvt_27_3_), p_207803_17_);
                    int lvt_37_1_ = Math.max(MathHelper.floor(lvt_33_1_ - lvt_27_3_), p_207803_18_);
                    int lvt_38_1_ = Math.max(MathHelper.floor(lvt_29_1_ + lvt_27_3_), lvt_35_1_);
                    int lvt_39_1_ = Math.max(MathHelper.floor(lvt_31_1_ + lvt_27_3_), lvt_36_2_);
                    int lvt_40_1_ = Math.max(MathHelper.floor(lvt_33_1_ + lvt_27_3_), lvt_37_1_);

                    for(int lvt_41_1_ = lvt_35_1_; lvt_41_1_ <= lvt_38_1_; ++lvt_41_1_) {
                        double lvt_42_1_ = ((double)lvt_41_1_ + 0.5D - lvt_29_1_) / lvt_27_3_;
                        if (lvt_42_1_ * lvt_42_1_ < 1.0D) {
                            for(int lvt_44_1_ = lvt_36_2_; lvt_44_1_ <= lvt_39_1_; ++lvt_44_1_) {
                                double lvt_45_1_ = ((double)lvt_44_1_ + 0.5D - lvt_31_1_) / lvt_27_3_;
                                if (lvt_42_1_ * lvt_42_1_ + lvt_45_1_ * lvt_45_1_ < 1.0D) {
                                    for(int lvt_47_1_ = lvt_37_1_; lvt_47_1_ <= lvt_40_1_; ++lvt_47_1_) {
                                        double lvt_48_1_ = ((double)lvt_47_1_ + 0.5D - lvt_33_1_) / lvt_27_3_;
                                        if (lvt_42_1_ * lvt_42_1_ + lvt_45_1_ * lvt_45_1_ + lvt_48_1_ * lvt_48_1_ < 1.0D) {
                                            int lvt_50_1_ = lvt_41_1_ - p_207803_16_ + (lvt_44_1_ - p_207803_17_) * p_207803_19_ + (lvt_47_1_ - p_207803_18_) * p_207803_19_ * p_207803_20_;
                                            if (!lvt_22_1_.get(lvt_50_1_)) {
                                                lvt_22_1_.set(lvt_50_1_);
                                                lvt_23_1_.set(lvt_41_1_, lvt_44_1_, lvt_47_1_);
                                                if (p_207803_3_.target.test(p_207803_1_.getBlockState(lvt_23_1_), p_207803_2_)) {
                                                    UCOreHandler.getInstance().addChunk(lvt_23_1_.immutable(), true);
                                                    p_207803_1_.setBlock(lvt_23_1_, p_207803_3_.state, 2);
                                                    ++lvt_21_1_;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return lvt_21_1_ > 0;
        }
    }
}
