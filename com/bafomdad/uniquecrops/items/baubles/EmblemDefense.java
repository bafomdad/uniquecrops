package com.bafomdad.uniquecrops.items.baubles;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;

import com.bafomdad.uniquecrops.core.EnumEmblems;
import com.google.common.collect.Multimap;

public class EmblemDefense extends ItemBauble {

	public EmblemDefense() {
		
		super(EnumEmblems.DEFENSE);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public String getDescription() {

		return "defense";
	}

	@Override
	public BaubleType getBaubleType(ItemStack stack) {

		return BaubleType.CHARM;
	}
	
	@SubscribeEvent
	public void autoShield(LivingAttackEvent event) {
		
		if (!(event.getEntityLiving() instanceof EntityPlayer)) return;
		if (!(event.getSource().getSourceOfDamage() instanceof EntityArrow)) return;
		
		ItemStack shield = ((EntityPlayer)event.getEntityLiving()).getHeldItemOffhand();
		ItemStack emblem = BaublesApi.getBaublesHandler((EntityPlayer)event.getEntityLiving()).getStackInSlot(6);
		if (shield == null || emblem == null) return;
		if (emblem.getItem() != this || !(shield.getItem() instanceof ItemShield)) return;
		
		shield.attemptDamageItem(1, event.getEntityLiving().worldObj.rand);
		event.setCanceled(true);
	}

	@Override
	void fillModifiers(Multimap<String, AttributeModifier> attributes, ItemStack stack) {}
}
