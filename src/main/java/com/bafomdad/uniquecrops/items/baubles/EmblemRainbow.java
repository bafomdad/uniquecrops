package com.bafomdad.uniquecrops.items.baubles;

import java.util.List;
import java.util.Random;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;

import com.bafomdad.uniquecrops.core.EnumEmblems;
import com.google.common.collect.Multimap;

public class EmblemRainbow extends ItemBauble {

	public EmblemRainbow() {
		
		super(EnumEmblems.RAINBOW);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public String getDescription() {

		return "rainbow";
	}

	@Override
	public BaubleType getBaubleType(ItemStack stack) {

		return BaubleType.CHARM;
	}
	
	@SubscribeEvent
	public void onSheared(EntityInteractSpecific event) {
		
		ItemStack rainbow = BaublesApi.getBaublesHandler((EntityPlayer)event.getEntityPlayer()).getStackInSlot(6);
		if (rainbow.isEmpty() || (!rainbow.isEmpty() && rainbow.getItem() != this)) return;
		
		if (!(event.getTarget() instanceof IShearable)) return;
		if (!(event.getTarget() instanceof EntitySheep) || (event.getTarget() instanceof EntitySheep && ((EntitySheep)event.getTarget()).getSheared())) return;
		if (event.getItemStack().isEmpty() || (!event.getItemStack().isEmpty() && !(event.getItemStack().getItem() instanceof ItemShears))) return;
		
		int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, event.getItemStack());
		if (!event.getWorld().isRemote) {
			List<ItemStack> wools = ((IShearable)event.getTarget()).onSheared(event.getItemStack(), event.getWorld(), event.getPos(), fortune);
			for (ItemStack is : wools) {
				Random rand = new Random();
				is.setItemDamage(rand.nextInt(15));
				EntityItem wool = new EntityItem(event.getWorld(), event.getTarget().posX, event.getTarget().posY, event.getTarget().posZ, is);
				event.getWorld().spawnEntity(wool);
			}
		}
	}

	@Override
	void fillModifiers(Multimap<String, AttributeModifier> attributes, ItemStack stack) {}
}
