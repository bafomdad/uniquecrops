package com.bafomdad.uniquecrops.integration.jei;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.init.UCRecipes;
import com.bafomdad.uniquecrops.mixin.AccessorRecipeManager;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.Collection;

@JeiPlugin
public class JEIPluginUC implements IModPlugin {

    private static final ResourceLocation ID = new ResourceLocation(UniqueCrops.MOD_ID, "main");

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {

        registry.addRecipeCategories(
                new UCArtisiaCategory(registry.getJeiHelpers().getGuiHelper()),
                new UCHourglassCategory(registry.getJeiHelpers().getGuiHelper()),
                new UCHeaterCategory(registry.getJeiHelpers().getGuiHelper()),
                new UCEnchanterCategory(registry.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registry) {

        registry.addRecipes(loadType(UCRecipes.ARTISIA_TYPE), UCArtisiaCategory.UID);
        registry.addRecipes(loadType(UCRecipes.HOURGLASS_TYPE), UCHourglassCategory.UID);
        registry.addRecipes(loadType(UCRecipes.HEATER_TYPE), UCHeaterCategory.UID);
        registry.addRecipes(loadType(UCRecipes.ENCHANTER_TYPE), UCEnchanterCategory.UID);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {

        registry.addRecipeCatalyst(new ItemStack(UCItems.DUMMY_ARTISIA.get()), UCArtisiaCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(UCBlocks.HOURGLASS.get()), UCHourglassCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(UCItems.DUMMY_HEATER.get()), UCHeaterCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(UCItems.DUMMY_FASCINO.get()), UCEnchanterCategory.UID);
    }

    @Override
    public ResourceLocation getPluginUid() {

        return ID;
    }

    public static <T extends IRecipe<C>, C extends IInventory> Collection<T> loadType(IRecipeType<T> type) {

        Minecraft mc = Minecraft.getInstance();
        return (Collection<T>)((AccessorRecipeManager)mc.world.getRecipeManager()).uc_getRecipes(type).values();
    }
}
