package com.bafomdad.uniquecrops.gui;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.UCStrings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiButtonLink extends GuiButton {
	
	final GuiBookGuide gui;
	private String[] desc;
	int offset;
	
	public int selectedOption = -1;

	public GuiButtonLink(GuiBookGuide gui, int id, int x, int y, int w, int h, String... desc) {
		
		super(id, x, y, w, h, "");
		this.gui = gui;
		this.desc = desc;
	}
	
	int getFontHeight() {
		
		return (int)(gui.mc.fontRenderer.FONT_HEIGHT * 1F);
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		
		if (this.visible)
		{
			int mmY = mouseY - this.y;
			GlStateManager.pushMatrix();
			GlStateManager.scale(1F, 1F, 1F);
			GlStateManager.translate(x / 1F, y / 1F, 0);
			GlStateManager.color(1, 1, 1);
			this.hovered = mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
			for (int i = 0; i < desc.length; i++) 
			{
				int color = Color.black.getRGB();
				if (hovered && mmY >= i * getFontHeight() && mmY < (i + 1) * getFontHeight())
					color = Color.orange.getRGB();
				if (i != 0)
					GlStateManager.translate(0, getFontHeight(), 0);
				int j = offset + i;
				if (j > desc.length - 1)
					j = desc.length - 1;
				String s = desc[j];
				Minecraft.getMinecraft().fontRenderer.drawString(s, 0, 0, color, false);
			}
			GlStateManager.scale(1 / 1F, 1 / 1F, 1 / 1F);
			GlStateManager.popMatrix();
		}
	}
	
	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		
		boolean b = super.mousePressed(mc, mouseX, mouseY);
		selectedOption = -1;
		if (b) {
			int mmY = mouseY - this.y;
			for (int i = offset; i < desc.length; i++) {
				if (mmY >= i * getFontHeight() && mmY < (i + 3) * getFontHeight())
					selectedOption = i;
			}
		}
		return selectedOption != -1;
	}
}
