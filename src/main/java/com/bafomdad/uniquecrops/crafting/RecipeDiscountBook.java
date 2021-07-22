package com.bafomdad.uniquecrops.crafting;

import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.init.UCRecipes;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RecipeDiscountBook extends SpecialRecipe {

    public RecipeDiscountBook(ResourceLocation id) {

        super(id);
    }

    @Override
    public boolean matches(CraftingInventory ic, World world) {

        boolean foundDiscountBook = false;
        boolean foundItem = false;

        for (int i = 0; i < ic.getSizeInventory(); i++) {
            ItemStack stack = ic.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == UCItems.BOOK_DISCOUNT.get())
                    foundDiscountBook = true;
                else if (!foundItem && !(NBTUtils.detectNBT(stack) && NBTUtils.getBoolean(stack, UCStrings.TAG_DISCOUNT, false)) && !stack.getItem().hasContainerItem(stack) && isStackValid(stack))
                    foundItem = true;
                else return false;
            }
        }
        return foundDiscountBook && foundItem;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory ic) {

        ItemStack item = ItemStack.EMPTY;

        for (int i = 0; i < ic.getSizeInventory(); i++) {
            ItemStack stack = ic.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() != UCItems.BOOK_DISCOUNT.get())
                item = stack;
        }
        ItemStack copy = item.copy();
        NBTUtils.setBoolean(copy, UCStrings.TAG_DISCOUNT, true);
        copy.setCount(1);
        return copy;
    }

    @Override
    public boolean canFit(int width, int height) {

        return width > 1 || height > 1;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {

        return UCRecipes.DISCOUNTBOOK_SERIALIZER.get();
    }

    private boolean isStackValid(ItemStack stack) {

        return stack.getItem().isDamageable() && !NBTUtils.getBoolean(stack, UCStrings.TAG_DISCOUNT, false);
    }
}
