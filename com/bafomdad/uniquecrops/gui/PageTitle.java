package com.bafomdad.uniquecrops.gui;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.bafomdad.uniquecrops.UniqueCrops;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class PageTitle extends Page {

	final ResourceLocation TITLE = new ResourceLocation(UniqueCrops.MOD_ID, "textures/gui/titlepage.png");
	private String title;
	
	public PageTitle(GuiBookGuide screen, String title) {
		
		super(screen);
		this.title = title;
	}
	
	@Override
	public void draw() {
		
		super.draw();
		if (this.title != null) {
			
			TextureManager render = mc.renderEngine;
			render.bindTexture(TITLE);
			
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GlStateManager.color(1F, 1F, 1F, 1F);
			int k = (gui.width - gui.WIDTH) / 2;
			gui.drawTexturedModalRect(k, 5, 0, 0, gui.WIDTH, gui.HEIGHT);
			drawSplitStringWithShadow(I18n.format(this.title), drawX + 10, drawY + 140, this.wordWrap, Color.gray.getRGB());
			gui.drawCenteredString(mc.fontRenderer, "-by " + gui.reader.getName(), drawX + 60, drawY + 155, Color.lightGray.getRGB());
			GlStateManager.disableBlend();
		}
	}
}
