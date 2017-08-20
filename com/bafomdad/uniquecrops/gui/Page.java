package com.bafomdad.uniquecrops.gui;

import org.lwjgl.opengl.GL11;

import com.bafomdad.uniquecrops.core.EnumItems;
import com.bafomdad.uniquecrops.core.GrowthSteps;
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
		this.drawY = (screen.height - screen.HEIGHT) / 2 + 15;
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
		GuiBookGuide.pageList.add(new PageTitle(screen, UCItems.generic.createStack(EnumItems.GUIDE).getDisplayName()));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.INTRO));
		GuiBookGuide.pageList.add(new PageText(screen, TextFormatting.UNDERLINE + "Categories, Page 1:"));
		GuiBookGuide.pageList.add(new PageText(screen, TextFormatting.UNDERLINE + "Categories, Page 2:"));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.NORMAL, "Normie"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGENORMAL));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.CRAFTER, "Artisia"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGECRAFT));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.CINDERBELLA, "Cinderbella"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGECINDER));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.COLLIS, "Goldenrods"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGECOLLIS));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.DIRIGIBLE, "Dirigible Plums"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGEPLUM));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.ENDERLILY, "Ender Lilies"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGELILY));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.INVISIBILIA, "Invisibilia"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGEINVIS));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.KNOWLEDGE, "Seed of Knowledge"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGEKNOWLEDGE));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.MARYJANE, "Mary Jane"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGEMARY));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.MERLINIA, "Merlinia"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGEMERLIN));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.MILLENNIUM, "Millennium"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGEMILL));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.MUSICA, "Musica"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGEMUSIC));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.PRECISION, "Precision"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGEPRECISE));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.WEEPINGBELL, "Weeping Bells"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGEWEEP));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.EULA, "EULA Plant"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGELEGAL));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.DYEIUS, "Dyeius"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGEDYE));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.COBBLONIA, "Cobblonia"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGECOBBLE));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.ABSTRACT, "Abstract"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGEABSTRACT));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.WAFFLES, "Wafflonia"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGEWAFFLE));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.PIXELSIUS, "Pixelsius"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGEPIXEL));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.DEVILSNARE, "Devil's Snare"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGESNARE));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.PETRAMIA, "Petramia"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGEPETRAMIA));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.MALLEATORIS, "Malleatoris"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGEMALLEATORIS));
		GuiBookGuide.pageList.add(new PageImage(screen, UCStrings.FEROXIA, "Feroxia"));
		GuiBookGuide.pageList.add(new PageText(screen, UCStrings.PAGESAVAGE));
		if (gui.reader != null && gui.book.getTagCompound().hasKey(GrowthSteps.TAG_GROWTHSTAGES))
			GuiBookGuide.pageList.add(new PageGrowthList(screen));
	}
}
