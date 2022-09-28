package com.bafomdad.uniquecrops.api;

import com.bafomdad.uniquecrops.UniqueCrops;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;

public interface IEnchanterRecipe extends Recipe<Container> {

    ResourceLocation RES = new ResourceLocation(UniqueCrops.MOD_ID, "enchanter");

    @Override
    default RecipeType<?> getType() {

        return Registry.RECIPE_TYPE.getOptional(RES).get();
    }

    @Override
    default boolean canCraftInDimensions(int width, int height) {

        return false;
    }

    @Override
    default boolean isSpecial() {

        return true;
    }

    boolean matchesEnchantment(String location);

    void applyEnchantment(ItemStack toApply);

    Enchantment getEnchantment();

    int getCost();
}
