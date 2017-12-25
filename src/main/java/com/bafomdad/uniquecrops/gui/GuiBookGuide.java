package com.bafomdad.uniquecrops.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.input.Keyboard;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.UCStrings;

public class GuiBookGuide extends GuiScreen {

	public static ResourceLocation texture = new ResourceLocation(UniqueCrops.MOD_ID, "textures/gui/bookguide.png");
	public static List<Page> pageList = new ArrayList<Page>();
	public final EntityPlayer reader;
	public final ItemStack book;
	
	public int WIDTH = 175;
	public int HEIGHT = 228;
	
	private GuiButton next;
	private GuiButton prev;
	private GuiButton category;
	private GuiButton category2;
	private GuiButton backbutton;
	
	private int pageIndex;
	private int savedIndex;
	private Page currentPage;
	
	public GuiBookGuide(EntityPlayer player, ItemStack heldBook) {

		this.pageIndex = 0;
		this.reader = player;
		this.book = heldBook;
		this.savedIndex = NBTUtils.getInt(book, "savedIndex", 0);
		this.pageIndex = savedIndex;
	}
	
	@Override
	public void initGui() {
		
		super.initGui();
		Page.loadPages(this);
		this.buttonList.clear();
		int k = (this.width - this.WIDTH) / 2;
		int l = (this.height - this.HEIGHT) / 2; 
		this.currentPage = pageList.size() > 0 ? (this.pageIndex < pageList.size() ? pageList.get(this.pageIndex) : null) : null;
		buttonList.add(this.next = new GuiButtonPageChange(0, k + WIDTH - 26, l + 210, false));
		buttonList.add(this.prev = new GuiButtonPageChange(1, k + 10, l + 210, true));
		List<String> stringlist = new ArrayList();
		for (Page page : pageList) {
			if (page instanceof PageImage)
				stringlist.add(((PageImage)page).getText());
		}
		String[] arrays = new String[stringlist.size()];
		for (int i = 0; i < arrays.length; i++) {
			arrays[i] = stringlist.get(i);
		}
		String[] firstCat = (String[]) Arrays.copyOfRange(arrays, 0, arrays.length / 2);
		String[] secondCat = (String[]) Arrays.copyOfRange(arrays, (arrays.length / 2), arrays.length);
 		buttonList.add(this.category = new GuiButtonLink(this, 2, k + 15, l + 35, 100, 168, firstCat));
		buttonList.add(this.category2 = new GuiButtonLink(this, 3, k + 15, l + 35, 100, 168, secondCat));
		buttonList.add(this.backbutton = new GuiBackButton(3, k + 80, l + 210));
		updateButtons();
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		
		int catsize = ((GuiButtonLink)category).selectedOption;
		int catsize2 = ((GuiButtonLink)category2).selectedOption;
		switch (button.id) {
			case 0: pageIndex++; break;
			case 1: --pageIndex; break;
			case 2: pageIndex = catsize + (pageIndex + catsize + 2); break;
			case 3: if (pageIndex == 3)
					pageIndex = catsize2 + (pageIndex + catsize2 + 27);
				else
					pageIndex = 2; break;
		}
		this.savedIndex = pageIndex;
		NBTUtils.setInt(book, "savedIndex", savedIndex);
		updateButtons();
	}
	
	private void updateButtons() {
		
		this.next.visible = (this.pageIndex < this.pageList.size() - 1);
		this.prev.visible = this.pageIndex > 0;
		this.category.visible = this.pageIndex == 2;
		this.category2.visible = this.pageIndex == 3;
		this.backbutton.visible = this.pageIndex > 3;
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
	protected void keyTyped(char character, int key) {
		
		boolean update = false;
		if (key == Keyboard.KEY_ESCAPE) {
			NBTUtils.setInt(book, "savedIndex", savedIndex);
			mc.displayGuiScreen(null);
		}
		if (key == Keyboard.KEY_RIGHT && this.next.visible) {
			this.pageIndex++;
			update = true;
		}
		if (key == Keyboard.KEY_LEFT && this.prev.visible) {
			this.pageIndex--;
			update = true;
		}
		if (key == Keyboard.KEY_UP && this.backbutton.visible) {
			this.pageIndex = 2;
			update = true;
		}
		if (update) {
			this.savedIndex = pageIndex;
			NBTUtils.setInt(book, "savedIndex", savedIndex);
			updateButtons();
		}
	}
	
	public float getZLevel() {
		
		return zLevel;
	}
}
