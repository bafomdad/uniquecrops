package com.bafomdad.uniquecrops.items;

import java.util.List;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.init.UCItems;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemPrecisionShovel extends ItemSpade implements IBookUpgradeable {

	public ItemPrecisionShovel() {
		
		super(ToolMaterial.DIAMOND);
		setRegistryName("precision.shovel");
		setUnlocalizedName(UniqueCrops.MOD_ID + ".precision.shovel");
		setCreativeTab(UniqueCrops.TAB);
		UCItems.items.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> list, ITooltipFlag whatisthis) {
		
		super.addInformation(stack, player, list, whatisthis);
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey(ItemGeneric.TAG_UPGRADE)) {
			int upgradelevel = stack.getTagCompound().getInteger(ItemGeneric.TAG_UPGRADE);
			list.add(TextFormatting.GOLD + "+" + upgradelevel);
		}
		else
			list.add(TextFormatting.GOLD + "Upgradeable");
	}
	
	@Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
    	
		boolean sametool = toRepair.getItem() == repair.getItem();
		boolean flag = repair.getItem() == UCItems.generic && repair.getItemDamage() == 8;
		return sametool || flag;
    }
}
