package com.bafomdad.uniquecrops.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.bafomdad.uniquecrops.UniqueCrops;

public class PageTitleMultiblocks extends Page {
	
	final ResourceLocation TITLE = new ResourceLocation(UniqueCrops.MOD_ID, "textures/gui/titlepage2.png");

	public PageTitleMultiblocks(GuiAbstractBook screen) {
		
		super(screen);
	}
	
	@Override
	public void draw() {
		
		super.draw();
		
		this.gui.mc.renderEngine.bindTexture(TITLE);
		
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(1F, 1F, 1F, 1F);
		int k = (gui.width - gui.WIDTH) / 2;
		this.gui.drawModalRectWithCustomSizedTexture(k - 30, 5, 0, 0, 256, 256, 256, 256);
		GlStateManager.disableBlend();
	}
}
