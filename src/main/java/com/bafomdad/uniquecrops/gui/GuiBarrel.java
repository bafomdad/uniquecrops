package com.bafomdad.uniquecrops.gui;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

public class GuiBarrel extends AbstractContainerScreen<ContainerBarrel> {

    private static final ResourceLocation RES = new ResourceLocation(UniqueCrops.MOD_ID, "textures/gui/barrel.png");

    public GuiBarrel(ContainerBarrel container, Inventory inv, Component title) {

        super(container, inv, title);
    }

    @Override
    public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {

        this.renderBackground(ms);
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderTooltip(ms, mouseX, mouseY);
    }

    @Override
    public void renderLabels(PoseStack ms, int x, int y) {

        String s = "Abstract Barrel";
        this.font.draw(ms, s, this.imageWidth / 2 - this.font.width(s) / 2, 6, 4210752);
    }

    @Override
    public void renderBg(PoseStack ms, float partialTicks, int x, int y) {

        RenderSystem.setShaderColor(1.0f, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, RES);
        this.blit(ms, leftPos, topPos, 0, 0, imageWidth, imageHeight);
    }
}
