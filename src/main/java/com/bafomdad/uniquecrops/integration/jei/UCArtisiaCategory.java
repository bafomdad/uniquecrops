package com.bafomdad.uniquecrops.integration.jei;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.api.IArtisiaRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UCArtisiaCategory implements IRecipeCategory<IArtisiaRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(UniqueCrops.MOD_ID, "artisia");
    private final IDrawable background;

    public UCArtisiaCategory(IGuiHelper helper) {

        this.background = helper.createDrawable(new ResourceLocation(UniqueCrops.MOD_ID, "textures/gui/craftyplant.png"), 0, 0, 126, 64);
    }

    @Override
    public ResourceLocation getUid() {

        return UID;
    }

    @Override
    public Class<? extends IArtisiaRecipe> getRecipeClass() {

        return IArtisiaRecipe.class;
    }

    @Override
    public String getTitle() {

        return I18n.format("container.jei.uniquecrops.craftyplant");
    }

    @Override
    public IDrawable getBackground() {

        return background;
    }

    @Override
    public IDrawable getIcon() {

        return null;
    }

    @Override
    public void setIngredients(IArtisiaRecipe recipe, IIngredients ingredients) {

        List<List<ItemStack>> list = new ArrayList<>();
        for (Ingredient ingr : recipe.getIngredients()) {
            list.add(Arrays.asList(ingr.getMatchingStacks()));
        }
        ingredients.setInputLists(VanillaTypes.ITEM, list);
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IArtisiaRecipe recipe, IIngredients ingredients) {

        IGuiItemStackGroup layout = recipeLayout.getItemStacks();

        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                int index = 1 + x + (y * 3);
                layout.init(index, true, (x * 18) + 5, (y * 18) + 5);
                layout.set(index, ingredients.getInputs(VanillaTypes.ITEM).get(index - 1));
            }
        }
        layout.init(0, false, 99, 23);
        layout.set(0, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
    }
}
