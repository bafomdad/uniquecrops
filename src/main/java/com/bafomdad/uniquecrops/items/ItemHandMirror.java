package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCItems;

import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemHandMirror extends Item {

	public ItemHandMirror() {
		
		setRegistryName("handmirror");
		setUnlocalizedName(UniqueCrops.MOD_ID + ".handmirror");
		setCreativeTab(UniqueCrops.TAB);
		setMaxDamage(128);
		setMaxStackSize(1);
		UCItems.items.add(this);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void reflectLazers(LivingAttackEvent event) {
		
		if (event.getEntityLiving() instanceof EntityPlayer && event.getSource().getTrueSource() instanceof EntityGuardian) {
			ItemStack mirror = ((EntityPlayer)event.getEntityLiving()).getHeldItemOffhand();
			if (!mirror.isEmpty() && mirror.getItem() == this) {
				float damage = event.getAmount();
				event.getSource().getTrueSource().attackEntityFrom(EntityDamageSource.MAGIC, damage);
				event.setCanceled(true);
				mirror.damageItem(1, event.getEntityLiving());
			}
		}
	}
}
