package com.bafomdad.uniquecrops.init;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.crafting.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.*;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.registries.*;

import java.util.function.Supplier;

public class UCRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, UniqueCrops.MOD_ID);

    public static final RegistryObject<RecipeSerializer<?>> ARTISIA_SERIALIZER = register("artisia", RecipeArtisia.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> HOURGLASS_SERIALIZER = register("hourglass", RecipeHourglass.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> ENCHANTER_SERIALIZER = register("enchanter", RecipeEnchanter.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> HEATER_SERIALIZER = register("heater", RecipeHeater.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> MULTIBLOCK_SERIALIZER = register("multiblock", RecipeMultiblock.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> DISCOUNTBOOK_SERIALIZER = register("discount_book", () -> new SimpleRecipeSerializer<>(RecipeDiscountBook::new));

    public static void registerBrews() {

        ItemStack awkwardPotion = PotionUtils.setPotion(new ItemStack(Items.POTION), Potion.byName("awkward"));
        ItemStack invisibilityPotion = PotionUtils.setPotion(new ItemStack(Items.POTION), Potion.byName("invisibility"));
        if (!awkwardPotion.isEmpty()) {
            BrewingRecipeRegistry.addRecipe(Ingredient.of(awkwardPotion), Ingredient.of(UCItems.TIMEDUST.get()), new ItemStack(UCItems.POTION_REVERSE.get()));
            BrewingRecipeRegistry.addRecipe(Ingredient.of(invisibilityPotion), Ingredient.of(UCBlocks.INVISIBILIA_GLASS.get()), new ItemStack(UCItems.POTION_IGNORANCE.get()));
            BrewingRecipeRegistry.addRecipe(Ingredient.of(awkwardPotion), Ingredient.of(UCItems.ZOMBIE_SLURRY.get()), new ItemStack(UCItems.POTION_ZOMBIFICATION.get()));
        }
    }

    public static <R extends RecipeSerializer<?>> RegistryObject<R> register(String name, Supplier<? extends R> supplier) {

        return RECIPES.register(name, supplier);
    }
}
