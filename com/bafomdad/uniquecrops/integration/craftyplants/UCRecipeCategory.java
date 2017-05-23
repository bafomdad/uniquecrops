package com.bafomdad.uniquecrops.integration.craftyplants;

import java.util.List;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.crafting.UCrafting;
import com.bafomdad.uniquecrops.init.UCBlocks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;

public class UCRecipeCategory extends BlankRecipeCategory<UCRecipeWrapper> {

	public static final String NAME = "uniquecrops.craftyplant";
	
	private static final ItemStack CRAFTYPLANT = new ItemStack(UCBlocks.cropArtisia);
	private final IDrawable background;
	
	public UCRecipeCategory(IGuiHelper helper) {
		
		this.background = helper.createDrawable(new ResourceLocation(UniqueCrops.MOD_ID, "textures/gui/craftyplant.png"), 0, 0, 126, 64);
	}
	
	@Override
	public String getUid() {

		return NAME;
	}

	@Override
	public String getTitle() {

		return I18n.format("container.jei." + NAME + ".name");
	}

	@Override
	public IDrawable getBackground() {

		return this.background;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, UCRecipeWrapper wrapper, IIngredients ingredients) {

		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 3; ++x) {
				int index = 1 + x + (y * 3);
				recipeLayout.getItemStacks().init(index, true, (x * 18) + 5, (y * 18) + 5);
				recipeLayout.getItemStacks().set(index, wrapper.recipe.getInputs().get(index - 1));
			}
		}
		recipeLayout.getItemStacks().init(0, false, 99, 23);
		recipeLayout.getItemStacks().set(0, wrapper.recipe.getOutput());
	}
}
