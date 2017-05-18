package com.bafomdad.uniquecrops.gui;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public class PageImage extends Page {

	final ResourceLocation resource;
	final String text;
	
	public PageImage(GuiBookGuide screen, String resource, String text) {
		
		super(screen);
		this.resource = new ResourceLocation(resource);
		this.text = text;
	}
	
	@Override
	public void draw() {
		
		super.draw();
		
		TextureManager render = mc.renderEngine;
		render.bindTexture(resource);
		
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(1F, 1F, 1F, 1F);
		int k = (gui.width - gui.WIDTH) / 2;
		gui.drawTexturedModalRect(k + 5, 5, 0, 0, gui.WIDTH, gui.HEIGHT);
		gui.drawCenteredString(mc.fontRendererObj, text, drawX + 60, drawY + 150, Color.gray.getRGB());
		GlStateManager.disableBlend();
	}
	
	public String getText() {
		
		return text;
	}
}
