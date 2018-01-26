package com.bafomdad.uniquecrops.integration.crafttweaker.mtlib;

import crafttweaker.api.item.IItemStack;
import crafttweaker.mc1120.item.MCItemStack;
import net.minecraft.item.ItemStack;

import java.lang.reflect.Array;

/**
 * https://github.com/jaredlll08/MTLib/blob/1.12/src/main/java/com/blamejared/mtlib/helpers/InputHelper.java
 */
public class InputHelper {

  public static ItemStack toStack(IItemStack iStack) {

    if (iStack == null) {
      return ItemStack.EMPTY;
    } else {
      Object internal = iStack.getInternal();
      if (!(internal instanceof ItemStack)) {
        LogHelper.logError("Not a valid item stack: " + iStack);
      }

      return (ItemStack) internal;
    }
  }

  public static <T> T[][] getMultiDimensionalArray(Class<T> clazz, T[] array, int height, int width) {

    T[][] multiDim = (T[][]) Array.newInstance(clazz, height, width);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        multiDim[y][x] = array[x + (y * width)];
      }
    }

    return multiDim;
  }

  public static ItemStack[] toStacks(IItemStack[] iStack) {

    if (iStack == null) {
      return null;
    } else {
      ItemStack[] output = new ItemStack[iStack.length];
      for (int i = 0; i < iStack.length; i++) {
        output[i] = toStack(iStack[i]);
      }

      return output;
    }
  }

  public static IItemStack toIItemStack(ItemStack stack) {

    if (stack.isEmpty()) {
      return null;
    }

    return new MCItemStack(stack);
  }
}
