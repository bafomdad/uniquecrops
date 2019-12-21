package com.bafomdad.uniquecrops.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.network.PacketBookIndex;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

public class GuiBookMultiblocks extends GuiAbstractBook {
	
	public static final ResourceLocation TEXTURE = new ResourceLocation(UniqueCrops.MOD_ID, "textures/gui/bookguide.png");
	public static List<Page> pageList = new ArrayList();

	private GuiButton next;
	private GuiButton prev;
	private GuiButton tableOfContents;
	private GuiButton backButton;
	
	private int pageIndex;
	private Page currentPage;

	public GuiBookMultiblocks(EntityPlayer player, ItemStack heldBook) {

		super(player, heldBook);
		this.pageIndex = NBTUtils.getInt(heldBook, "savedIndex", 0);
	}
	
	@Override
	public void initGui() {
		
		super.initGui();
		Page.loadMultiblockPages(this);
		this.buttonList.clear();
		int k = (this.width - this.WIDTH) / 2;
		int l = (this.height - this.HEIGHT) / 2;
		this.currentPage = pageList.size() > 0 ? (this.pageIndex < pageList.size() ? pageList.get(this.pageIndex) : null) : null;
		buttonList.add(this.next = new GuiButtonPageChange(0, k + WIDTH - 26, l + 210, false));
		buttonList.add(this.prev = new GuiButtonPageChange(1, k + 10, l + 210, true));
		List<String> patternList = new ArrayList();
		for (Page page : pageList) {
			if (page instanceof PageRecipes) {
				patternList.add(((PageRecipes)page).getTitle());
			}
		}
		buttonList.add(this.tableOfContents = new GuiButtonLink(this, 2, k + 15, l + 35, 100, 168, patternList.toArray(new String[0])));
		buttonList.add(this.backButton = new GuiBackButton(3, k + 80, l + 210));
		
		updateButtons();
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		
		int contentSize = ((GuiButtonLink)tableOfContents).selectedOption;
		switch (button.id) {
			case 0: pageIndex++; break;
			case 1: --pageIndex; break;
			case 2: pageIndex = ((pageIndex + contentSize) * 2) + 1; break;
			case 3: pageIndex = 1; break;
		}
		updateButtons();
	}
	
	private void updateButtons() {
		
		this.next.visible = (this.pageIndex < this.pageList.size() - 1);
		this.prev.visible = this.pageIndex > 0;
		this.tableOfContents.visible = this.pageIndex == 1;
		this.backButton.visible = this.pageIndex > 1;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		GlStateManager.color(1F, 1F, 1F, 1F);
		mc.renderEngine.bindTexture(TEXTURE);
		int k = (this.width - this.WIDTH) / 2;
		int l = (this.height - this.HEIGHT) / 2;
		drawTexturedModalRect(k, l, 0, 0, WIDTH, HEIGHT);
		
		this.currentPage = pageList.size() > 0 ? (this.pageIndex < pageList.size() ? pageList.get(this.pageIndex) : null) : null;
		if (this.currentPage != null) {
			this.currentPage.draw();
			this.currentPage.drawScreenPost(mouseX, mouseY);
		}
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean doesGuiPauseGame() {
		
		return false;
	}
	
	@Override
	protected void keyTyped(char character, int key) {
		
		if (key == Keyboard.KEY_ESCAPE) {
			UCPacketHandler.INSTANCE.sendToServer(new PacketBookIndex(pageIndex));
			mc.displayGuiScreen(null);
		}
	}
}
