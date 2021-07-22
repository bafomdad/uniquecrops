package com.bafomdad.uniquecrops.data;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.function.Function;

public class UCItemTagsProvider extends ItemTagsProvider {

    public static final ITag.INamedTag<Item> STEEL_INGOT = forgeTag("ingots/steel");

    public UCItemTagsProvider(DataGenerator gen, BlockTagsProvider provider, ExistingFileHelper helper) {

        super(gen, provider, UniqueCrops.MOD_ID, helper);
    }

    @Override
    public void registerTags() {

        this.getOrCreateBuilder(STEEL_INGOT).addItemEntry(UCItems.STEEL_DONUT.get());
    }

    @Override
    public String getName() {

        return "Unique Crops item tags";
    }

    private static ITag.INamedTag<Item> forgeTag(String name) {

        return getOrRegister(ItemTags.getAllTags(), loc -> ItemTags.makeWrapperTag(loc.toString()), new ResourceLocation("forge", name));
    }

    private static <T> ITag.INamedTag<T> getOrRegister(List<? extends ITag.INamedTag<T>> list, Function<ResourceLocation, ITag.INamedTag<T>> register, ResourceLocation loc) {

        for (ITag.INamedTag<T> existing : list) {
            if (existing.getName().equals(loc))
                return existing;
        }
        return register.apply(loc);
    }
}
