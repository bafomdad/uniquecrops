package com.bafomdad.uniquecrops.items;

import java.util.List;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.core.EnumItems;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.init.UCItems;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemPrecisionSword extends ItemSword implements IBookUpgradeable {

	public ItemPrecisionSword() {
		
		super(ToolMaterial.DIAMOND);
		setRegistryName("precision.sword");
		setUnlocalizedName(UniqueCrops.MOD_ID + ".precision.sword");
		setCreativeTab(UniqueCrops.TAB);
		UCItems.items.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> list, ITooltipFlag whatisthis) {
		
		super.addInformation(stack, player, list, whatisthis);
		if (getLevel(stack) > -1) {
			int upgradelevel = getLevel(stack);
			list.add(TextFormatting.GOLD + "+" + upgradelevel);
		}
		else
			list.add(TextFormatting.GOLD + "Upgradeable");
	}
	
	@Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		
		if (!target.world.isRemote && target.hurtResistantTime > 0) {
			int upgradelevel = getLevel(stack);
			if (upgradelevel == 10)
				target.hurtResistantTime = 0;
		}
		return super.hitEntity(stack, target, attacker);
	}
	
	@Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
    	
		boolean sametool = toRepair.getItem() == repair.getItem();
		boolean flag = repair.getItem() == UCItems.generic && repair.getItemDamage() == EnumItems.PREGEM.ordinal();
		return sametool || flag;
    }
	
	@Override
	public int getLevel(ItemStack stack) {

		return NBTUtils.getInt(stack, ItemGeneric.TAG_UPGRADE, -1);
	}

	@Override
	public void setLevel(ItemStack stack, int level) {

		NBTUtils.setInt(stack, ItemGeneric.TAG_UPGRADE, level);
	}
}
