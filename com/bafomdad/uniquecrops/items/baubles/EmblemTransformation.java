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
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;

import com.bafomdad.uniquecrops.core.enums.EnumEmblems;
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

	@SubscribeEvent
	public void onHitEntity(LivingHurtEvent event) {
		
		if (event.getAmount() <= 0 || event.getEntityLiving() instanceof EntityPlayer) return;
		
		if (!(event.getSource().getImmediateSource() instanceof EntityPlayer)) return;
		
		ItemStack transformer = BaublesApi.getBaublesHandler((EntityPlayer)event.getSource().getImmediateSource()).getStackInSlot(6);
		if (transformer.isEmpty() || (!transformer.isEmpty() && transformer.getItem() != this)) return;
		
		Random rand = new Random();
		if (rand.nextInt(100) != 0) return;
		
		EntityLivingBase elb = event.getEntityLiving();
		List<ResourceLocation> entities = new ArrayList<ResourceLocation>(EntityList.ENTITY_EGGS.keySet());
		ResourceLocation res = entities.get(rand.nextInt(entities.size()));
		Entity entity = EntityList.createEntityByIDFromName(res, elb.world);
		if (!entity.isNonBoss()) return;
		entity.setPositionAndRotation(elb.posX, elb.posY, elb.posZ, elb.rotationYaw, elb.rotationPitch);
		
		elb.world.spawnEntity(entity);
		elb.setDead();
	}
	
	@Override
	void fillModifiers(Multimap<String, AttributeModifier> attributes, ItemStack stack) {}
}
