package com.bafomdad.uniquecrops.integration.jei;

import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import com.bafomdad.uniquecrops.UniqueCrops;

public class UCHourglassCategory implements IRecipeCategory<UCHourglassWrapper> {
	
	private final IDrawable background;
	private final String uuid;
	
	public UCHourglassCategory(IGuiHelper helper, String uuid) {
		
		this.background = helper.createDrawable(new ResourceLocation(UniqueCrops.MOD_ID, "textures/gui/hourglass.png"), 0, 0, 126, 64);
		this.uuid = uuid;
	}

	@Override
	public String getUid() {

		return this.uuid;
	}

	@Override
	public String getTitle() {

		return I18n.format("container.jei.uniquecrops.hourglass.name");
	}

	@Override
	public IDrawable getBackground() {

		return this.background;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, UCHourglassWrapper recipeWrapper, IIngredients ingredients) {

		IGuiItemStackGroup layout = recipeLayout.getItemStacks();
		
		List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
		List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);
		
		layout.init(0, false, 99, 23);
		layout.init(1, false, 10, 23);
		
		layout.set(1, inputs.get(0));
		layout.set(0, outputs.get(0));
	}
	
	@Override
	public String getModName() {

		return UniqueCrops.MOD_NAME;
	}
}
