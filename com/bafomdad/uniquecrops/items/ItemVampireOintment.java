package com.bafomdad.uniquecrops.items;

import java.util.List;
import java.util.UUID;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.init.UCItems;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class ItemVampireOintment extends Item {

	public ItemVampireOintment() {
		
		setRegistryName("vampiric_ointment");
		setTranslationKey(UniqueCrops.MOD_ID + ".vampiric_ointment");
		setCreativeTab(UniqueCrops.TAB);
		setMaxStackSize(16);
		UCItems.items.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List list, ITooltipFlag whatisthis) {
		
		if (hasTaglock(stack)) {
			list.add(TextFormatting.RED + "Tagged");
			list.add(I18n.format(UCStrings.TOOLTIP + "ointment.filled"));
		}
		else
			list.add(I18n.format(UCStrings.TOOLTIP + "ointment.empty"));
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand) {
		
		if (!(target instanceof EntityPlayer) && target.isNonBoss()) {
			if (!hasTaglock(stack) && !player.world.isRemote) {
				ItemStack newStack = new ItemStack(this);
				setTaglock(newStack, player, target);
				ItemHandlerHelper.giveItemToPlayer(player, newStack);
				stack.shrink(1);
				return true;
			}
		}
		return false;
	}
	
	public boolean hasTaglock(ItemStack stack) {
		
		return (stack.hasTagCompound() && stack.getTagCompound().hasKey(UCStrings.TAG_LOCK)) ? true : false;
	}
	
	public void setTaglock(ItemStack stack, EntityPlayer player, EntityLivingBase target) {
		
		UUID id = target.getPersistentID();
		NBTUtils.setString(stack, UCStrings.TAG_LOCK, id.toString());
	}
}
