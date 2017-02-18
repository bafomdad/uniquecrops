package com.bafomdad.uniquecrops.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class GuiButtonPageChange extends GuiButton {
	
	private final boolean previous;
	
	public GuiButtonPageChange(int id, int x, int y, boolean previous) {
		
		super(id, x, y, 16, 16, "");
		this.previous = previous;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		
		if (visible) {
			boolean mouseOver = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
	        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			mc.renderEngine.bindTexture(GuiBookGuide.texture);
			int u = 175;
			int v = 0;

			if (mouseOver) {
				v += 17;
			}
			if (previous) {
				u += 17;
			}
			drawTexturedModalRect(this.xPosition, this.yPosition, u, v, 16, 16);
		}
	}
}
