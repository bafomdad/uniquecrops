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
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {

        this.renderBackground(ms);
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderTooltip(ms, mouseX, mouseY);
    }

    @Override
    public void renderLabels(MatrixStack ms, int x, int y) {

        String s = "Crafty Plant";
        font.draw(ms, s, imageWidth / 2 - font.width(s), 6, 4210752);
    }

    @Override
    protected void renderBg(MatrixStack ms, float partialTicks, int x, int y) {

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.textureManager.bind(TEXTURE);

        int k = (this.width - this.imageWidth) / 2;
        int l = (this.height - this.imageHeight) / 2;
        blit(ms, k, l, 0, 0, this.imageWidth, this.imageHeight);
    }
}
