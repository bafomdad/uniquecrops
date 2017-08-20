package com.bafomdad.uniquecrops.items.baubles;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;

import com.bafomdad.uniquecrops.core.EnumEmblems;
import com.google.common.collect.Multimap;

public class EmblemTransformation extends ItemBauble {

	public EmblemTransformation() {
		
		super(EnumEmblems.TRANSFORMATION);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public String getDescription() {

		return "transformation";
	}

	@Override
	public BaubleType getBaubleType(ItemStack stack) {

		return BaubleType.CHARM;
	}
	
	@SubscribeEvent
	public void onHitEntity(LivingHurtEvent event) {
		
		if (event.getAmount() <= 0 || event.getEntityLiving() instanceof EntityPlayer) return;
		
		if (!(event.getSource().getSourceOfDamage() instanceof EntityPlayer)) return;
		
		ItemStack transformer = BaublesApi.getBaublesHandler((EntityPlayer)event.getSource().getSourceOfDamage()).getStackInSlot(6);
		if (transformer == null || (transformer != null && transformer.getItem() != this)) return;
		
		Random rand = new Random();
		if (rand.nextInt(100) != 0) return;
		
		EntityLivingBase elb = event.getEntityLiving();
		List<String> entities = new ArrayList<String>(EntityList.ENTITY_EGGS.keySet());
		String randomString = entities.get(rand.nextInt(entities.size()));
		Entity entity = EntityList.createEntityByName(randomString, elb.worldObj);
		if (!entity.isNonBoss()) return;
		entity.setPositionAndRotation(elb.posX, elb.posY, elb.posZ, elb.rotationYaw, elb.rotationPitch);
		
		elb.worldObj.spawnEntityInWorld(entity);
		elb.setDead();
	}
	
	@Override
	void fillModifiers(Multimap<String, AttributeModifier> attributes, ItemStack stack) {}
}
