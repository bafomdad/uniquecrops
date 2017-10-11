package com.bafomdad.uniquecrops.crafting;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.EnumItems;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.ItemGeneric;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class DiscountBookRecipe extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
	
	final String name;
	
	public DiscountBookRecipe(String name) {
		
		this.name = name;
		this.setRegistryName(new ResourceLocation(UniqueCrops.MOD_ID, name));
	}

	@Override
	public boolean matches(InventoryCrafting ic, World world) {

		boolean foundDiscountBook = false;
		boolean foundItem = false;
		
		for (int i = 0; i < ic.getSizeInventory(); i++) {
			ItemStack stack = ic.getStackInSlot(i);
			if (!stack.isEmpty()) {
				if (stack.getItem() == UCItems.generic.createStack(EnumItems.DISCOUNT).getItem())
					foundDiscountBook = true;
				else if (!foundItem && !(NBTUtils.detectNBT(stack) && NBTUtils.getBoolean(stack, ItemGeneric.TAG_DISCOUNT, false)) && !stack.getItem().hasContainerItem(stack) && isStackValid(stack))
					foundItem = true;
				else return false;
			}
		}
		return foundDiscountBook && foundItem;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting ic) {

		ItemStack item = ItemStack.EMPTY;
		
		for (int i = 0; i < ic.getSizeInventory(); i++) {
			ItemStack stack = ic.getStackInSlot(i);
			if (!stack.isEmpty() && stack.getItem() != UCItems.generic.createStack(EnumItems.DISCOUNT).getItem())
				item = stack;
		}
		ItemStack copy = item.copy();
		NBTUtils.setBoolean(copy, ItemGeneric.TAG_DISCOUNT, true);
		copy.setCount(1);
		return copy;
	}

	@Override
	public ItemStack getRecipeOutput() {

		return UCItems.generic.createStack(EnumItems.DISCOUNT);
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting ic) {

		return ForgeHooks.defaultRecipeGetRemainingItems(ic);
	}
	
	private boolean isStackValid(ItemStack stack) {
		
		return stack.getItem().isDamageable() && !NBTUtils.getBoolean(stack, ItemGeneric.TAG_DISCOUNT, false);
	}

	@Override
	public boolean canFit(int width, int height) {
		
		return width >= 3 && height >= 3;
	}

	@Override
	public boolean isHidden() {
		
		return false;
	}
}
