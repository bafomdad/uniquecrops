package com.bafomdad.uniquecrops.items.baubles;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;

import com.bafomdad.uniquecrops.core.EnumEmblems;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.init.UCRecipes;
import com.bafomdad.uniquecrops.items.ItemEdibleMetal;
import com.google.common.collect.Multimap;

public class EmblemIronstomach extends ItemBauble {

	public EmblemIronstomach() {
		
		super(EnumEmblems.IRONSTOMACH);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public String getDescription() {

		return "ironstomach";
	}

	@Override
	public BaubleType getBaubleType(ItemStack stack) {

		return BaubleType.CHARM;
	}
	
	@SubscribeEvent
	public void onRightclickNonedible(RightClickItem event) {
		
		ItemStack emblem = BaublesApi.getBaublesHandler(event.getEntityPlayer()).getStackInSlot(6);
		if (emblem.isEmpty() || (!emblem.isEmpty() && emblem.getItem() != this)) return;
		
		ItemStack stack = event.getItemStack();
		if (!event.getWorld().isRemote)
			tryConvertToFood(stack, event.getEntityPlayer(), event.getHand());
	}
	
	private void tryConvertToFood(ItemStack stack, EntityPlayer player, EnumHand hand) {

		if (stack.isEmpty() || (!stack.isEmpty() && stack.getItem() instanceof ItemEdibleMetal)) return;
		for (Map.Entry<ItemStack, ItemStack> entry : UCRecipes.edibleMetals.entrySet()) {
			if (entry.getKey().getItem() == stack.getItem() && entry.getKey().getItemDamage() == stack.getItemDamage()) {
				player.setHeldItem(hand, new ItemStack(entry.getValue().getItem(), stack.getCount()));
				return;
			}
		}
	}

	@Override
	void fillModifiers(Multimap<String, AttributeModifier> attributes, ItemStack stack) {}
}
