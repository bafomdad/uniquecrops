package com.bafomdad.uniquecrops.integration.jei;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.api.IEnchanterRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public class UCEnchanterCategory implements IRecipeCategory<IEnchanterRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(UniqueCrops.MOD_ID, "enchanter");
    private final IDrawable background;

    public UCEnchanterCategory(IGuiHelper helper) {

        this.background = helper.createDrawable(new ResourceLocation(UniqueCrops.MOD_ID, "textures/gui/enchanter.png"), 0, 0, 100, 83);
    }

    @Override
    public ResourceLocation getUid() {

        return UID;
    }

    @Override
    public Class<? extends IEnchanterRecipe> getRecipeClass() {

        return IEnchanterRecipe.class;
    }

    @Override
    public Component getTitle() {

        return new TranslatableComponent("container.jei.uniquecrops.enchanter");
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
    public void draw(IEnchanterRecipe recipe, IRecipeSlotsView view, PoseStack ms, double mouseX, double mouseY) {

        final String text = I18n.get(recipe.getEnchantment().getDescriptionId()) + " " + recipe.getEnchantment().getMaxLevel();
        Minecraft minecraft = Minecraft.getInstance();
        int stringWidth = minecraft.font.width(text);
        minecraft.font.draw(ms, text, 50 - stringWidth / 2, -20, 0x555555);
        final String cost = "Cost: " + recipe.getCost();
        int costWidth = minecraft.font.width(cost);
        minecraft.font.draw(ms, cost, 50 - costWidth / 2, 95, 0x555555);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, IEnchanterRecipe recipe, IFocusGroup ingredients) {

        var inputs = recipe.getIngredients();
        if (inputs.size() > 1) {
            for (int i = 0; i < inputs.size(); ++i) {
                builder.addSlot(RecipeIngredientRole.INPUT, (i * 18) + 5, 63)
                        .addIngredients(inputs.get(i));
            }
        } else {
            for (int i = 0; i < inputs.get(0).getItems().length; ++i) {
                builder.addSlot(RecipeIngredientRole.INPUT, (i * 18) + 5, 63)
                        .addItemStack(inputs.get(0).getItems()[i]);
            }
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 41, 7)
                .addItemStack(recipe.getResultItem());
    }
}
