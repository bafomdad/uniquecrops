package com.bafomdad.uniquecrops.items;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.init.UCItems;

public class ItemMagnet extends Item implements IBookUpgradeable {

	public ItemMagnet() {
		
		setRegistryName("item_magnet");
		setTranslationKey(UniqueCrops.MOD_ID + ".item_magnet");
		setCreativeTab(UniqueCrops.TAB);
		setMaxStackSize(1);
		UCItems.items.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> list, ITooltipFlag whatisthis) {
		
		list.add(I18n.format(UCStrings.TOOLTIP + "item_magnet"));
		int upgradeLevel = getLevel(stack);
		if (upgradeLevel > -1) {
			list.add(TextFormatting.GOLD + "+" + upgradeLevel);
		}
		else
			list.add(TextFormatting.GOLD + "Upgradeable");
		list.add((NBTUtils.getBoolean(stack, UCStrings.ITEM_ACTIVATED, false) ? TextFormatting.GREEN + "Active" : TextFormatting.RED + "Inactive"));
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {
		
		if (entity instanceof EntityPlayer && !world.isRemote && NBTUtils.getBoolean(stack, UCStrings.ITEM_ACTIVATED, false)) {
			EntityPlayer player = (EntityPlayer)entity;
			if (player.isCreative() || player.isSpectator()) return;
			
			if (!player.isSneaking()) {
				int range = 2 + Math.max(this.getLevel(stack), 0);
				List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(player.posX - range, player.posY - range, player.posZ - range, player.posX + range, player.posY + range, player.posZ + range));
				if (!items.isEmpty()) {
					for (EntityItem ei : items) {
						if (!ei.isDead && !ei.cannotPickup()) {
							ei.onCollideWithPlayer(player);
						}
					}
				}
			}
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		
		ItemStack stack = player.getHeldItem(hand);
		if (!world.isRemote && player.isSneaking()) {
			NBTUtils.setBoolean(stack, UCStrings.ITEM_ACTIVATED, !NBTUtils.getBoolean(stack, UCStrings.ITEM_ACTIVATED, false));
			return new ActionResult(EnumActionResult.SUCCESS, stack);
		}
		return super.onItemRightClick(world, player, hand);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		
		return EnumRarity.EPIC;
	}
	
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		
		return NBTUtils.getBoolean(stack, UCStrings.ITEM_ACTIVATED, false);
	}
}
