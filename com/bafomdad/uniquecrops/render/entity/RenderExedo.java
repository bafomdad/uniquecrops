package com.bafomdad.uniquecrops.render.entity;

import javax.annotation.Nonnull;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.tiles.TileExedo;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.render.model.ModelExedo;

public class RenderExedo extends TileEntitySpecialRenderer<TileExedo> {

	final ModelExedo model;
	static final ResourceLocation TEX = new ResourceLocation(UniqueCrops.MOD_ID, "textures/models/model_exedo.png");
	
	public RenderExedo() {
		
		this.model = new ModelExedo();
	}
	
	@Override
	public void render(@Nonnull TileExedo te, double x, double y, double z, float partialTicks, int digProgress, float whatever) {
		
		float f = 0.0625F;
		
		IBlockState state = te.getWorld().getBlockState(te.getPos());
		if (state != null && state.getBlock() == UCBlocks.exedo) {
			GlStateManager.pushMatrix();
			bindTexture(TEX);
			boolean wiggle = te.isWiggling;
			boolean chomping = te.isChomping;
			if (wiggle) {
				GlStateManager.translate(x + 0.5, y + 1.5, z + 0.5);
				GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
				model.renderWiggle(te, f);
			}
			if (chomping && te.ent != null) {
				double xOffset, yOffset, zOffset;
				xOffset = te.ent.posX - te.getPos().getX();
				yOffset = te.ent.posY - te.getPos().getY();
				zOffset = te.ent.posZ - te.getPos().getZ();

				GlStateManager.pushMatrix();
				GlStateManager.translate((x) + xOffset, (y + (3.5 * te.ent.height)) + yOffset, (z) + zOffset);
				GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
				model.renderChomp(te, f);
				GlStateManager.popMatrix();
			}
			if (!wiggle) {
				GlStateManager.translate(x + 0.5, y + 1.5, z + 0.5);
				GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
				model.render(te, f);
			}
			GlStateManager.popMatrix();
		}
	}
}
