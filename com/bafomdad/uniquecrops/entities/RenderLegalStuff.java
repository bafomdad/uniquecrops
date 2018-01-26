package com.bafomdad.uniquecrops.entities;

import java.awt.Color;
import java.util.List;

import scala.actors.threadpool.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderLegalStuff extends Render<EntityLegalStuff> {
	
	public static final Factory FACTORY = new Factory();
	private static int RADIUS = 70;

	public RenderLegalStuff(RenderManager renderManager) {
		
		super(renderManager);
	}
	
	@Override
	public void doRender(EntityLegalStuff entity, double x, double y, double z, float entityYaw, float partialTicks) {
		
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
		
		if (entity.ticksExisted < (entity.textList.size() * 20) + 5000) {
			
			List<String> strList = entity.textList;
			Minecraft mc = Minecraft.getMinecraft();
	
			int timestep = (int)((System.currentTimeMillis()) % 10000);
			double angle = timestep * Math.PI / 5000.0;
			float angleTime = (float)(angle * 57.2957795131);
			
			GlStateManager.pushMatrix();
			GlStateManager.translate((float)x, (float)y + entity.height + 0.5F, (float)z);
			GlStateManager.disableLighting();
			
			//System.out.println(angleTime);
			//GlStateManager.rotate((float)(angle * 57.2957795131), 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
	        GlStateManager.scale(-0.025, -0.025, -0.025);
	
	        int t = entity.ticksExisted / 5;
//	        for (int i = 0; i < RADIUS; i++) {
	        	//String s = entity.str.substring(t, t + RADIUS);
	        String stringer = "To be fair, it takes a very high IQ to be able to enjoy watching Rick and Morty.";
	        if (t <= stringer.length()) {
	        	String s = stringer.substring(t, Math.min(t + RADIUS, stringer.length()));
	        	if (stringer.length() < t + RADIUS) {
	        		StringBuilder sb = new StringBuilder(s);
	        		sb.append(' ');
	        		s = sb.toString();
	        	}
	        	if (!s.trim().isEmpty()) {
		        	for (int j = 0; j < s.length(); j++) {
		        		char c = s.charAt(j);
		        		GlStateManager.pushMatrix();
		        		GlStateManager.translate(0, -360.0F, 0);
		        		mc.fontRenderer.drawString(Character.toString(c), 0, 270, Color.WHITE.getRGB());
		        		GlStateManager.popMatrix();
		        		GlStateManager.rotate((float)mc.fontRenderer.getStringWidth(Character.toString(c)), 0.0F, 0.0F, 1.0F);
		        	}
	        	}
	        }
//	        }
//	        if (!strList.isEmpty()) {
//		        for (int i = 0; i < Math.min(RADIUS, strList.size()); i++) {
//		        	String s = strList.get(i);
//		        	GlStateManager.pushMatrix();
//		            GlStateManager.translate(0, -360.0F, 0);
//		            mc.fontRenderer.drawString(s, 0, 270, Color.WHITE.getRGB());
//		        	GlStateManager.popMatrix();
//		        	GlStateManager.rotate((float)mc.fontRenderer.getStringWidth(s), 0.0F, 0.0F, 1.0F);
//		        }
//			    if (entity.ticksExisted % 10 == 0)
//		        	entity.textList.remove(0);
//	        }
			GlStateManager.enableLighting();
			GlStateManager.popMatrix();
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLegalStuff entity) {

		return null;
	}
	
	public static class Factory implements IRenderFactory<EntityLegalStuff> {
		
		@Override
		public Render<? super EntityLegalStuff> createRenderFor(RenderManager manager) {
			
			return new RenderLegalStuff(manager);
		}
	}
}
