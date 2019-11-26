package com.bafomdad.uniquecrops.render.entity;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;

import com.bafomdad.uniquecrops.blocks.tiles.TileFascino;
import com.bafomdad.uniquecrops.events.UCTickHandler;
import com.bafomdad.uniquecrops.render.model.ModelCubeyThingy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderFascino extends TileEntitySpecialRenderer<TileFascino> {
	
	private static final ResourceLocation TEXTURE_BOOK = new ResourceLocation("textures/entity/enchanting_table_book.png");
	final ModelBook book = new ModelBook();
	final ModelCubeyThingy cube = new ModelCubeyThingy();

	@Override
	public void render(@Nonnull TileFascino te, double x, double y, double z, float partialTicks, int digProgress, float whatever) {

		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5, y + 0.1, z + 0.5);
		this.bindTexture(TEXTURE_BOOK);
		
		int j = 0xF000F0 % 0x10000;
		int k = 0xF000F0 / 0x10000;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
		
		double time = UCTickHandler.ticksInGame + UCTickHandler.partialTicks;
		int cubes = 2;
		float cubeOffset = 360 / cubes;
		float modifier = 6F;
		float rotationMod = 0.2F;
		float radiusBase = 0.35F;
		float radiusMod = 0.05F;
		
		double wave = Math.sin(time * 0.2) / 32F;
		
		GlStateManager.pushMatrix();
		GlStateManager.rotate((float)time * 4F, 0F, 1.0F, 0F);
		GlStateManager.translate(-0.25, wave + 0.65, 0);
		this.book.pagesLeft.offsetX = 0.005F;
		this.book.pagesRight.offsetX = 0.005F;
		this.book.render(null, 0, 0, 0, 0, 0.0F, 0.0625F);
		GlStateManager.popMatrix();
		
		GlStateManager.pushMatrix();
		int items = 0;
		for (int i = 0; i < te.getInventory().getSlots(); i++) {
			ItemStack stack = te.getInventory().getStackInSlot(i);
			if (!stack.isEmpty())
				items++;
			else break;
		}
		if (items > 0) {
//			int enchantTime = te.getEnchantingTicks();
			float offsetPerItem = 180F / items;
			float totalAngle = 0F;
			float[] angles = new float[te.getInventory().getSlots()];
			GlStateManager.translate(0, 0.75, 0);
			float playerView = Minecraft.getMinecraft().getRenderManager().playerViewY;
			GlStateManager.rotate((180.0F - playerView) + 90.0F, 0F, 1F, 0F);
			GlStateManager.rotate(90, 1F, 0F, 0F);
			GlStateManager.rotate(90.0F / items, 1F, 0F, 0F);
			GlStateManager.rotate(90.0F, 0F, 0F, 1F);
			for (int l = 0; l < items; l++) {
				angles[l] = totalAngle += offsetPerItem;
				GlStateManager.pushMatrix();
				GlStateManager.rotate(angles[l], 0F, 1F, 0F);
				GlStateManager.translate(0.75 + wave, 0, 0);
				GlStateManager.rotate(-angles[l], 0F, 1F, 0F);
				GlStateManager.rotate((90.0F / items) + 90.0F, 0F, 1F, 0F);
				ItemStack stack = te.getInventory().getStackInSlot(l);
				GlStateManager.rotate(-90.0F, 0F, 0F, 1F);
				GlStateManager.pushMatrix();
				GlStateManager.rotate(90.0F, 0F, 1F, 0F);
				
//				if (enchantTime > 0 && (enchantTime % items) > l)
//					GlStateManager.rotate((float)time * 8F, 0F, 1F, 0F);
				
				Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.GROUND);
				GlStateManager.popMatrix();
				GlStateManager.popMatrix();
			}
		}
		GlStateManager.popMatrix();

		this.bindTexture(TEXTURE_BOOK);
		for (int i = 0; i < 8; i++) {
			GlStateManager.rotate((float)time * 4F, 0F, 1.0F, 0F);
			for (int m = 0; m < cubes; m++) {
				float offset = cubeOffset * m;
				float deg = (int)(time / rotationMod % 360F + offset);
				float rad = deg * (float)Math.PI / 180.0F;
				float radiusX = (float)(radiusBase + radiusMod * Math.sin(time / modifier));
				float radiusZ = (float)(radiusBase + radiusMod * Math.cos(time / modifier));
				float x1 = (float)(radiusX * Math.cos(rad));
				float z1 = (float)(radiusZ * Math.sin(rad));
				GlStateManager.pushMatrix();
				GlStateManager.translate(x1, i * 0.1, z1);
				this.cube.renderCube();
				GlStateManager.popMatrix();
			}
		}
		GlStateManager.popMatrix();
	}
}
