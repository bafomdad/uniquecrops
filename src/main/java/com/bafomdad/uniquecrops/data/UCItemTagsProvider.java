package com.bafomdad.uniquecrops.data;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.core.Registry;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

public class UCItemTagsProvider extends ItemTagsProvider {

    public static final TagKey<Item> STEEL_INGOT = forgeTag("ingots/steel");
    public static final TagKey<Item> NORMAL_DROP = cTag("normal_drops");

    public UCItemTagsProvider(DataGenerator gen, BlockTagsProvider provider, ExistingFileHelper helper) {

        super(gen, provider, UniqueCrops.MOD_ID, helper);
    }

    @Override
    public void addTags() {

        this.tag(STEEL_INGOT).add(UCItems.STEEL_DONUT.get());
        this.tag(NORMAL_DROP).add(Items.BEETROOT, Items.WHEAT, Items.CARROT, Items.POTATO);
        this.tag(ItemTags.PIGLIN_LOVED).add(UCItems.THUNDERPANTZ.get(), UCItems.GLASSES_PIXELS.get(), UCItems.GLASSES_3D.get());
    }

    @Override
    public String getName() {

        return "Unique Crops item tags";
    }

    private static TagKey<Item> forgeTag(String name) {

        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("forge", name));
    }

    private static TagKey<Item> cTag(String name) {

        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("c", name));
    }
}
