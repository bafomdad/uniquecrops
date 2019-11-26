package com.bafomdad.uniquecrops.render.entity;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.bafomdad.uniquecrops.entities.EntityItemCooking;

public class RenderCookingItem extends RenderEntityItem {
	
	public static final Factory FACTORY = new Factory();

	protected RenderCookingItem(RenderManager manager) {
		
		super(manager, Minecraft.getMinecraft().getRenderItem());
	}
	
	@Override
    public void doRender(EntityItem entity, double x, double y, double z, float entityYaw, float partialTicks) {

		String str = ((EntityItemCooking)entity).getCookingTime() + "%";
		this.renderLivingLabel(entity, str, x, y, z, 16);
		
		super.doRender(entity, x, y - 0.2, z, entityYaw, partialTicks);
    }
	
	public static class Factory implements IRenderFactory<EntityItemCooking> {

		@Override
		public Render<? super EntityItemCooking> createRenderFor(RenderManager manager) {

			return new RenderCookingItem(manager);
		}
	}
}
