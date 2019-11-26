package com.bafomdad.uniquecrops.render.entity;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.entities.EntityBattleCrop;
import com.bafomdad.uniquecrops.render.model.ModelBattleCrop;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderBattleCropEntity extends RenderBiped<EntityBattleCrop> {

	public static final Factory FACTORY = new Factory();
	private static final ResourceLocation CROP_TEXTURE = new ResourceLocation(UniqueCrops.MOD_ID, "textures/models/entity/model_original.png");
	
	protected RenderBattleCropEntity(RenderManager manager) {
	
		super(manager, new ModelBattleCrop(), 0.15F);
//        this.addLayer(new LayerHeldItem(this));
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityBattleCrop entity) {
		
		return CROP_TEXTURE;
	}

	public static class Factory implements IRenderFactory<EntityBattleCrop> {
		
		@Override
		public Render<? super EntityBattleCrop> createRenderFor(RenderManager manager) {
			
			return new RenderBattleCropEntity(manager);
		}
	}
}
