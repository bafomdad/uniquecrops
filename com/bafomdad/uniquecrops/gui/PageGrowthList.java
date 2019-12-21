package com.bafomdad.uniquecrops.gui;

import java.util.List;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.core.enums.EnumGrowthSteps;
import com.bafomdad.uniquecrops.crops.Feroxia;

public class PageGrowthList extends Page {

	public PageGrowthList(GuiBookGuide screen) {
		
		super(screen);
	}
	
	@Override
	public void draw() {
		
		super.draw();
		
		boolean uni = gui.mc.fontRenderer.getUnicodeFlag();
		gui.mc.fontRenderer.setUnicodeFlag(true);
		GlStateManager.pushMatrix();
		GlStateManager.scale(1F, 1F, 1F);
		NBTTagList taglist = gui.book.getTagCompound().getTagList(UCStrings.TAG_GROWTHSTAGES, 10);
		for (int i = 0; i < taglist.tagCount(); i++) {
			NBTTagCompound tag = taglist.getCompoundTagAt(i);
			int stage = tag.getInteger("stage" + i);
			String desc = EnumGrowthSteps.values()[stage].getDescription();
			gui.mc.fontRenderer.drawSplitString("Stage " + (i + 1) + ": " + I18n.format(desc), this.drawX, this.drawY + (i * 25), this.wordWrap, 0);
		}
		GlStateManager.popMatrix();
		gui.mc.fontRenderer.setUnicodeFlag(uni);
	}
}
