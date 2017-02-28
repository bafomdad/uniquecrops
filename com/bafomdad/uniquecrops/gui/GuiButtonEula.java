package com.bafomdad.uniquecrops.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class GuiButtonEula extends GuiButton {
	
	private final boolean agree;

	public GuiButtonEula(int id, int x, int y, boolean agree) {
		
		super(id, x, y, 16, 16, "");
		this.agree = agree;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		
		if (visible) {
			boolean mouseOver = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			mc.renderEngine.bindTexture(GuiBookEula.texture);
			int u = 175;
			int v = 34;
			
			if (mouseOver)
				v += 17;
			
			if (agree)
				u += 17;
			
			drawTexturedModalRect(this.xPosition, this.yPosition, u, v, 16, 16);
		}
	}
}
