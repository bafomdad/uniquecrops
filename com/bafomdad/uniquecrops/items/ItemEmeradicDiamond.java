package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.api.IItemBooster;
import com.bafomdad.uniquecrops.init.UCItems;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemEmeradicDiamond extends Item implements IItemBooster {

	public ItemEmeradicDiamond() {
		
		setRegistryName("emeradic_diamond");
		setTranslationKey(UniqueCrops.MOD_ID + ".emeradic_diamond");
		setCreativeTab(UniqueCrops.TAB);
		UCItems.items.add(this);
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
       
		return true;
    }

	@Override
	public int getRange(ItemStack stack) {

		return 5;
	}

	@Override
	public int getPower(ItemStack stack) {

		return 1;
	}
}
