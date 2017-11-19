package com.bafomdad.uniquecrops.items;

import java.util.List;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.init.UCItems;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemPoncho extends ItemArmor implements IBookUpgradeable {
	
	public ItemPoncho(ItemArmor.ArmorMaterial material, int renderindex, EntityEquipmentSlot slot) {
		
		super(material, renderindex, slot);
		setRegistryName("poncho");
		setUnlocalizedName(UniqueCrops.MOD_ID + ".poncho");
		setCreativeTab(UniqueCrops.TAB);
		setMaxDamage(112);
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
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		
		player.jumpMovementFactor = (0.025F + 1 * 0.02F);
		if (player.motionY < -0.175F && !player.onGround && !player.capabilities.isFlying && !player.isSneaking()) {
			float fallVel = -0.175F;
			player.motionY = fallVel;
			player.fallDistance = 0;
		}
	}
	
	@Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		
		boolean flag = repair.getItem() == UCItems.generic && repair.getItemDamage() == 12;
		return toRepair.getItem() == this && flag;
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
