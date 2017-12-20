package com.bafomdad.uniquecrops.gui;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.bafomdad.uniquecrops.UniqueCrops;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

public class PageImage extends Page {

	final Block block;
	final String text;
	final ResourceLocation resource = new ResourceLocation(UniqueCrops.MOD_ID, "textures/gui/croppage.png");
	
	public PageImage(GuiBookGuide screen, Block crop, String text) {
		
		super(screen);
		this.block = crop;
		this.text = text;
	}
	
	@Override
	public void draw() {
		
		super.draw();
		
		TextureManager render = mc.renderEngine;
		render.bindTexture(resource);
		
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(1F, 1F, 1F, 1F);
		int k = (gui.width - gui.WIDTH) / 2;
		int j = (gui.height - gui.HEIGHT) / 2;
		gui.drawTexturedModalRect(k + 5, j + 5, 0, 0, gui.WIDTH, gui.HEIGHT);
		gui.drawCenteredString(mc.fontRenderer, text, drawX + 60, drawY + 150, Color.gray.getRGB());
		GlStateManager.disableBlend();
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(k + 94, j + 63, gui.getZLevel() + 100F);
		
		GlStateManager.scale(50, 50, 50);
		GlStateManager.rotate(150F, 1, 0, 0);
		GlStateManager.rotate(50F, 0, 1, 0);
		
		GlStateManager.translate(-0.5, -0.5, -0.5);
		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		IBlockState state = block.getDefaultState().withProperty(BlockCrops.AGE, 7);
		mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		
		BlockRendererDispatcher brd = mc.getBlockRendererDispatcher();
		GlStateManager.translate(0, 0, 0 + 1);
		GlStateManager.color(1, 1, 1, 1);
		brd.renderBlockBrightness(state, 1.0F);
		
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.enableDepth();
		GlStateManager.popMatrix();
		
		GlStateManager.popMatrix();
	}
	
	public String getText() {
		
		return text;
	}
}
