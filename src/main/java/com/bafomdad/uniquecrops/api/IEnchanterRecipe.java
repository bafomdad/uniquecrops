package com.bafomdad.uniquecrops.api;

import com.bafomdad.uniquecrops.UniqueCrops;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public interface IEnchanterRecipe extends IRecipe<IInventory> {

    ResourceLocation RES = new ResourceLocation(UniqueCrops.MOD_ID, "enchanter");

    @Override
    default IRecipeType<?> getType() {

        return Registry.RECIPE_TYPE.getOptional(RES).get();
    }

    @Override
    default boolean canFit(int width, int height) {

        return false;
    }

    @Override
    default boolean isDynamic() {

        return true;
    }

    boolean matchesEnchantment(String location);

    void applyEnchantment(ItemStack toApply);

    Enchantment getEnchantment();

    int getCost();
}
