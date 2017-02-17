package com.bafomdad.uniquecrops.items;

import java.util.List;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.IBookUpgradeable;
import com.bafomdad.uniquecrops.init.UCItems;

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
		GameRegistry.register(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean whatisthis) {
		
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey(ItemGeneric.TAG_UPGRADE)) {
			int upgradelevel = stack.getTagCompound().getInteger(ItemGeneric.TAG_UPGRADE);
			list.add(TextFormatting.GOLD + "+" + upgradelevel);
		}
		else
			list.add(TextFormatting.GOLD + "Upgradeable");
	}
	
	@Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		
		player.jumpMovementFactor = (0.025F + 1 * 0.02F);
		if (player.motionY < -0.175F && !player.onGround && !player.capabilities.isFlying) {
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
}
