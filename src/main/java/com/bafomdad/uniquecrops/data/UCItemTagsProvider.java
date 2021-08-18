package com.bafomdad.uniquecrops.data;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.function.Function;

public class UCItemTagsProvider extends ItemTagsProvider {

    public static final ITag.INamedTag<Item> STEEL_INGOT = forgeTag("ingots/steel");
    public static final ITag.INamedTag<Item> NORMAL_DROP = cTag("normal_drops");

    public UCItemTagsProvider(DataGenerator gen, BlockTagsProvider provider, ExistingFileHelper helper) {

        super(gen, provider, UniqueCrops.MOD_ID, helper);
    }

    @Override
    public void addTags() {

        this.tag(STEEL_INGOT).add(UCItems.STEEL_DONUT.get());
        this.tag(NORMAL_DROP).add(Items.BEETROOT, Items.WHEAT, Items.CARROT, Items.POTATO);
    }

    @Override
    public String getName() {

        return "Unique Crops item tags";
    }

    private static ITag.INamedTag<Item> forgeTag(String name) {

        return getOrRegister(ItemTags.getWrappers(), loc -> ItemTags.bind(loc.toString()), new ResourceLocation("forge", name));
    }

    private static ITag.INamedTag<Item> cTag(String name) {

        return getOrRegister(ItemTags.getWrappers(), loc -> ItemTags.bind(loc.toString()), new ResourceLocation("c", name));
    }

    private static <T> ITag.INamedTag<T> getOrRegister(List<? extends ITag.INamedTag<T>> list, Function<ResourceLocation, ITag.INamedTag<T>> register, ResourceLocation loc) {

        for (ITag.INamedTag<T> existing : list) {
            if (existing.getName().equals(loc))
                return existing;
        }
        return register.apply(loc);
    }
}
