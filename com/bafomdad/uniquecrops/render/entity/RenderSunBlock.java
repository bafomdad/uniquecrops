package com.bafomdad.uniquecrops.render.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import org.lwjgl.opengl.GL11;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.tiles.TileSunBlock;

public class RenderSunBlock extends TileEntitySpecialRenderer<TileSunBlock> {
	
	private static final ResourceLocation TEX = new ResourceLocation(UniqueCrops.MOD_ID, "textures/models/sunglow.png");

	@Override
	public void render(TileSunBlock tile, double x, double y, double z, float partialTicks, int digProgress, float partialTick) {
		
		GlStateManager.pushMatrix();
		GlStateManager.translate((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(TEX);
		Tessellator tess = Tessellator.getInstance();
//		GlStateManager.disableDepth();
		GlStateManager.disableAlpha();
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		
		float phase = (float)(Minecraft.getSystemTime() % 2000L) / 2000.0F;
		float power = tile.powerlevel * 1.0F / tile.MAX_POWER;
		if (tile.powerlevel > 0) {
			for (int i = 0; i < 7; i++) {
				GlStateManager.pushMatrix();
				GlStateManager.translate((float)Math.cos(2.356194490192345D * i) * 0.5F * power, 0.0F, (float)Math.sin(2.356194490192345D * i) * 0.5F * power);
				GlStateManager.rotate(180.0F - Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
				GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				
				BufferBuilder buff = tess.getBuffer();
				buff.begin(7, DefaultVertexFormats.POSITION_TEX);
				GlStateManager.color(Math.max(phase * 2.0F, 1.0F), Math.max(phase * 2.0F, 1.0F), Math.max(phase * 2.0F, 1.0F), 0.5F * (1.0F - phase));
				float w = 1.0F;
				float h = 40.0F * phase * power;
		        buff.pos(-0.5D * w, -0.25D, 0.0D).tex((double)0, (double)1).endVertex();
		        buff.pos(0.5D * w, -0.25D, 0.0D).tex((double)1, (double)1).endVertex();
		        buff.pos(0.5D, 0.75D * h, 0.0D).tex((double)1, (double)0).endVertex();
		        buff.pos(-0.5D, 0.75D * h, 0.0D).tex((double)0, (double)0).endVertex();
				
				tess.draw();
				phase = (float)(phase + 0.125D);
				if (phase > 1.0F)
					phase -= 1.0F;
				GlStateManager.popMatrix();
			}
		}
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
//		GlStateManager.enableDepth();
		GlStateManager.enableLighting();
		
		GlStateManager.popMatrix();
	}
}
