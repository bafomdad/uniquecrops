package com.bafomdad.uniquecrops.items.baubles;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.bafomdad.uniquecrops.core.enums.EnumEmblems;
import com.google.common.collect.Multimap;

public class EmblemBookworm extends ItemBauble {

	public EmblemBookworm() {
		
		super(EnumEmblems.BOOKWORM);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public String getDescription() {

		return "bookworm";
	}

	@Override
	void fillModifiers(Multimap<String, AttributeModifier> attributes, ItemStack stack) {}
	
	@SubscribeEvent
	public void onItemRightclick(PlayerInteractEvent.RightClickItem event) {
		
		ItemStack book = event.getItemStack();
		if (book.getItem() == Items.ENCHANTED_BOOK) {
			EntityPlayer player = event.getEntityPlayer();
			if (!this.hasBauble(player))
				event.setCanceled(true);
		}
	}
}
