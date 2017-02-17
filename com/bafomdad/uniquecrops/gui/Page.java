package com.bafomdad.uniquecrops.gui;

import org.lwjgl.opengl.GL11;

import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.init.UCItems;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;

public abstract class Page {

	public static GuiBookGuide gui;
	protected Minecraft mc = Minecraft.getMinecraft();
	protected int drawX, drawY, wordWrap;
	
	public Page(GuiBookGuide screen) {
		
		this.gui = screen;
		this.drawX = (screen.width - screen.WIDTH) / 2 + 25;
		this.drawY = (screen.HEIGHT - screen.HEIGHT) / 2 + 15;
		this.wordWrap = this.gui.WIDTH - 40;
	}
	
	public void draw() {
		
		GL11.glColor4f(1f, 1f, 1f, 1f);
	}
	
	public void drawSplitStringWithShadow(String str, int x, int y, int wrapWidth, int textColor) {
		
		mc.fontRendererObj.drawSplitString(str, x + 1, y + 1, wrapWidth, 0);
		mc.fontRendererObj.drawSplitString(str, x, y, wrapWidth, textColor);
	}
	
	public static void loadPages(GuiBookGuide screen) {
		
		GuiBookGuide.pageList.clear();
		GuiBookGuide.pageList.add(new PageTitle(screen, UCItems.generic.createStack("guidebook").getDisplayName()));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.INTRO));
		GuiBookGuide.pageList.add(new PageText(screen, TextFormatting.UNDERLINE + "Categories:"));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.CINDERBELLA, "Cinderbella"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGE1));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.COLLIS, "Goldenrods"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGE2));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.DIRIGIBLE, "Dirigible Plums"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGE3));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.ENDERLILY, "Ender Lilies"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGE4));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.INVISIBILIA, "Invisibilia"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGE5));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.KNOWLEDGE, "Seed of Knowledge"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGE6));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.MARYJANE, "Mary Jane"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGE7));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.MERLINIA, "Merlinia"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGE8));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.MILLENNIUM, "Millennium"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGE9));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.MUSICA, "Musica"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGE10));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.NORMAL, "Normie"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGE11));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.PRECISION, "Precision"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGE12));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.WEEPINGBELL, "Weeping Bells"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGE13));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.FEROXIA, "Feroxia"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGE14));
		if (UCUtils.getServerTaglist(gui.reader.getEntityId()) != null)
			GuiBookGuide.pageList.add(new PageGrowthList(screen));
	}
}
