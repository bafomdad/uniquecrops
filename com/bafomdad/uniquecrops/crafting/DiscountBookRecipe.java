package com.bafomdad.uniquecrops.crafting;

import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.ItemGeneric;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class DiscountBookRecipe implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting ic, World world) {

		boolean foundDiscountBook = false;
		boolean foundItem = false;
		
		for (int i = 0; i < ic.getSizeInventory(); i++) {
			ItemStack stack = ic.getStackInSlot(i);
			if (stack != null) {
				if (stack.getItem() == UCItems.generic.createStack("discountbook").getItem())
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

		ItemStack item = null;
		
		for (int i = 0; i < ic.getSizeInventory(); i++) {
			ItemStack stack = ic.getStackInSlot(i);
			if (stack != null && stack.getItem() != UCItems.generic.createStack("discountbook").getItem())
				item = stack;
		}
		ItemStack copy = item.copy();
		NBTUtils.setBoolean(copy, ItemGeneric.TAG_DISCOUNT, true);
		copy.stackSize = 1;
		return copy;
	}

	@Override
	public int getRecipeSize() {

		return 10;
	}

	@Override
	public ItemStack getRecipeOutput() {

		return null;
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting ic) {

		return ForgeHooks.defaultRecipeGetRemainingItems(ic);
	}
	
	private boolean isStackValid(ItemStack stack) {
		
		return stack.getItem().isDamageable() && !NBTUtils.getBoolean(stack, ItemGeneric.TAG_DISCOUNT, false);
	}
}
