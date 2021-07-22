package com.bafomdad.uniquecrops.integration.jei;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.api.IEnchanterRecipe;
import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public String getTitle() {

        return I18n.format("container.jei.uniquecrops.enchanter");
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
    public void draw(IEnchanterRecipe recipe, MatrixStack ms, double mouseX, double mouseY) {

        final String text = I18n.format(recipe.getEnchantment().getName()) + " " + recipe.getEnchantment().getMaxLevel();
        Minecraft minecraft = Minecraft.getInstance();
        int stringWidth = minecraft.fontRenderer.getStringWidth(text);
        minecraft.fontRenderer.drawString(ms, text, 50 - stringWidth / 2, -20, 0x555555);
        final String cost = "Cost: " + recipe.getCost();
        int costWidth = minecraft.fontRenderer.getStringWidth(cost);
        minecraft.fontRenderer.drawString(ms, cost, 50 - costWidth / 2, 95, 0x555555);
    }

    @Override
    public void setIngredients(IEnchanterRecipe recipe, IIngredients ingredients) {

        List<List<ItemStack>> list = new ArrayList<>();
        for (Ingredient ingr : recipe.getIngredients()) {
            list.add(Arrays.asList(ingr.getMatchingStacks()));
        }
        ingredients.setInputLists(VanillaTypes.ITEM, list);
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IEnchanterRecipe recipe, IIngredients ingredients) {

        IGuiItemStackGroup layout = recipeLayout.getItemStacks();

        layout.init(0, false, 41, 7);
        layout.set(0, ingredients.getOutputs(VanillaTypes.ITEM).get(0));

        List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
        if (inputs.size() > 1) {
            for (int i = 0; i < inputs.size(); ++i) {
                int index = 1 + i;
                layout.init(index, true, (i * 18) + 5, 63);
                layout.set(index, inputs.get(index - 1));
            }
        } else {
            for (int i = 0; i < inputs.get(0).size(); ++i) {
                int index = 1 + i;
                layout.init(index, true, (i * 18) + 5, 63);
                layout.set(index, inputs.get(0).get(index - 1));
            }
        }
    }
}
