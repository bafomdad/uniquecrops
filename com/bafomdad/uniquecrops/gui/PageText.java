package com.bafomdad.uniquecrops.gui;

import net.minecraft.client.resources.I18n;

public class PageText extends Page {

	private String text;
	
	public PageText(GuiAbstractBook screen, String text) {
		
		super(screen);
		this.text = text;
	}
	
	@Override
	public void draw() {
		
		super.draw();
		gui.mc.fontRenderer.drawSplitString(I18n.format(text), this.drawX, this.drawY, this.wordWrap, 0);
	}
}
