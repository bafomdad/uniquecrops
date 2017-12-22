package com.bafomdad.uniquecrops.integration.craftyplants;

import com.bafomdad.uniquecrops.UniqueCrops;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.List;

public class UCRecipeCategory implements IRecipeCategory<UCRecipeWrapper> {

	public static final String NAME = "uniquecrops.craftyplant";

	private final IDrawable background;

	public UCRecipeCategory(IGuiHelper helper) {

		this.background = helper.createDrawable(new ResourceLocation(UniqueCrops.MOD_ID, "textures/gui/craftyplant.png"), 0, 0, 126, 64);
	}

	@Nonnull
	@Override
	public String getUid() {

		return NAME;
	}

	@Nonnull
	@Override
	public String getTitle() {

		return I18n.format("container.jei." + NAME + ".name");
	}

	@Nonnull
	@Override
	public IDrawable getBackground() {

		return this.background;
	}

	@Override
	public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull UCRecipeWrapper wrapper, @Nonnull IIngredients ingredients) {

		IGuiItemStackGroup layout = recipeLayout.getItemStacks();

		List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
		List<List<ItemStack>> outputList = ingredients.getOutputs(ItemStack.class);
		List<ItemStack> outputs = outputList.get(0);

		layout.init(0, false, 99, 23);
		layout.set(0, outputs);

		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 3; ++x) {
				int index = 1 + x + (y * 3);
				layout.init(index, true, (x * 18) + 5, (y * 18) + 5);
				layout.set(index, inputs.get(index - 1));
			}
		}
	}

	@Nonnull
	@Override
	public String getModName() {

		return UniqueCrops.MOD_NAME;
	}
}
