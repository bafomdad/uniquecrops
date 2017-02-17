package com.bafomdad.uniquecrops.gui;

import io.netty.buffer.Unpooled;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.UCUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiBookGuide extends GuiScreen {

	public static ResourceLocation texture = new ResourceLocation(UniqueCrops.MOD_ID, "textures/gui/bookguide.png");
	public static List<Page> pageList = new ArrayList<Page>();
	public final EntityPlayer reader;
	private final ItemStack book;
	private String[] cats = UCStrings.CROPCATS;
	
	private static final int BUTTON_NEXT = 0;
	private static final int BUTTON_PREV = 1;
	private static final int CATEGORY = 2;
	private static final int BACK_BUTTON = 3;
	
	public int WIDTH = 175;
	public int HEIGHT = 228;
	int left = width / 2 - WIDTH / 2;
	int top = height / 2 - HEIGHT / 2;
	
	private GuiButton next;
	private GuiButton prev;
	private GuiButton category;
	private GuiButton backbutton;
	
	private int pageIndex;
	private int savedIndex;
	private Page currentPage;
	
	public int bookXStart;
	
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
		bookXStart = (width - WIDTH) / 2;
		this.currentPage = pageList.size() > 0 ? (this.pageIndex < pageList.size() ? pageList.get(this.pageIndex) : null) : null;
		buttonList.add(next = new GuiButtonPageChange(BUTTON_NEXT, bookXStart + WIDTH - 26, 210, false));
		buttonList.add(prev = new GuiButtonPageChange(BUTTON_PREV, bookXStart + 10, 210, true));
		buttonList.add(category = new GuiButtonLink(this, CATEGORY, bookXStart + 15, 35, 100, 168, cats));
		buttonList.add(backbutton = new GuiBackButton(BACK_BUTTON, bookXStart + 80, 210));
		updateButtons();
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		
		int catsize = ((GuiButtonLink)category).selectedOption;
		switch (button.id) {
			case BUTTON_NEXT: pageIndex++; break;
			case BUTTON_PREV: --pageIndex; break;
			case CATEGORY: pageIndex = catsize + (pageIndex + catsize + 1); break;
			case BACK_BUTTON: pageIndex = 2; break;
		}
		this.savedIndex = pageIndex;
		NBTUtils.setInt(book, "savedIndex", savedIndex);
		updateButtons();
	}
	
	private void updateButtons() {
		
		this.next.visible = (this.pageIndex < this.pageList.size() - 1);
		this.prev.visible = this.pageIndex > 0;
		this.category.visible = this.pageIndex == 2;
		this.backbutton.visible = this.pageIndex > 2;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
       
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		drawBackground();
		drawForeground();
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean doesGuiPauseGame() {
		
		return false;
	}
	
	@Override
	protected void keyTyped(char character, int key) {
		
		if (key == Keyboard.KEY_ESCAPE)
			mc.displayGuiScreen(null);
	}
	
	protected void drawBackground() {
		
		mc.renderEngine.bindTexture(texture);
		drawTexturedModalRect(bookXStart, 5, 0, 0, WIDTH, HEIGHT);
	}
	
	public void drawForeground() {
		
		this.currentPage = pageList.size() > 0 ? (this.pageIndex < pageList.size() ? pageList.get(this.pageIndex) : null) : null;
		if (this.currentPage != null)
			this.currentPage.draw();
	}
}
