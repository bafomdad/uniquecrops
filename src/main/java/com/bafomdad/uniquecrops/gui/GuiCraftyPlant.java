package com.bafomdad.uniquecrops.gui;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class GuiCraftyPlant extends ContainerScreen<ContainerCraftyPlant> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(UniqueCrops.MOD_ID, "textures/gui/guicraftyplant.png");

    public GuiCraftyPlant(ContainerCraftyPlant container, PlayerInventory inv, ITextComponent title) {

        super(container, inv, title);
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {

        this.renderBackground(ms);
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(ms, mouseX, mouseY);
    }

    @Override
    public void drawGuiContainerForegroundLayer(MatrixStack ms, int x, int y) {

        String s = "Crafty Plant";
        font.drawString(ms, s, xSize / 2 - font.getStringWidth(s), 6, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float partialTicks, int x, int y) {

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.textureManager.bindTexture(TEXTURE);

        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        blit(ms, k, l, 0, 0, this.xSize, this.ySize);
    }
}
