package com.bafomdad.uniquecrops.data;

import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

public class DataGenerators {

    public static void gatherData(GatherDataEvent event) {

        ExistingFileHelper helper = event.getExistingFileHelper();
        if (event.includeServer()) {
//            event.getGenerator().addProvider(new UCLootProvider(event.getGenerator()));
            BlockTagsProvider blockTagsProvider = new UCBlockTagsProvider(event.getGenerator(), helper);
//            event.getGenerator().addProvider(blockTagsProvider);
            event.getGenerator().addProvider(new UCItemTagsProvider(event.getGenerator(), blockTagsProvider, helper));
//            event.getGenerator().addProvider(new UCAdvancementProvider(event.getGenerator()));
//            event.getGenerator().addProvider(new SmeltingProvider(event.getGenerator()));
//            event.getGenerator().addProvider(new StoneCuttingProvider(event.getGenerator()));
//            event.getGenerator().addProvider(new UCRecipeProvider(event.getGenerator()));
//            event.getGenerator().addProvider(new ArtisiaProvider(event.getGenerator()));
//            event.getGenerator().addProvider(new HourglassProvider(event.getGenerator()));
//            event.getGenerator().addProvider(new EnchanterProvider(event.getGenerator()));
//            event.getGenerator().addProvider(new HeaterProvider(event.getGenerator()));
//            event.getGenerator().addProvider(new MultiblockProvider(event.getGenerator()));
        }
        if (event.includeClient()) {
//            event.getGenerator().addProvider(new UCBlockStateProvider(event.getGenerator(), helper));
        }
    }
}
