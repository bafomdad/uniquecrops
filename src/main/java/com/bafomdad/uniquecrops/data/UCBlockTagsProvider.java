package com.bafomdad.uniquecrops.data;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCBlocks;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class UCBlockTagsProvider extends BlockTagsProvider {

    public UCBlockTagsProvider(DataGenerator gen, ExistingFileHelper helper) {

        super(gen, UniqueCrops.MOD_ID, helper);
    }

    @Override
    public void registerTags() {

        getOrCreateBuilder(BlockTags.STAIRS).add(UCBlocks.RUINEDBRICKS_STAIRS.get());
        getOrCreateBuilder(BlockTags.SLABS).add(UCBlocks.RUINEDBRICKS_SLAB.get(), UCBlocks.RUINEDBRICKSCARVED_SLAB.get());
        getOrCreateBuilder(BlockTags.LOGS).add(UCBlocks.FLYWOOD_LOG.get());
        getOrCreateBuilder(BlockTags.PLANKS).add(UCBlocks.FLYWOOD_PLANKS.get(), UCBlocks.ROSEWOOD_PLANKS.get());
        getOrCreateBuilder(BlockTags.WOODEN_STAIRS).add(UCBlocks.FLYWOOD_STAIRS.get(), UCBlocks.ROSEWOOD_STAIRS.get());
        getOrCreateBuilder(BlockTags.WOODEN_SLABS).add(UCBlocks.FLYWOOD_SLAB.get(), UCBlocks.ROSEWOOD_SLAB.get());
        getOrCreateBuilder(Tags.Blocks.STORAGE_BLOCKS_IRON).add(UCBlocks.OLDIRON.get());
        getOrCreateBuilder(Tags.Blocks.STORAGE_BLOCKS_DIAMOND).add(UCBlocks.OLDDIAMOND.get());
        getOrCreateBuilder(Tags.Blocks.STORAGE_BLOCKS_GOLD).add(UCBlocks.OLDGOLD.get());
        getOrCreateBuilder(Tags.Blocks.GRAVEL).add(UCBlocks.OLDGRAVEL.get());
    }

    @Override
    public String getName() {

        return "Unique Crops block tags";
    }
}
