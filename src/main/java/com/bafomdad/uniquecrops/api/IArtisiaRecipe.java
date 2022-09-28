package com.bafomdad.uniquecrops.api;

import com.bafomdad.uniquecrops.UniqueCrops;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;

public interface IArtisiaRecipe extends Recipe<Container> {

    ResourceLocation RES = new ResourceLocation(UniqueCrops.MOD_ID, "artisia");

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
}
