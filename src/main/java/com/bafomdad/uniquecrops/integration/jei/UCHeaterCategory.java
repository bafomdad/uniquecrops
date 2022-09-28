package com.bafomdad.uniquecrops.integration.jei;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.api.IHeaterRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

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
    public Component getTitle() {

        return new TranslatableComponent("container.jei.uniquecrops.heater");
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
    public void setRecipe(IRecipeLayoutBuilder builder, IHeaterRecipe recipe, IFocusGroup ingredients) {

        builder.addSlot(RecipeIngredientRole.OUTPUT, 99, 23)
                .addItemStack(recipe.getResultItem());

        builder.addSlot(RecipeIngredientRole.INPUT, 10, 23)
                .addItemStack(recipe.getInput());
    }
}
