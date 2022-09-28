package com.bafomdad.uniquecrops.mixin;

import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;
import java.util.Map;

@Mixin(RecipeManager.class)
public interface AccessorRecipeManager {

    @Invoker("byType")
    <C extends Container, T extends Recipe<C>> Map<ResourceLocation, Recipe<C>> uc_byType(RecipeType<T> type);

    @Invoker("getAllRecipesFor")
    <C extends Container, T extends Recipe<C>> List<T> uc_getRecipes(RecipeType<T> type);
}
