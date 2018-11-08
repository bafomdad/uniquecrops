package com.bafomdad.uniquecrops.render;

import javax.annotation.Nonnull;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.tiles.TileSucco;
import com.bafomdad.uniquecrops.init.UCBlocks;

public class RenderSucco extends TileEntitySpecialRenderer<TileSucco> {

	final ModelFakeCrop model;
	final ResourceLocation[] resList = new ResourceLocation[] {
			new ResourceLocation(UniqueCrops.MOD_ID, "textures/blocks/vampire1.png"),
			new ResourceLocation(UniqueCrops.MOD_ID, "textures/blocks/vampire2.png"),
			new ResourceLocation(UniqueCrops.MOD_ID, "textures/blocks/vampire2.png"),
			new ResourceLocation(UniqueCrops.MOD_ID, "textures/blocks/vampire3.png"),
			new ResourceLocation(UniqueCrops.MOD_ID, "textures/blocks/vampire3.png"),
			new ResourceLocation(UniqueCrops.MOD_ID, "textures/blocks/vampire4.png"),
			new ResourceLocation(UniqueCrops.MOD_ID, "textures/blocks/vampire4.png"),
			new ResourceLocation(UniqueCrops.MOD_ID, "textures/blocks/vampire5.png")
	};
	
	public RenderSucco() {
		
		this.model = new ModelFakeCrop();
	}
	
	@Override
	public void render(@Nonnull TileSucco te, double x, double y, double z, float partialTicks, int digProgress, float whatever) {
		
		if (WorldRenderHandler.rendering || Minecraft.getMinecraft().player.capabilities.isCreativeMode)
			renderCrop(te, x, y, z);
	}
	
	private void renderCrop(TileSucco te, double x, double y, double z) {
		
		float f = 0.0625F;
		
		GlStateManager.pushMatrix();
		GlStateManager.translate((float)x + 0.5, (float)y + 0.43, (float)z + 0.5);
		GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
		
		IBlockState state = te.getWorld().getBlockState(te.getPos());
		if (state.getBlock() == UCBlocks.cropSucco) {
			bindTexture(resList[state.getValue(BlockCrops.AGE)]);
			model.render(te, f);
		}
		GlStateManager.popMatrix();
	}
}
