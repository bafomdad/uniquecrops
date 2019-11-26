package com.bafomdad.uniquecrops.render;

import java.util.ArrayDeque;
import java.util.Queue;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.bafomdad.uniquecrops.UniqueCrops;

public class FXSpark extends Particle {
	
	public static final ResourceLocation TEX = new ResourceLocation(UniqueCrops.MOD_ID, "textures/models/spark.png");

	private static final Queue<FXSpark> queuedRenders = new ArrayDeque();
	
	private float f;
	private float f1;
	private float f2;
	private float f3;
	private float f4;
	private float f5;
	
	public FXSpark(World world, double d0, double d1, double d2, float size, float red, float green, float blue, float mX, float mY, float mZ, float maxAgeMul) {
		
		super(world, d0, d1, d2, 0.0D, 0.0D, 0.0D);
		this.particleRed = red;
		this.particleGreen = green;
		this.particleBlue = blue;
		this.particleAlpha = 0.5F;
		this.motionX = mX;
		this.motionY = mY;
		this.motionZ = mZ;
		this.particleMaxAge = (int)(28D / (Math.random() * 0.3D + 0.7D) * maxAgeMul);
		this.particleAngle = rand.nextFloat();
		this.particleScale = (size * rand.nextFloat()) + 0.15F;
		
		this.prevPosX = posX;
		this.prevPosY = posY;
		this.prevPosZ = posZ;
	}
	
	public static void dispatchQueuedRenders(Tessellator tess) {
		
		GlStateManager.color(1.0F, 1.0F, 1.0F, 0.75F);
		Minecraft.getMinecraft().renderEngine.bindTexture(TEX);
		
		if (!queuedRenders.isEmpty()) {
			tess.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);
			for (FXSpark spark : queuedRenders)
				spark.renderQueued(tess);
			tess.draw();
		}
		queuedRenders.clear();
	}
	
	private void renderQueued(Tessellator tess) {
		
		float agescale = (float)particleAge / 1.0F;
		if (agescale > 1F)
			agescale = 2 - agescale;

		float f10 = 0.5F * particleScale;
		float f11 = (float)(prevPosX + (posX - prevPosX) * f - interpPosX);
		float f12 = (float)(prevPosY + (posY - prevPosY) * f - interpPosY);
		float f13 = (float)(prevPosZ + (posZ - prevPosZ) * f - interpPosZ);
		int combined = 15 << 20 | 15 << 4;
		int k3 = combined >> 16 & 0xFFFF;
		int l3 = combined & 0xFFFF;
		tess.getBuffer().pos(f11 - f1 * f10 - f4 * f10, f12 - f2 * f10, f13 - f3 * f10 - f5 * f10).tex(0, 1).lightmap(k3, l3).color(particleRed, particleGreen, particleBlue, 0.5F).endVertex();
		tess.getBuffer().pos(f11 - f1 * f10 + f4 * f10, f12 + f2 * f10, f13 - f3 * f10 + f5 * f10).tex(1, 1).lightmap(k3, l3).color(particleRed, particleGreen, particleBlue, 0.5F).endVertex();
		tess.getBuffer().pos(f11 + f1 * f10 + f4 * f10, f12 + f2 * f10, f13 + f3 * f10 + f5 * f10).tex(1, 0).lightmap(k3, l3).color(particleRed, particleGreen, particleBlue, 0.5F).endVertex();
		tess.getBuffer().pos(f11 + f1 * f10 - f4 * f10, f12 - f2 * f10, f13 + f3 * f10 - f5 * f10).tex(0, 0).lightmap(k3, l3).color(particleRed, particleGreen, particleBlue, 0.5F).endVertex();
	}
	
	@Override
	public void renderParticle(BufferBuilder wr, Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		
		this.f = f;
		this.f1 = f1;
		this.f2 = f2;
		this.f3 = f3;
		this.f4 = f4;
		this.f5 = f5;

		queuedRenders.add(this);
	}
}
