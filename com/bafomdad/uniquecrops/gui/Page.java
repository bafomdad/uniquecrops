package com.bafomdad.uniquecrops.gui;

import org.lwjgl.opengl.GL11;

import com.bafomdad.uniquecrops.api.ICropBook;
import com.bafomdad.uniquecrops.core.EnumItems;
import com.bafomdad.uniquecrops.core.GrowthSteps;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;

public abstract class Page {

	public static GuiBookGuide gui;
	protected Minecraft mc = Minecraft.getMinecraft();
	protected int drawX, drawY, wordWrap;
	
	public Page(GuiBookGuide screen) {
		
		this.gui = screen;
		this.drawX = (screen.width - screen.WIDTH) / 2 + 25;
		this.drawY = (screen.height - screen.HEIGHT) / 2 + 15;
		this.wordWrap = this.gui.WIDTH - 40;
	}
	
	public void draw() {
		
		GL11.glColor4f(1f, 1f, 1f, 1f);
	}
	
	public void drawSplitStringWithShadow(String str, int x, int y, int wrapWidth, int textColor) {
		
		mc.fontRenderer.drawSplitString(str, x + 1, y + 1, wrapWidth, 0);
		mc.fontRenderer.drawSplitString(str, x, y, wrapWidth, textColor);
	}
	
	public static void loadPages(GuiBookGuide screen) {
		
		GuiBookGuide.pageList.clear();
		GuiBookGuide.pageList.add(new PageTitle(screen, UCItems.generic.createStack(EnumItems.GUIDE).getDisplayName()));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.INTRO));
		GuiBookGuide.pageList.add(new PageText(screen, TextFormatting.UNDERLINE + "Categories, Page 1:"));
		GuiBookGuide.pageList.add(new PageText(screen, TextFormatting.UNDERLINE + "Categories, Page 2:"));
		for (Block crop : UCBlocks.blocks) {
			if (crop instanceof ICropBook) {
				ICropBook book = (ICropBook)crop;
				GuiBookGuide.pageList.add(new PageImage(screen, crop, I18n.format(crop.getTranslationKey() + ".name")));
				GuiBookGuide.pageList.add(new PageText(screen, book.getBookDescription()));
			}
		}
		if (gui.reader != null && gui.book.getTagCompound().hasKey(GrowthSteps.TAG_GROWTHSTAGES))
			GuiBookGuide.pageList.add(new PageGrowthList(screen));
	}
}
