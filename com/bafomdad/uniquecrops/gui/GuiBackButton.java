package com.bafomdad.uniquecrops.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class GuiBackButton extends GuiButton {

	public GuiBackButton(int id, int x, int y) {
		
		super(id, x, y, 16, 16, "");
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		
		if (visible) {
			boolean mouseOver = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
	        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			mc.renderEngine.bindTexture(GuiBookGuide.texture);
			int u = 175;
			int v = 34;

			if (!mouseOver) {
				u += 16;
			}
			drawTexturedModalRect(xPosition, yPosition, u, v, 16, 16);
		}
	}
}
