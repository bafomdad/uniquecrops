/*
 * Copyright (c) 2017 modmuss50 and Gigabit101
 *
 *
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 *
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 *
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.bafomdad.uniquecrops.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.registries.GameData;

public class RecipeHelper {

//	private static void addShapedOreRecipe(ItemStack output, Object... input) {
//		
//		ResourceLocation location = getNameForRecipe(output);
//		ShapedOreRecipe recipe = new ShapedOreRecipe(location, output, input);
//		recipe.setRegistryName(location);
//		GameData.register_impl(recipe);
//	}
//	
//	private static void addShapedRecipe(ItemStack output, Object... input) {
//		
//		ResourceLocation location = getNameForRecipe(output);
//		CraftingHelper.ShapedPrimer primer = CraftingHelper.parseShaped(input);
//		ShapedRecipes recipe = new ShapedRecipes(output.getItem().getRegistryName().toString(), primer.width, primer.height, primer.input, output);
//		recipe.setRegistryName(location);
//		GameData.register_impl(recipe);
//	}
//	
//	private static void addShapelessOreRecipe(ItemStack output, Object... input) {
//		
//		ResourceLocation location = getNameForRecipe(output);
//		ShapelessOreRecipe recipe = new ShapelessOreRecipe(location, output, input);
//		recipe.setRegistryName(location);
//		GameData.register_impl(recipe);
//	}
//	
//	private static void addShapelessRecipe(ItemStack output, Object... input) {
//		
//		ResourceLocation location = getNameForRecipe(output);
//		ShapelessRecipes recipe = new ShapelessRecipes(location.getResourceDomain(), output, buildInput(input));
//		recipe.setRegistryName(location);
//		GameData.register_impl(recipe);
//	}
//	
//	private static void addCustomRecipe(IRecipe recipe) {
//		
//		GameData.register_impl(recipe);
//	}
	
	public static ResourceLocation getNameForRecipe(ItemStack output) {
		
		ModContainer activeContainer = Loader.instance().activeModContainer();
		ResourceLocation baseLoc = new ResourceLocation(activeContainer.getModId(), output.getItem().getRegistryName().getPath());
		ResourceLocation recipeLoc = baseLoc;
		int index = 0;
		while (CraftingManager.REGISTRY.containsKey(recipeLoc)) {
			index++;
			recipeLoc = new ResourceLocation(activeContainer.getModId(), baseLoc.getPath() + "_" + index);
		}
		return recipeLoc;
	}
	
//	private static NonNullList<Ingredient> buildInput(Object[] input) {
//		
//		NonNullList<Ingredient> list = NonNullList.create();
//		for (Object obj : input) {
//			if (obj instanceof Ingredient)
//				list.add((Ingredient)obj);
//			else {
//				Ingredient ingredient = CraftingHelper.getIngredient(obj);
//				if (ingredient == null)
//					ingredient = Ingredient.EMPTY;
//				
//				list.add(ingredient);
//			}
//		}
//		return list;
//	}
}
