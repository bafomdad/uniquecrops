package com.bafomdad.uniquecrops.api;

import net.minecraft.item.ItemStack;

public interface IBookUpgradeable {

  int getLevel(ItemStack stack);

  void setLevel(ItemStack stack, int level);

}