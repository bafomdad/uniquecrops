package com.bafomdad.uniquecrops.items.curios;

import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.init.UCFoods;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemCurioUC;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;

import java.util.HashMap;
import java.util.Map;

public class EmblemIronStomach extends ItemCurioUC {

    private static final Map<TagKey<Item>, FoodProperties> STRANGE_FOODS = new HashMap<>();

    public static void init() {

        if (!STRANGE_FOODS.isEmpty()) return;

        STRANGE_FOODS.put(Tags.Items.NUGGETS, UCFoods.EDIBLE_NUGGET);
        STRANGE_FOODS.put(Tags.Items.INGOTS, UCFoods.EDIBLE_INGOT);
        STRANGE_FOODS.put(Tags.Items.GEMS, UCFoods.EDIBLE_GEM);
        STRANGE_FOODS.put(Tags.Items.INGOTS_GOLD, UCFoods.EDIBLE_GOLD);
        STRANGE_FOODS.put(Tags.Items.GEMS_DIAMOND, UCFoods.EDIBLE_DIAMOND);
        STRANGE_FOODS.put(Tags.Items.GEMS_EMERALD, UCFoods.EDIBLE_EMERALD);
    }

    public static boolean containsTag(Item item) {

        if (STRANGE_FOODS.isEmpty()) return false;
        for (Map.Entry<TagKey<Item>, FoodProperties> tag : STRANGE_FOODS.entrySet()) {
            if (UCUtils.hasTag(tag.getKey(), item)) return true;
        }
        return false;
    }

    public static FoodProperties getFood(Item item) {

        if (STRANGE_FOODS.isEmpty()) return null;
        for (Map.Entry<TagKey<Item>, FoodProperties> tag : STRANGE_FOODS.entrySet()) {
            if (UCUtils.hasTag(tag.getKey(), item)) return tag.getValue();
        }
        return null;
    }

    public static boolean isEquipped(LivingEntity living) {

        return ((EmblemIronStomach)UCItems.EMBLEM_IRONSTOMACH.get()).hasCurio(living);
    }
}
