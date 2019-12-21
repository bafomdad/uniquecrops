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
import com.bafomdad.uniquecrops.core.enums.EnumCrops;
import com.bafomdad.uniquecrops.network.PacketBookIndex;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

public class GuiBookGuide extends GuiAbstractBook {

	public static ResourceLocation texture = new ResourceLocation(UniqueCrops.MOD_ID, "textures/gui/bookguide.png");
	public static List<Page> pageList = new ArrayList<Page>();
	
	private GuiButton next;
	private GuiButton prev;
	private GuiButton category;
	private GuiButton category2;
	private GuiButton backbutton;
	
	private int pageIndex;
	private Page currentPage;
	
	public GuiBookGuide(EntityPlayer player, ItemStack heldBook) {

		super(player, heldBook);
		this.pageIndex = NBTUtils.getInt(heldBook, "savedIndex", 0);
	}
	
	@Override
	public void initGui() {
		
		super.initGui();
		Page.loadGuidePages(this);
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
		buttonList.add(this.backbutton = new GuiBackButton(4, k + 80, l + 210));
		updateButtons();
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		
		int catsize = ((GuiButtonLink)category).selectedOption;
		int catsize2 = ((GuiButtonLink)category2).selectedOption;
		switch (button.id) {
			case 0: pageIndex++; break;
			case 1: --pageIndex; break;
			case 2: pageIndex = ((pageIndex + catsize) * 3) - 2; break;
//			case 3: pageIndex = ((pageIndex + (catsize2 + (EnumCrops.values().length / 2) - 1)) * 3) - 2; break;
			case 3: pageIndex = ((pageIndex + (catsize2 + (Page.cropCount / 2) - 1)) * 3) - 2; break;
			case 4: pageIndex = 2; break;
		}
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
			UCPacketHandler.INSTANCE.sendToServer(new PacketBookIndex(pageIndex));
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
			updateButtons();
		}
	}
}
