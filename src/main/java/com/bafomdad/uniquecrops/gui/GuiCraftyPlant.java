package com.bafomdad.uniquecrops.gui;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

public class GuiCraftyPlant extends AbstractContainerScreen<ContainerCraftyPlant> {

    private static final ResourceLocation RES = new ResourceLocation(UniqueCrops.MOD_ID, "textures/gui/guicraftyplant.png");

    public GuiCraftyPlant(ContainerCraftyPlant container, Inventory inv, Component title) {

        super(container, inv, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {

        this.renderBackground(ms);
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderTooltip(ms, mouseX, mouseY);
    }

    @Override
    public void renderLabels(PoseStack ms, int x, int y) {

        String s = "Crafty Plant";
        font.draw(ms, s, imageWidth / 2 - font.width(s), 6, 4210752);
    }

    @Override
    protected void renderBg(PoseStack ms, float partialTicks, int x, int y) {

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, RES);

        int k = (this.width - this.imageWidth) / 2;
        int l = (this.height - this.imageHeight) / 2;
        blit(ms, k, l, 0, 0, this.imageWidth, this.imageHeight);
    }
}
