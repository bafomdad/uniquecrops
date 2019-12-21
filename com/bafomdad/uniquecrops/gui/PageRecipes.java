package com.bafomdad.uniquecrops.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.MultiblockPattern;

public class PageRecipes extends Page {
	
	final MultiblockPattern pattern;
	final List<GuiItemDisplay> displayList = new ArrayList();
	final ResourceLocation SLOT_TEX = new ResourceLocation(UniqueCrops.MOD_ID, "textures/gui/slotbackground.png");

	public PageRecipes(GuiAbstractBook screen, MultiblockPattern pattern) {
		
		super(screen);
		this.pattern = pattern;
	}
	
	@Override
	public void draw() {
		
		super.draw();
		int k = (gui.width - gui.WIDTH) / 2;
		int j = (gui.height - gui.HEIGHT) / 2;
		
		GlStateManager.pushMatrix();
		GlStateManager.scale(1.625F, 1.625F, 1.625F);
		String title = getTitle();
		gui.mc.fontRenderer.drawString(title, ((gui.width - gui.WIDTH) - gui.mc.fontRenderer.getStringWidth(title)) / 2, j + 7, Color.GRAY.getRGB(), false);
		GlStateManager.popMatrix();
		int size = pattern.getShape().length;
		k += 70 - (size * size);
		j += 56 / 2;
		
		this.displayList.clear();
		
		for (int z = 0; z < size; z++) {
			String line = pattern.getShape()[z];
			for (int x = 0; x < line.length(); x++) {
				IBlockState state = pattern.getDefinition().get(line.charAt(x)).getFirstState();
				ItemStack stack = state.getBlock().getItem(this.gui.reader.world, BlockPos.ORIGIN, state);
				GlStateManager.pushMatrix();
				GlStateManager.color(1F, 1F, 1F, 1F);
				gui.mc.renderEngine.bindTexture(SLOT_TEX);
				this.gui.drawModalRectWithCustomSizedTexture(k + 6 + x * 18, j + 7 + z * 18, 0, 0, 16, 16, 16, 16);
				GlStateManager.popMatrix();
				if (!stack.isEmpty()) {
					this.displayList.add(new GuiItemDisplay(this.gui, k + 6 + x * 18, j + 7 + z * 18, 1F, stack));
				}
			}
		}
		ItemStack catalyst = pattern.getCatalyst();
		this.displayList.add(new GuiItemDisplay(this.gui, k - 25, j + 12 + size * 18, 1F, catalyst));
		for (GuiItemDisplay display : this.displayList)
			display.draw();
		
		gui.drawCenteredString(gui.mc.fontRenderer, "Power: " + pattern.getPower(), k + 25, j + 16 + size * 18, Color.CYAN.getRGB());
	}
	
	@Override
	public void drawScreenPost(int mouseX, int mouseY) {
		
		for (GuiItemDisplay display : this.displayList)
			display.onMouseHover(mouseX, mouseY);
	}
	
	public String getTitle() {
		
		return pattern.getName();
	}
}
