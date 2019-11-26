package com.bafomdad.uniquecrops.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.profiler.Profiler;

public class UCParticleSpawner {

	public static void dispatch() {
		
		Tessellator tess = Tessellator.getInstance();
		
		Profiler profiler = Minecraft.getMinecraft().profiler;
		
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		GlStateManager.depthMask(false);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.003921569F);
		GlStateManager.disableLighting();

		profiler.startSection("spark");
		FXSpark.dispatchQueuedRenders(tess);
		profiler.endSection();

		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
		GlStateManager.disableBlend();
		GlStateManager.depthMask(true);
		GL11.glPopAttrib();
	}
}
