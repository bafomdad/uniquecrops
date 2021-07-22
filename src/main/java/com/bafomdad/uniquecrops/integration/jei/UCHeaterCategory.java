package com.bafomdad.uniquecrops.integration.jei;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.api.IHeaterRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class UCHeaterCategory implements IRecipeCategory<IHeaterRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(UniqueCrops.MOD_ID, "heater");
    private final IDrawable background;

    public UCHeaterCategory(IGuiHelper helper) {

        this.background = helper.createDrawable(new ResourceLocation(UniqueCrops.MOD_ID, "textures/gui/heater.png"), 0, 0, 126, 64);
    }

    @Override
    public ResourceLocation getUid() {

        return UID;
    }

    @Override
    public Class<? extends IHeaterRecipe> getRecipeClass() {

        return IHeaterRecipe.class;
    }

    @Override
    public String getTitle() {

        return I18n.format("container.jei.uniquecrops.heater");
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
    public void setIngredients(IHeaterRecipe recipe, IIngredients ingredients) {

        ingredients.setInput(VanillaTypes.ITEM, recipe.getInput());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IHeaterRecipe recipe, IIngredients ingredients) {

        IGuiItemStackGroup layout = recipeLayout.getItemStacks();

        layout.init(0, false, 99, 23);
        layout.init(1, false, 10, 23);

        layout.set(1, ingredients.getInputs(VanillaTypes.ITEM).get(0));
        layout.set(0, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
    }
}
