package com.bafomdad.uniquecrops.integration.jei;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.api.IArtisiaRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

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
    public Component getTitle() {

        return new TranslatableComponent("container.jei.uniquecrops.craftyplant");
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
    public void setRecipe(IRecipeLayoutBuilder builder, IArtisiaRecipe recipe, IFocusGroup ingredients) {

        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                int index = 1 + x + (y * 3);
                builder.addSlot(RecipeIngredientRole.INPUT, (x * 18) + 5, (y * 18) + 5)
                        .addIngredients(recipe.getIngredients().get(index - 1));
            }
        }
        builder.addSlot(RecipeIngredientRole.OUTPUT, 99, 23)
                .addItemStack(recipe.getResultItem());
    }
}
