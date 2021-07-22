package com.bafomdad.uniquecrops.api;

import com.bafomdad.uniquecrops.UniqueCrops;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public interface IHourglassRecipe extends IRecipe<IInventory> {

    ResourceLocation RES = new ResourceLocation(UniqueCrops.MOD_ID, "hourglass");

    boolean matches(BlockState state);

    BlockState getInput();

    BlockState getOutput();

    @Override
    default IRecipeType<?> getType() {

        return Registry.RECIPE_TYPE.getOptional(RES).get();
    }

    @Override
    default boolean matches(IInventory inv, World worldIn) {

        return false;
    }

    @Override
    default ItemStack getCraftingResult(IInventory inv) {

        return ItemStack.EMPTY;
    }

    @Override
    default boolean canFit(int width, int height) {

        return false;
    }

    @Override
    default ItemStack getRecipeOutput() {

        return ItemStack.EMPTY;
    }

    @Override
    default boolean isDynamic() {

        return true;
    }
}
