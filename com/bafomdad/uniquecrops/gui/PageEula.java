package com.bafomdad.uniquecrops.gui;

import org.lwjgl.opengl.GL11;

import com.bafomdad.uniquecrops.UniqueCrops;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public abstract class PageEula {

	public static GuiBookEula gui;
	protected Minecraft mc = Minecraft.getMinecraft();
	protected int drawX, drawY, wordWrap;
	
	public PageEula(GuiBookEula screen) {
		
		this.gui = screen;
		this.drawX = (screen.width - screen.WIDTH) / 2 + 25;
		this.drawY = (screen.height - screen.HEIGHT) / 2 + 15;
		this.wordWrap = this.gui.WIDTH - 40;
	}
	
	public void draw() {
		
		GL11.glColor4f(1f, 1f, 1f, 1f);
	}
	
	public static void loadPages(GuiBookEula screen) {
		
		GuiBookEula.pageList.clear();
		for (int i = 0; i < 8; i++)
			GuiBookEula.pageList.add(new EulaText(screen, I18n.format(UniqueCrops.MOD_ID + ".eula.text" + i)));
	}
	
	public static class EulaText extends PageEula {

		private String text;
		
		public EulaText(GuiBookEula screen, String text) {
			
			super(screen);
			this.text = text;
		}
		
		@Override
		public void draw() {
			
			super.draw();
			mc.fontRenderer.drawSplitString(I18n.format(text), this.drawX, this.drawY, this.wordWrap, 0);
		}
	}
}
