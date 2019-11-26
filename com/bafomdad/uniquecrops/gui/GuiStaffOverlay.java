package com.bafomdad.uniquecrops.gui;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.capabilities.CPCapability;
import com.bafomdad.uniquecrops.capabilities.CPProvider;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.core.UCStrings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiStaffOverlay extends Gui {

	Minecraft mc;
	private static final ResourceLocation TEX = new ResourceLocation(UniqueCrops.MOD_ID, "textures/gui/leaf_bar.png");
	
	long lastSystemTime, staffUpdateCounter, updateCounter;
	int capacity;
	
	public GuiStaffOverlay(Minecraft mc) {
		
		super();
		this.mc = mc;
	}
	
	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent.Post event) {
		
		Profiler profiler = mc.profiler;
		if (event.getType() == ElementType.ALL) {
			profiler.startSection("UC-hud");
			CPCapability cap = mc.player.getHeldItemMainhand().getCapability(CPProvider.CROP_POWER, null);
				if (cap == null) {
					profiler.endSection();
					return;
				}
				ScaledResolution scaled = new ScaledResolution(mc);
				int i = cap.getPower();
				boolean flag = this.staffUpdateCounter > this.updateCounter /*&& (this.staffUpdateCounter - this.updateCounter) / 3L % 2L == 1L*/; 
				if (flag) {
					GlStateManager.color(0.0F, 1.0F, 0.0F);
				}
				GL11.glDisable(GL11.GL_LIGHTING);
//				this.drawCenteredString(mc.fontRenderer, "Crop power: " + cap.getPower() + " / " + cap.getCapacity(), width / 2, 10, Color.WHITE.getRGB());
				
//				float scale = (float)Math.min(0.125F, Math.sin(Minecraft.getSystemTime() / 2000D));
				float scale = (float)Math.sin(Minecraft.getSystemTime() / 500D) * 0.015625F;
				GlStateManager.pushMatrix();
				if (i < this.capacity) {
					this.lastSystemTime = Minecraft.getSystemTime();
//					GlStateManager.color(1.0F, 0.0F, 0.0F);
					this.staffUpdateCounter = this.updateCounter + 200;
				}
				if (i > this.capacity) {
					this.lastSystemTime = Minecraft.getSystemTime();
//					GlStateManager.color(0.0F, 1.0F, 0.0F);
					this.staffUpdateCounter = this.updateCounter + 200;
				}
				if (Minecraft.getSystemTime() - this.lastSystemTime > 1000L) {
					this.capacity = i;
					this.lastSystemTime = Minecraft.getSystemTime();
				}
				GlStateManager.scale(1 + scale, 1 + scale, 1 + scale);
				this.renderLeafBar(scaled, cap.getPower(), cap.getCapacity());
				GlStateManager.popMatrix();
				this.capacity = i;
		}
		++this.updateCounter;
		profiler.endSection();
	}
	
	private void renderLeafBar(ScaledResolution res, int currentPower, int capacity) {
		
		int x = res.getScaledWidth() / 2 + UCConfig.guiWidth;
		int y = res.getScaledHeight() + UCConfig.guiHeight;
		
		mc.renderEngine.bindTexture(TEX);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		int count = 5;
		double start = Math.PI / 2;
		int direction = -1;
		int radius = 15;
		double interval = 2 * Math.PI / count;
		int mod = capacity / count;

		for (int i = 0; i < count; ++i) {
			double angle = start + i * interval * (direction < 0 ? -1 : 1);
			double r1 = radius * Math.cos(angle);
			double r2 = radius * Math.sin(angle);
			this.drawTexturedModalRect((int)r1 + x, (int)r2 + y, 0, 0, 15, 15);
			
			if (i < (currentPower / mod))
				this.drawTexturedModalRect((int)r1 + x, (int)r2 + y, 15, 0, 15, 15);

			if ((i * mod) + (capacity / (count * 2)) <= currentPower)
				this.drawTexturedModalRect((int)r1 + x, (int)r2 + y, 30, 0, 15, 15);
		}
		GlStateManager.disableBlend();
	}
	
    private float lerp(float a, float b, float f) {
    	
    	return a + f * (b - a);
    }
}
