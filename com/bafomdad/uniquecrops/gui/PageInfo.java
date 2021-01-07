package com.bafomdad.uniquecrops.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;

public class PageInfo extends Page {

	final BlockCropsBase crop;
	
	public PageInfo(GuiBookGuide screen, BlockCropsBase crop) {
		
		super(screen);
		this.crop = crop;
	}
	
	@Override
	public void draw() {
		
		super.draw();
		int offset = 25;
		gui.mc.fontRenderer.drawSplitString(TextFormatting.UNDERLINE + "More info on " + I18n.format(crop.getTranslationKey() + ".name") + ":", this.drawX, this.drawY, this.wordWrap, 0);
		gui.mc.fontRenderer.drawSplitString("Can Bonemeal: " + colorBoolean(crop.getCanBonemeal()), this.drawX, this.drawY + offset, this.wordWrap, 0);
		gui.mc.fontRenderer.drawSplitString("Can click harvest: " + colorBoolean(crop.getCanClickHarvest()), this.drawX, this.drawY + (offset * 2), this.wordWrap, 0);
		gui.mc.fontRenderer.drawSplitString("Can drop extra: " + colorBoolean(crop.getCanDropExtra()), this.drawX, this.drawY + (offset * 3), this.wordWrap, 0);
		gui.mc.fontRenderer.drawSplitString("Can ignore growth restrictions: " + colorBoolean(crop.ignoresGrowthRestrictions()), this.drawX, this.drawY + (offset * 4), this.wordWrap, 0);
	}
	
	private String colorBoolean(boolean bool) {
		
		return (bool) ? TextFormatting.GREEN + "" + bool : TextFormatting.RED + "" + bool;
	}
}
