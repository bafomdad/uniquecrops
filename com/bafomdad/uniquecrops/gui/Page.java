package com.bafomdad.uniquecrops.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.api.ICropBook;
import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.MultiblockPattern;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.core.enums.EnumItems;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;

public abstract class Page {

	public static GuiAbstractBook gui;
	public static int cropCount;
	protected int drawX, drawY, wordWrap;
	
	public Page(GuiAbstractBook screen) {
		
		this.gui = screen;
		this.drawX = (gui.width - gui.WIDTH) / 2 + 25;
		this.drawY = (gui.height - gui.HEIGHT) / 2 + 15;
		this.wordWrap = gui.WIDTH - 40;
	}
	
	public void draw() {
		
		GL11.glColor4f(1f, 1f, 1f, 1f);
	}
	
	public void drawScreenPost(int mouseX, int mouseY) {}
	
	public void drawSplitStringWithShadow(String str, int x, int y, int wrapWidth, int textColor) {
		
		gui.mc.fontRenderer.drawSplitString(str, x + 1, y + 1, wrapWidth, 0);
		gui.mc.fontRenderer.drawSplitString(str, x, y, wrapWidth, textColor);
	}
	
	public static void loadGuidePages(GuiBookGuide screen) {
		
		GuiBookGuide.pageList.clear();
		cropCount = 0;
		GuiBookGuide.pageList.add(new PageTitleGuide(screen, EnumItems.GUIDE.createStack().getDisplayName()));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.INTRO));
		GuiBookGuide.pageList.add(new PageText(screen, TextFormatting.UNDERLINE + "Categories, Page 1:"));
		GuiBookGuide.pageList.add(new PageText(screen, TextFormatting.UNDERLINE + "Categories, Page 2:"));
		for (Block crop : UCBlocks.blocks) {
			if (crop instanceof ICropBook) {
				ICropBook book = (ICropBook)crop;
				if (book.canIncludeInBook()) {
					cropCount++;
					GuiBookGuide.pageList.add(new PageImage(screen, crop, I18n.format(crop.getTranslationKey() + ".name")));
					GuiBookGuide.pageList.add(new PageText(screen, book.getBookDescription()));
					GuiBookGuide.pageList.add(new PageInfo(screen, (BlockCropsBase)crop));
				}
			}
		}
		if (gui.reader != null && gui.book.getTagCompound().hasKey(UCStrings.TAG_GROWTHSTAGES))
			GuiBookGuide.pageList.add(new PageGrowthList(screen));
	}
	
	public static void loadMultiblockPages(GuiBookMultiblocks screen) {
		
		GuiBookMultiblocks.pageList.clear();
		GuiBookMultiblocks.pageList.add(new PageTitleMultiblocks(screen));
		GuiBookMultiblocks.pageList.add(new PageText(screen, "Table of Contents"));
		List<MultiblockPattern> patterns = UCConfig.getPatterns();
		if (!patterns.isEmpty()) {
			GuiBookMultiblocks.pageList.add(new PageText(screen, UniqueCrops.MOD_ID + ".bookmultiblocks.page.intro"));
			for (MultiblockPattern pattern : patterns) {
				GuiBookMultiblocks.pageList.add(new PageRecipes(screen, pattern));
				GuiBookMultiblocks.pageList.add(new PageText(screen, pattern.getDescription()));
			}
		} else {
			GuiBookMultiblocks.pageList.add(new PageText(screen, "Here be dragons"));
		}
	}
	
	public static void loadEulaPages(GuiBookEula screen) {
		
		GuiBookEula.pageList.clear();
		for (int i = 0; i < 8; i++)
			GuiBookEula.pageList.add(new PageText(screen, UniqueCrops.MOD_ID + ".eula.text" + i));
	}
}
