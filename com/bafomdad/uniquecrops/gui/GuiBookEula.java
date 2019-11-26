package com.bafomdad.uniquecrops.gui;

import java.util.ArrayList;
import java.util.List;

import com.bafomdad.uniquecrops.UniqueCrops;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiBookEula extends GuiAbstractBook {

	public static ResourceLocation texture = new ResourceLocation(UniqueCrops.MOD_ID, "textures/gui/bookeula.png");
	public static List<Page> pageList = new ArrayList();
	
	private GuiButton next;
	private GuiButton prev;
	private GuiButton agree;
	private GuiButton disagree;
	
	private int pageIndex;
	private Page currentPage;
	
	public GuiBookEula(EntityPlayer player) {
		
		super(player, ItemStack.EMPTY);
		this.pageIndex = 0;
	}
	
	@Override
	public void initGui() {
		
		super.initGui();
		Page.loadEulaPages(this);
		this.buttonList.clear();
		int  k = (this.width - this.WIDTH) / 2;
		int l = (this.height - this.HEIGHT) / 2;
		this.currentPage = pageList.size() > 0 ? (this.pageIndex < pageList.size() ? pageList.get(this.pageIndex) : null) : null;
		buttonList.add(this.next = new GuiButtonPageChange(0, k + WIDTH - 26, l + 210, false));
		buttonList.add(this.prev = new GuiButtonPageChange(1, k + 10, l + 210, true));
		buttonList.add(this.agree = new GuiButtonEula(2, k + 65, l + 210, true));
		buttonList.add(this.disagree = new GuiButtonEula(3, k + 95, l + 210, false));
		updateButtons();
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		
		switch(button.id) {
			case 0: pageIndex++; break;
			case 1: --pageIndex; break;
			case 2: mc.displayGuiScreen(null); break;
			case 3: pageIndex = 0; break;
		}
		updateButtons();
	}
	
	private void updateButtons() {
		
		this.next.visible = (this.pageIndex < this.pageList.size() - 1);
		this.prev.visible = this.pageIndex > 0;
		this.agree.visible = this.pageIndex == this.pageList.size() - 1;
		this.disagree.visible = this.pageIndex == this.pageList.size() - 1;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
	
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(texture);
		int k = (this.width - this.WIDTH) / 2;
		int b0 = (this.height - this.HEIGHT) / 2;
		drawTexturedModalRect(k, b0, 0, 0, WIDTH, HEIGHT);
		
		this.currentPage = pageList.size() > 0 ? (this.pageIndex < pageList.size() ? pageList.get(this.pageIndex) : null) : null;
		if (this.currentPage != null)
			this.currentPage.draw();
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean doesGuiPauseGame() {
		
		return false;
	}
	
	@Override
	protected void keyTyped(char character, int key) {}
}
