package com.bafomdad.uniquecrops.items.curios;

import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemCurioUC;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class EmblemBookworm extends ItemCurioUC {

    public static boolean isEdible(Item item) {

        return item == Items.ENCHANTED_BOOK;
    }

    public static FoodProperties getFood(ItemStack stack) {

        ListTag enchants = EnchantedBookItem.getEnchantments(stack);
        int hunger = 0;
        float saturation = 0.0F;
        float f = 0.25F;
        if (!enchants.isEmpty()) {
            for (int i = 0; i < enchants.size(); i++) {
                CompoundTag tag = enchants.getCompound(i);
                Enchantment ench = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(tag.getString("id")));
                if (ench != null) {
                    float sat =  Math.max(f * ench.getRarity().ordinal(), f);
                    if (sat > saturation)
                        saturation = sat;
                    int lvl = tag.getShort("lvl");
                    hunger += lvl * 2;
                }
            }
        }
        return new FoodProperties.Builder().nutrition(hunger).saturationMod(saturation).build();
    }

    public static boolean isEquipped(LivingEntity living) {

        return ((EmblemBookworm) UCItems.EMBLEM_BOOKWORM.get()).hasCurio(living);
    }
}
