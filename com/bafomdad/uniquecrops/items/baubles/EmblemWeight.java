package com.bafomdad.uniquecrops.items.baubles;

import java.util.Random;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;

import com.bafomdad.uniquecrops.core.enums.EnumEmblems;
import com.bafomdad.uniquecrops.entities.EntityItemDonk;
import com.google.common.collect.Multimap;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EmblemWeight extends ItemBauble {

	public EmblemWeight() {
		
		super(EnumEmblems.WEIGHT);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public String getDescription() {

		return "weight";
	}

	@Override
	void fillModifiers(Multimap<String, AttributeModifier> attributes, ItemStack stack) {}
	
	@SubscribeEvent
	public void onItemToss(ItemTossEvent event) {
		
		if (event.getPlayer() == null) return;
		
		ItemStack emblem = BaublesApi.getBaublesHandler(event.getPlayer()).getStackInSlot(6);
		if (emblem.isEmpty() || (!emblem.isEmpty() && emblem.getItem() != this)) return;
		
//		if (event.getEntityItem().getItem().getItem() == Item.getItemFromBlock(Blocks.ANVIL)) {
			EntityItemDonk ei = new EntityItemDonk(event.getEntityItem().world, event.getEntityItem(), event.getEntityItem().getItem());
			ei.getEntityData().setBoolean("UC:CanDonk", true);
			EntityPlayer player = event.getPlayer();
			Random rand = new Random();
			event.setCanceled(true);
			
            //float f2 = 0.3F;
			float f2 = 1.3F;
            ei.motionX = (double)(-MathHelper.sin(player.rotationYaw * 0.017453292F) * MathHelper.cos(player.rotationPitch * 0.017453292F) * f2);
            ei.motionZ = (double)(MathHelper.cos(player.rotationYaw * 0.017453292F) * MathHelper.cos(player.rotationPitch * 0.017453292F) * f2);
            ei.motionY = (double)(-MathHelper.sin(player.rotationPitch * 0.017453292F) * f2 + 0.1F);
            float f3 = rand.nextFloat() * ((float)Math.PI * 2F);
            f2 = 0.02F * rand.nextFloat();
            ei.motionX += Math.cos((double)f3) * (double)f2;
            ei.motionY += (double)((rand.nextFloat() - rand.nextFloat()) * 0.1F);
            ei.motionZ += Math.sin((double)f3) * (double)f2;

            if (!player.world.isRemote)
            	player.world.spawnEntity(ei);
//		}
	}
}
