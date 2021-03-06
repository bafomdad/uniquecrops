package com.bafomdad.uniquecrops.entities;

import javax.annotation.Nonnull;

import com.bafomdad.uniquecrops.blocks.tiles.TileArtisia;
import com.bafomdad.uniquecrops.init.UCBlocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class RenderCraftItem extends TileEntitySpecialRenderer<TileArtisia> {

	@Override
	public void renderTileEntityAt(@Nonnull TileArtisia te, double x, double y, double z, float partialTicks, int digProgress) {
		
		IBlockState state = te.getWorld().getBlockState(te.getPos());
		if (state != null && state.getBlock() == UCBlocks.cropArtisia) {
			ItemStack stack = te.getItem();
			if (stack != null) {
				GlStateManager.pushMatrix();
				GlStateManager.translate((float)x + 0.5F, (float)y + 1.25F, (float)z + 0.5F);
				renderItem(stack);
				GlStateManager.popMatrix();
			}
		}
	}
	
	private void renderItem(ItemStack stack) {
		
		float toScale = stack.getItem() instanceof ItemBlock ? 0.25F : 0.4375F;
		GlStateManager.scale(toScale, toScale, toScale);
		float playerView = Minecraft.getMinecraft().getRenderManager().playerViewY;
		GlStateManager.rotate(180.0F - playerView, 0, 1.0F, 0);
		Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.NONE);
	}
}
