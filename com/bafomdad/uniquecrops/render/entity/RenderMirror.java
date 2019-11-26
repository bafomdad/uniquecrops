package com.bafomdad.uniquecrops.render.entity;

import javax.annotation.Nonnull;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import com.bafomdad.uniquecrops.blocks.tiles.TileMirror;
import com.bafomdad.uniquecrops.entities.EntityMirror;
import com.bafomdad.uniquecrops.render.WorldRenderHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;

public class RenderMirror extends TileEntitySpecialRenderer<TileMirror> {

	@Override
	public void render(@Nonnull TileMirror te, double x, double y, double z, float partialTicks, int digProgress, float partialTick) {
	
		if (te != null && te.getWorld() != null) {
			if (TileEntityRendererDispatcher.instance.entity instanceof EntityMirror) return;
			EntityMirror entityMirror = te.getMirrorEntity();
			if (entityMirror == null) return;
			if (!WorldRenderHandler.hasKey(entityMirror)) {
				WorldRenderHandler.generateTexture(entityMirror);
				return;
			}
			
			entityMirror.rendering = true;
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			GlStateManager.enableAlpha();
			GlStateManager.alphaFunc(GL11.GL_GREATER, 0.00625F);
			GlStateManager.disableLighting();
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
			GlStateManager.pushMatrix();
			{
				GlStateManager.color(1.0F, 1.0F, 1.0F);
				int textureID = WorldRenderHandler.getValue(entityMirror);
				GlStateManager.bindTexture(textureID);
				
				EnumFacing facing = entityMirror.getAdjustedHorizontalFacing();
				switch (facing) {
					case EAST: GlStateManager.translate(x + 0.57D, y, z + 0.5D); break;
					case WEST: GlStateManager.translate(x + 0.43D, y, z + 0.5D); break;
					case NORTH: GlStateManager.translate(x + 0.5D, y, z + 0.43D); break;
					case SOUTH: GlStateManager.translate(x + 0.5D, y, z + 0.57D); break;
					default: break;
				}
				if (facing == EnumFacing.EAST || facing == EnumFacing.WEST)
					GlStateManager.rotate(-facing.getHorizontalAngle(), 0.0F, 1.0F, 0.0F);
				else
					GlStateManager.rotate(facing.getHorizontalAngle(), 0.0F, 1.0F, 0.0F);
				GlStateManager.translate(-x - 0.5D, -y, -z - 0.5D);
				
				Tessellator tess = Tessellator.getInstance();
				BufferBuilder buffer = tess.getBuffer();
				
				buffer.setTranslation(x + 0.5D, y, z + 0.5D);
				{
					buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
					
					final double w_one = 1 / 2.0D;
					final double w_zero = -w_one;
					final double h_one = 2.0D;
					final double h_zero = 0.0D;
					final double z_offset = 0.0001D;

					buffer.pos(w_zero, h_zero, z_offset).tex(1.0D, 0.0D).endVertex();
					buffer.pos(w_one, h_zero, z_offset).tex(0.0D, 0.0D).endVertex();
					buffer.pos(w_one, h_one, z_offset).tex(0.0D, 1.0D).endVertex();
					buffer.pos(w_zero, h_one, z_offset).tex(1.0D, 1.0D).endVertex();
				}
				tess.draw();
				buffer.setTranslation(0, 0, 0);
			}
			GlStateManager.popMatrix();
			GlStateManager.disableBlend();
			GlStateManager.enableLighting();
			GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
			GlStateManager.disableAlpha();
			int light = te.getWorld().getCombinedLight(te.getPos(), 0);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)(light % 65536) / 1.0F, (float)(light / 65536) / 1.0F);
		}
	}
	
	@Override
	public boolean isGlobalRenderer(TileMirror te) {
		
		return te.getMirrorEntity() != null;
	}
}
