package com.bafomdad.uniquecrops.items.curios;

import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemCurioUC;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class EmblemBookworm extends ItemCurioUC {

    public static boolean isFood(Item item) {

        return item == Items.ENCHANTED_BOOK;
    }

    public static Food getFood(ItemStack stack) {

        ListNBT enchants = EnchantedBookItem.getEnchantments(stack);
        int hunger = 0;
        float saturation = 0.0F;
        float f = 0.25F;
        if (!enchants.isEmpty()) {
            for (int i = 0; i < enchants.size(); i++) {
                CompoundNBT tag = enchants.getCompound(i);
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
        return new Food.Builder().hunger(hunger).saturation(saturation).build();
    }

    public static boolean isEquipped(LivingEntity living) {

        return ((EmblemBookworm) UCItems.EMBLEM_BOOKWORM.get()).hasCurio(living);
    }
}
