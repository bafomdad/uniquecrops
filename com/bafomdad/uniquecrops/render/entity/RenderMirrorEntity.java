package com.bafomdad.uniquecrops.render.entity;

import javax.annotation.Nullable;

import com.bafomdad.uniquecrops.entities.EntityMirror;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderMirrorEntity extends Render<EntityMirror> {

	public static final Factory FACTORY = new Factory();
	
	protected RenderMirrorEntity(RenderManager manager) {
		
		super(manager);
	}
	
	@Nullable
	@Override
	protected ResourceLocation getEntityTexture(EntityMirror mirror) {
		
		return null;
	}
	
	public static class Factory implements IRenderFactory<EntityMirror> {
		
		@Override
		public Render<? super EntityMirror> createRenderFor(RenderManager manager) {
			
			return new RenderMirrorEntity(manager);
		}
	}
}
