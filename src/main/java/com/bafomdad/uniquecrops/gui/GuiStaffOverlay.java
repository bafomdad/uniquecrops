package com.bafomdad.uniquecrops.gui;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.capabilities.CPProvider;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.profiler.IProfiler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;

public class GuiStaffOverlay {

    private static final ResourceLocation TEX = new ResourceLocation(UniqueCrops.MOD_ID, "textures/gui/leaf_bar.png");
    Minecraft mc;

    long lastSystemTime, staffUpdateCounter, updateCounter;
    int capacity;

    public GuiStaffOverlay(Minecraft mc) {

        this.mc = mc;
    }

    public void renderOverlay(RenderGameOverlayEvent.Post event) {

        IProfiler profiler = mc.getProfiler();
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            if (mc.screen != null) return;
            mc.player.getMainHandItem().getCapability(CPProvider.CROP_POWER, null).ifPresent(crop -> {
                profiler.push("UC-hud");
                MatrixStack ms = event.getMatrixStack();
                ms.pushPose();
                MainWindow scaled = event.getWindow();
                int i = crop.getPower();
                boolean flag = this.staffUpdateCounter > this.updateCounter;
                if (flag)
                    RenderSystem.color3f(0.0F, 1.0F, 0.0F);
                RenderSystem.disableLighting();

                float scale = (float)Math.sin(Util.getMillis() / 500D) * 0.01562F;

                if (i < this.capacity) {
                    this.lastSystemTime = Util.getMillis();
                    this.staffUpdateCounter = this.updateCounter + 200;
                }
                if (i > this.capacity) {
                    this.lastSystemTime = Util.getMillis();
                    this.staffUpdateCounter = this.updateCounter + 200;
                }
                if (Util.getMillis() - this.lastSystemTime > 1000L) {
                    this.capacity = i;
                    this.lastSystemTime = Util.getMillis();
                }
                RenderSystem.scalef(1 + scale, 1 + scale, 1 + scale);
                this.renderLeafBar(ms, scaled, crop.getPower(), crop.getCapacity());
                ms.popPose();
                this.capacity = i;

                profiler.pop();
            });
        }
    }

    private void renderLeafBar(MatrixStack ms, MainWindow res, int currentPower, int capacity) {

        if (capacity <= 0) return;

        int x = res.getGuiScaledWidth() / 2 + UCConfig.CLIENT.guiWidth.get();
        int y = res.getGuiScaledHeight() + UCConfig.CLIENT.guiHeight.get();

        mc.getTextureManager().bind(TEX);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        int count = 5;
        if (capacity < count)
            count = capacity;
        double start = Math.PI / 2;
        int direction = -1;
        int radius = 15;
        double interval = 2 * Math.PI / count;
        int mod = capacity / count;

        for (int i = 0; i < count; ++i) {
            double angle = start + i * interval * (direction < 0 ? -1 : 1);
            double r1 = radius * Math.cos(angle);
            double r2 = radius * Math.sin(angle);
            AbstractGui.blit(ms, (int)r1 + x, (int)r2 + y, 0, 0, 15, 15, 256, 256);

            if (i < (currentPower / mod))
                AbstractGui.blit(ms, (int)r1 + x, (int)r2 + y, 15, 0, 15, 15, 256, 256);

            if ((i * mod) + (capacity / (count * 2)) <= currentPower)
                AbstractGui.blit(ms, (int)r1 + x, (int)r2 + y, 30, 0, 15, 15, 256, 256);

            RenderSystem.disableBlend();
        }
    }
}
