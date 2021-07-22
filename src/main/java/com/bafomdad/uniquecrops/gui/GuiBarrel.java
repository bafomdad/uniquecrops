package com.bafomdad.uniquecrops.gui;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class GuiBarrel extends ContainerScreen<ContainerBarrel> {

    private static final ResourceLocation RES = new ResourceLocation(UniqueCrops.MOD_ID, "textures/gui/barrel.png");

    public GuiBarrel(ContainerBarrel container, PlayerInventory inv, ITextComponent title) {

        super(container, inv, title);
    }

    @Override
    public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {

        this.renderBackground(ms);
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(ms, mouseX, mouseY);
    }

    @Override
    public void drawGuiContainerForegroundLayer(MatrixStack ms, int x, int y) {

        String s = "Abstract Barrel";
        this.font.drawString(ms, s, this.xSize / 2 - this.font.getStringWidth(s) / 2, 6, 4210752);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(MatrixStack ms, float partialTicks, int x, int y) {

        RenderSystem.color4f(1.0f, 1.0F, 1.0F, 1.0F);
        this.minecraft.textureManager.bindTexture(RES);
        this.blit(ms, guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
