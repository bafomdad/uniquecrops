package com.bafomdad.uniquecrops.render;

import java.awt.Color;

import javax.annotation.Nonnull;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.tiles.TileSundial;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderSundial extends TileEntitySpecialRenderer<TileSundial> {

	final ModelSundial model;
	final ResourceLocation res = new ResourceLocation(UniqueCrops.MOD_ID, "textures/blocks/sundial.png");
	
	public RenderSundial() {
		
		this.model = new ModelSundial();
	}
	
	@Override
	public void render(@Nonnull TileSundial te, double x, double y, double z, float partialTicks, int digProgress, float whatever) {
		
		if (te != null && te.getWorld() != null)
			this.renderSundial(te, x + 0.5F, y + 0.1, z + 0.5F);
	}
	
	private void renderSundial(TileSundial te, double x, double y, double z) {
		
		float f = 0.0625F;
		
		GlStateManager.pushMatrix();
		GlStateManager.translate((float)x, (float)y, (float)z);
		GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
		
		bindTexture(res);
		
		model.DialRedstone.isHidden = te.savedTime < 0;
		model.DialRedstone.rotateAngleY = te.savedRotation;
		
		model.render(te, f);
		GlStateManager.popMatrix();
	}
}
