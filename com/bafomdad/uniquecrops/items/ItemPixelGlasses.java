package com.bafomdad.uniquecrops.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.bafomdad.uniquecrops.UniqueCrops;

public class ItemPixelGlasses extends ItemArmor {

	public ItemPixelGlasses(ArmorMaterial material, int renderindex, EntityEquipmentSlot slot) {
		
		super(material, renderindex, slot);
		setRegistryName("pixelglasses");
		setUnlocalizedName(UniqueCrops.MOD_ID + ".pixelglasses");
		setCreativeTab(UniqueCrops.TAB);
		setMaxDamage(200);
		GameRegistry.register(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean whatisthis) {
		
		list.add(TextFormatting.RED + "(WIP)");
	}
	
	@Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		
		return false;
	}
}
