package com.bafomdad.uniquecrops.items;

import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.init.UCKeys;

public class ItemPixelGlasses extends ItemArmor implements IBookUpgradeable {
	
	public ItemPixelGlasses(ArmorMaterial material, int renderindex, EntityEquipmentSlot slot) {
		
		super(material, renderindex, slot);
		setRegistryName("pixelglasses");
		setTranslationKey(UniqueCrops.MOD_ID + ".pixelglasses");
		setCreativeTab(UniqueCrops.TAB);
		setMaxDamage(200);
		UCItems.items.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> list, ITooltipFlag whatisthis) {
		
		super.addInformation(stack, player, list, whatisthis);
		if (UCKeys.pixelKey != null)
			list.add(TextFormatting.GOLD + "Press Key " + Keyboard.getKeyName(UCKeys.pixelKey.getKeyCode()) + " to toggle.");
		int upgradeLevel = getLevel(stack);
		if (upgradeLevel > -1) {
			list.add(TextFormatting.GOLD + "+" + upgradeLevel);
		}
		else
			list.add(TextFormatting.GOLD + "Upgradeable");
	}
	
	@Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		
		return false;
	}
}
