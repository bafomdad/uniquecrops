package com.bafomdad.uniquecrops.render.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.bafomdad.uniquecrops.entities.EntityMovingCrop;

public class RenderMovingCrop extends Render<EntityMovingCrop> {
	
	public static final Factory FACTORY = new Factory();

	public RenderMovingCrop(RenderManager rm) {
		
		super(rm);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityMovingCrop entity) {

		return null;
	}
	
	public static class Factory implements IRenderFactory<EntityMovingCrop> {

		@Override
		public Render<? super EntityMovingCrop> createRenderFor(RenderManager manager) {

			return new RenderMovingCrop(manager);
		}
	}
}
