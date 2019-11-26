package com.bafomdad.uniquecrops.render.entity;

import java.util.Random;

import javax.annotation.Nonnull;

import com.bafomdad.uniquecrops.blocks.tiles.TileArtisia;
import com.bafomdad.uniquecrops.blocks.tiles.TileLacusia;
import com.bafomdad.uniquecrops.blocks.tiles.TileWeatherflesia;
import com.bafomdad.uniquecrops.events.UCTickHandler;
import com.bafomdad.uniquecrops.init.UCBlocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class RenderCraftItem {
	
	public static class Weatherflesia extends TileEntitySpecialRenderer<TileWeatherflesia> {
		
		@Override
		public void render(@Nonnull TileWeatherflesia te, double x, double y, double z, float partialTicks, int digProgress, float wat) {
		
			IBlockState state = te.getWorld().getBlockState(te.getPos());
			if (state != null && state.getBlock() == UCBlocks.weatherflesia) {
				ItemStack stack = te.getItem();
				if (stack.isEmpty()) return;
				
				double time = UCTickHandler.ticksInGame + UCTickHandler.partialTicks;
				int multiplier = stack.getMaxDamage() - stack.getItemDamage();
				double scale = (multiplier / 300.0) * 0.5;
				
				GlStateManager.pushMatrix();
				double wave = Math.sin(time * 0.2) / 32F;
				GlStateManager.translate(x + 0.5, y + 0.85 + wave, z + 0.5);
				renderItem(stack);
				GlStateManager.popMatrix();
				
				// stuff below was copied from LayerEnderDragonDeath
		        Tessellator tessellator = Tessellator.getInstance();
		        BufferBuilder bufferbuilder = tessellator.getBuffer();
		        RenderHelper.disableStandardItemLighting();
		        float f = (float)(Math.max(Math.sin(time * 0.025F), 0.5));
		        float f1 = 0.0F;

		        if (f > 0.8F)
		            f1 = (f - 0.8F) / 0.2F;

		        Random random = new Random(432L);
		        GlStateManager.disableTexture2D();
		        GlStateManager.shadeModel(7425);
		        GlStateManager.enableBlend();
		        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
		        GlStateManager.disableAlpha();
		        GlStateManager.enableCull();
		        GlStateManager.depthMask(false);
		        GlStateManager.pushMatrix();
		        GlStateManager.translate(x + 0.5, y + 1.0F, z + 0.5);
		        
		        // my stuff starts here
		        GlStateManager.scale(scale, scale, scale);
		        GlStateManager.rotate((float)time * 4F, 1F, 1F, 0F);
		        // my stuff ends here

		        for (int i = 0; (float)i < (f + f * f) / 2.0F * 60.0F; ++i) {
		        	
		            GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
		            GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
		            GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
		            GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
		            GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
		            GlStateManager.rotate(random.nextFloat() * 360.0F + f * 90.0F, 0.0F, 0.0F, 1.0F);
		            float f2 = random.nextFloat() * 20.0F + 5.0F + f1 * 10.0F;
		            float f3 = random.nextFloat() * 2.0F + 1.0F + f1 * 2.0F;
		            bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
		            bufferbuilder.pos(0.0D, 0.0D, 0.0D).color(255, 255, 255, (int)(255.0F * (1.0F - f1))).endVertex();
		            bufferbuilder.pos(-0.866D * (double)f3, (double)f2, (double)(-0.5F * f3)).color(255, 0, 255, 0).endVertex();
		            bufferbuilder.pos(0.866D * (double)f3, (double)f2, (double)(-0.5F * f3)).color(255, 0, 255, 0).endVertex();
		            bufferbuilder.pos(0.0D, (double)f2, (double)(1.0F * f3)).color(255, 0, 255, 0).endVertex();
		            bufferbuilder.pos(-0.866D * (double)f3, (double)f2, (double)(-0.5F * f3)).color(255, 0, 255, 0).endVertex();
		            tessellator.draw();
		        }
		        GlStateManager.popMatrix();
		        GlStateManager.depthMask(true);
		        GlStateManager.disableCull();
		        GlStateManager.disableBlend();
		        GlStateManager.shadeModel(7424);
		        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		        GlStateManager.enableTexture2D();
		        GlStateManager.enableAlpha();
		        RenderHelper.enableStandardItemLighting();
			}
		}
	}

	public static class Artisia extends TileEntitySpecialRenderer<TileArtisia> {
		
		@Override
		public void render(@Nonnull TileArtisia te, double x, double y, double z, float partialTicks, int digProgress, float wat) {
			
			IBlockState state = te.getWorld().getBlockState(te.getPos());
			if (state != null && state.getBlock() == UCBlocks.cropArtisia) {
				ItemStack stack = te.getItem();
				if (!stack.isEmpty()) {
					GlStateManager.pushMatrix();
					GlStateManager.translate((float)x + 0.5F, (float)y + 1.25F, (float)z + 0.5F);
					renderItem(stack);
					GlStateManager.popMatrix();
				}
			}
		}
	}
	
	public static class Lacusia extends TileEntitySpecialRenderer<TileLacusia> {
		
		@Override
		public void render(@Nonnull TileLacusia te, double x, double y, double z, float partialTicks, int digProgress, float wat) {
			
			IBlockState state = te.getWorld().getBlockState(te.getPos());
			if (state != null && state.getBlock() == UCBlocks.cropLacusia) {
				ItemStack stack = te.getItem();
				if (!stack.isEmpty()) {
					GlStateManager.pushMatrix();
					GlStateManager.translate((float)x + 0.5F, (float)y + 0.65F, (float)z + 0.5F);
					renderItem(stack);
					GlStateManager.popMatrix();
				}
			}
		}
	}
	
	public static void renderItem(ItemStack stack) {
		
		float toScale = stack.getItem() instanceof ItemBlock ? 0.25F : 0.4375F;
		GlStateManager.scale(toScale, toScale, toScale);
		float playerView = Minecraft.getMinecraft().getRenderManager().playerViewY;
		GlStateManager.rotate(180.0F - playerView, 0, 1.0F, 0);
		Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.NONE);
	}
}
