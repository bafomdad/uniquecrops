package com.bafomdad.uniquecrops.init;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.api.*;
import com.bafomdad.uniquecrops.crafting.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class UCRecipes {

    public static final DeferredRegister<IRecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, UniqueCrops.MOD_ID);

    public static final IRecipeType<IArtisiaRecipe> ARTISIA_TYPE = new RecipeType<>();
    public static final IRecipeType<IHourglassRecipe> HOURGLASS_TYPE = new RecipeType<>();
    public static final IRecipeType<IEnchanterRecipe> ENCHANTER_TYPE = new RecipeType<>();
    public static final IRecipeType<IHeaterRecipe> HEATER_TYPE = new RecipeType<>();
    public static final IRecipeType<IMultiblockRecipe> MULTIBLOCK_TYPE = new RecipeType<>();

    public static final RegistryObject<IRecipeSerializer<?>> ARTISIA_SERIALIZER = register("artisia", RecipeArtisia.Serializer::new, ARTISIA_TYPE);
    public static final RegistryObject<IRecipeSerializer<?>> HOURGLASS_SERIALIZER = register("hourglass", RecipeHourglass.Serializer::new, HOURGLASS_TYPE);
    public static final RegistryObject<IRecipeSerializer<?>> ENCHANTER_SERIALIZER = register("enchanter", RecipeEnchanter.Serializer::new, ENCHANTER_TYPE);
    public static final RegistryObject<IRecipeSerializer<?>> HEATER_SERIALIZER = register("heater", RecipeHeater.Serializer::new, HEATER_TYPE);
    public static final RegistryObject<IRecipeSerializer<?>> MULTIBLOCK_SERIALIZER = register("multiblock", RecipeMultiblock.Serializer::new, MULTIBLOCK_TYPE);
    public static final RegistryObject<IRecipeSerializer<?>> DISCOUNTBOOK_SERIALIZER = register("discount_book", () -> new SpecialRecipeSerializer<>(RecipeDiscountBook::new));

    public static void registerBrews() {

        ItemStack awkwardPotion = PotionUtils.setPotion(new ItemStack(Items.POTION), Potion.byName("awkward"));
        ItemStack invisibilityPotion = PotionUtils.setPotion(new ItemStack(Items.POTION), Potion.byName("invisibility"));
        if (!awkwardPotion.isEmpty()) {
            BrewingRecipeRegistry.addRecipe(Ingredient.of(awkwardPotion), Ingredient.of(UCItems.TIMEDUST.get()), new ItemStack(UCItems.POTION_REVERSE.get()));
            BrewingRecipeRegistry.addRecipe(Ingredient.of(invisibilityPotion), Ingredient.of(UCBlocks.INVISIBILIA_GLASS.get()), new ItemStack(UCItems.POTION_IGNORANCE.get()));
        }
    }

    public static <R extends IRecipeSerializer<?>> RegistryObject<R> register(String name, Supplier<? extends R> supplier, IRecipeType type) {

        RegistryObject<R> recipe = RECIPES.register(name, supplier);
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(UniqueCrops.MOD_ID, name), type);
        return recipe;
    }

    public static <R extends IRecipeSerializer<?>> RegistryObject<R> register(String name, Supplier<? extends R> supplier) {

        return RECIPES.register(name, supplier);
    }

    private static class RecipeType<T extends IRecipe<?>> implements IRecipeType<T> {

        @Override
        public String toString() {

            return Registry.RECIPE_TYPE.getKey(this).toString();
        }
    }
}
