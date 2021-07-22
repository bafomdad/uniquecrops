package com.bafomdad.uniquecrops.data;

import com.bafomdad.uniquecrops.UniqueCrops;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.bafomdad.uniquecrops.init.UCBlocks.*;

public class UCBlockStateProvider extends BlockStateProvider {

    public UCBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {

        super(gen, UniqueCrops.MOD_ID, exFileHelper);
    }

    @Override
    public String getName() {

        return "uniquecrops blockstates";
    }

    @Override
    protected void registerStatesAndModels() {

        //Set<Block> moddedBlocks = Registry.BLOCK.stream().filter(b -> UniqueCrops.MOD_ID.equals(Registry.BLOCK.getKey(b).getNamespace())).collect(Collectors.toSet());

//        stairsBlock(FLYWOOD_STAIRS, prefix("block/flywood_planks"));
//        simpleBlock(OLDBRICK);
//        simpleBlock(OLDCOBBLE);
//        simpleBlock(OLDCOBBLEMOSS);
//        simpleBlock(OLDGRAVEL);
//        cubeIt(OLDDIAMOND, "block/old_diamond_side", "block/old_diamond_bottom", "block/old_diamond_top");
//        cubeIt(OLDGOLD, "block/old_gold_side", "block/old_gold_bottom", "block/old_gold_top");
//        cubeIt(OLDIRON, "block/old_iron_side", "block/old_iron_bottom", "block/old_iron_top");
//        stairsBlock(RUINEDBRICKS_STAIRS, prefix("block/ruinedbricks"));
//        slabBlock(FLYWOOD_SLAB, prefix("block/flywood_planks"), prefix("block/flywood_planks"));
//        slabBlock(RUINEDBRICKS_SLAB, prefix("block/ruinedbricks"), prefix("block/ruinedbricks"));
//        slabBlock(RUINEDBRICKSCARVED_SLAB, prefix("block/ruinedbricksmooth"), prefix("block/ruinedbricksmooth"));
        simpleBlock(ROSEWOOD_PLANKS.get());
        stairsBlock((StairsBlock)ROSEWOOD_STAIRS.get(), prefix("block/rosewood_planks"));
        slabBlock((SlabBlock)ROSEWOOD_SLAB.get(), prefix("block/rosewood_planks"), prefix("block/rosewood_planks"));
    }

    private ResourceLocation prefix(String path) {

        return new ResourceLocation(UniqueCrops.MOD_ID, path);
    }

    private void cubeIt(Block block, String sideTexture, String bottomTexture, String topTexture) {

        String modelName = Registry.BLOCK.getKey(block).getPath();
        ModelFile cube = models().cubeBottomTop(modelName, prefix(sideTexture), prefix(bottomTexture), prefix(topTexture));
        simpleBlock(block, cube);
    }
}
