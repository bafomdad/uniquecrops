package com.bafomdad.uniquecrops.integration.jei;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.api.IHourglassRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;

public class UCHourglassCategory implements IRecipeCategory<IHourglassRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(UniqueCrops.MOD_ID, "hourglass");
    private final IDrawable background;

    public UCHourglassCategory(IGuiHelper helper) {

        this.background = helper.createDrawable(new ResourceLocation(UniqueCrops.MOD_ID, "textures/gui/hourglass.png"), 0, 0, 126, 64);
    }

    @Override
    public ResourceLocation getUid() {

        return UID;
    }

    @Override
    public Class<? extends IHourglassRecipe> getRecipeClass() {

        return IHourglassRecipe.class;
    }

    @Override
    public Component getTitle() {

        return new TranslatableComponent("container.jei.uniquecrops.hourglass");
    }

    @Override
    public IDrawable getBackground() {

        return this.background;
    }

    @Override
    public IDrawable getIcon() {

        return null;
    }

//    @Override
//    public void setIngredients(IHourglassRecipe recipe, IIngredients ingredients) {
//
//        BlockState inputState = recipe.getInput();
//        BlockState outputState = recipe.getOutput();
//
//        ingredients.setInput(VanillaTypes.ITEM, new ItemStack(inputState.getBlock()));
//        ingredients.setOutput(VanillaTypes.ITEM, new ItemStack(outputState.getBlock()));
//    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, IHourglassRecipe recipe, IFocusGroup ingredients) {

        builder.addSlot(RecipeIngredientRole.OUTPUT, 99, 23)
                .addItemStack(new ItemStack(recipe.getOutput().getBlock()));

        builder.addSlot(RecipeIngredientRole.INPUT, 10, 23)
                .addItemStack(new ItemStack(recipe.getInput().getBlock()));
//        IGuiItemStackGroup layout = recipeLayout.getItemStacks();
//
//        layout.init(0, false, 99, 23);
//        layout.init(1, false, 10, 23);
//
//        layout.set(1, ingredients.getInputs(VanillaTypes.ITEM).get(0));
//        layout.set(0, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
    }
}
