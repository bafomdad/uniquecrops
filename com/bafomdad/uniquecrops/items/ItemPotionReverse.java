package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.UniqueCrops;

import net.minecraft.item.ItemFood;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemPotionReverse extends ItemFood {

	public ItemPotionReverse(int amount, float saturation, boolean always, String itemname) {
		
		super(amount, saturation, false);
		setRegistryName("genericfood." + itemname);
		setUnlocalizedName(UniqueCrops.MOD_ID + ".genericfood." + itemname);
		setCreativeTab(UniqueCrops.TAB);
		if (always)
			setAlwaysEdible();
		setMaxStackSize(1);
		GameRegistry.register(this);
	}
}
