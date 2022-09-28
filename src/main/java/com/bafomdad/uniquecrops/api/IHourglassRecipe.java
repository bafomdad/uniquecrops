package com.bafomdad.uniquecrops.api;

import com.bafomdad.uniquecrops.UniqueCrops;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.world.level.Level;

public interface IHourglassRecipe extends Recipe<Container> {

    ResourceLocation RES = new ResourceLocation(UniqueCrops.MOD_ID, "hourglass");

    boolean matches(BlockState state);

    BlockState getInput();

    BlockState getOutput();

    @Override
    default RecipeType<?> getType() {

        return Registry.RECIPE_TYPE.getOptional(RES).get();
    }

    @Override
    default boolean matches(Container inv, Level worldIn) {

        return false;
    }

    @Override
    default ItemStack assemble(Container inv) {

        return ItemStack.EMPTY;
    }

    @Override
    default boolean canCraftInDimensions(int width, int height) {

        return false;
    }

    @Override
    default ItemStack getResultItem() {

        return ItemStack.EMPTY;
    }

    @Override
    default boolean isSpecial() {

        return true;
    }
}
