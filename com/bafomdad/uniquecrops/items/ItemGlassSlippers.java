package com.bafomdad.uniquecrops.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCItems;

public class ItemGlassSlippers extends ItemArmor {

	public ItemGlassSlippers(ItemArmor.ArmorMaterial material, int renderindex, EntityEquipmentSlot slot) {
		
		super(material, renderindex, slot);
		setRegistryName("slippers");
		setUnlocalizedName(UniqueCrops.MOD_ID + ".slippers");
		setCreativeTab(UniqueCrops.TAB);
		setMaxDamage(90);
		GameRegistry.register(this);
	}
	
	@Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		
		boolean flag = repair.getItem() == UCItems.generic && repair.getItemDamage() == 14;
		return toRepair.getItem() == this && flag;
	}
}
