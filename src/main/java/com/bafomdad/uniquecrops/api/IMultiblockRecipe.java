package com.bafomdad.uniquecrops.api;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.crafting.RecipeMultiblock;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Map;

public interface IMultiblockRecipe extends IRecipe<IInventory> {

    ResourceLocation RES = new ResourceLocation(UniqueCrops.MOD_ID, "multiblock");

    boolean match(World world, BlockPos originBlock);
    boolean isOriginBlock(BlockState state);

    ItemStack getCatalyst();
    int getPower();
    String[] getShape();
    Map<Character, RecipeMultiblock.Slot> getDefinition();

    void setResult(World world, BlockPos originBlock);

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
