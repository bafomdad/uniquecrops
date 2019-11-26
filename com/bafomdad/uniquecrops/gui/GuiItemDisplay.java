package com.bafomdad.uniquecrops.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.util.ITooltipFlag.TooltipFlags;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiUtils;

public class GuiItemDisplay {
	
	private final int x, y;
	private final float scale;
	private final ItemStack stack;
	private final GuiScreen gui;

	public GuiItemDisplay(GuiScreen gui, int x, int y, float scale, ItemStack stack) {
		
		this.gui = gui;
		this.x = x;
		this.y = y;
		this.scale = scale;
		this.stack = stack;
	}
	
	public void draw() {
		
		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.enableDepth();
		GlStateManager.enableRescaleNormal();
//		GlStateManager.translate(x, y, 0);
		GlStateManager.scale(scale, scale, scale);
		
		Minecraft mc = this.gui.mc;
		mc.getRenderItem().renderItemIntoGUI(stack, x, y);
		
		RenderHelper.disableStandardItemLighting();
		GlStateManager.popMatrix();
	}
	
	public void onMouseHover(int mouseX, int mouseY) {
		
		if (this.isHovered(mouseX, mouseY)) {
			Minecraft mc = this.gui.mc;
			
			List<String> list = this.stack.getTooltip(mc.player, mc.gameSettings.advancedItemTooltips ? TooltipFlags.ADVANCED : TooltipFlags.NORMAL);
			for (int k = 0; k < list.size(); ++k) {
				if (k == 0)
					list.set(k, this.stack.getRarity().color + list.get(k));
				else
					list.set(k, TextFormatting.GRAY + list.get(k));
			}
			GuiUtils.drawHoveringText(list, mouseX, mouseY, mc.displayWidth, mc.displayHeight, -1, mc.fontRenderer);
		}
	}
	
	public boolean isHovered(int mouseX, int mouseY) {
		
		return mouseX >= this.x && mouseY >= this.y && mouseX < this.x + 16 * this.scale && mouseY < this.y + 16 * this.scale;
	}
}
