package com.bafomdad.uniquecrops.gui;

import java.awt.Color;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class GuiTestImage extends Page {
	
	final Block block;

	public GuiTestImage(GuiBookGuide screen, Block block) {
		
		super(screen);
		this.block = block;
	}
	
	@Override
	public void draw() {
		
		super.draw();
		
		mc.fontRenderer.drawString("hi :]", this.drawX, this.drawY, Color.BLACK.getRGB());
		
		IBlockState state = block.getStateFromMeta(7);
		BlockModelShapes shape = mc.getBlockRendererDispatcher().getBlockModelShapes();
		
		TextureManager render = mc.renderEngine;
		IBakedModel model = shape.getModelForState(state);
		model.getQuads(state, EnumFacing.SOUTH, 0);
		
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(1F, 1F, 1F, 1F);
		int k = (gui.width - gui.WIDTH) / 2;
		int l = (gui.height - gui.HEIGHT) / 2;
		
	}
}
