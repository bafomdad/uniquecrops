package com.bafomdad.uniquecrops.gui;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.UCUtils;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class GuiEulaBook extends Screen {

    public static ResourceLocation RES = new ResourceLocation(UniqueCrops.MOD_ID, "textures/gui/bookeula.png");
    public final int WIDTH = 175;
    public final int HEIGHT = 228;
    public final int WORDWRAP = WIDTH - 40;

    private Button next, prev, agree, disagree;

    private int pageIndex = 0;

    public GuiEulaBook() {

        super(StringTextComponent.EMPTY);
    }

    @Override
    public void init() {

        super.init();
        int k = (this.width - this.WIDTH) / 2;
        int l = (this.height - this.HEIGHT) / 2;
        this.buttons.add(this.next = new GuiButtonPageChange(k + WIDTH - 26, l + 210, false, (button) -> { this.pageIndex++; }));
        this.buttons.add(this.prev = new GuiButtonPageChange(k + 10, l + 210, true, (button) -> { --this.pageIndex; }));
        this.buttons.add(this.agree = new GuiButtonEula(k + 65, l + 210, true, (button) -> { minecraft.setScreen(null); }));
        this.buttons.add(this.disagree = new GuiButtonEula(k + 95, l + 210, false, (button) -> { this.pageIndex = 0; }));
        updateButtons();
    }

    private void updateButtons() {

        this.next.visible = (this.pageIndex < 7);
        this.prev.visible = (this.pageIndex > 0);
        this.agree.visible = this.pageIndex == 7;
        this.disagree.visible = this.pageIndex == 7;
    }

    @Override
    public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {

        this.renderBackground(ms);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        ms.pushPose();
        minecraft.textureManager.bind(RES);
        int k = (this.width - this.WIDTH) / 2;
        int b0 = (this.height - this.HEIGHT) / 2;
        blit(ms, k, b0, 0, 0, WIDTH, HEIGHT);
        ITextComponent text = new TranslationTextComponent(UniqueCrops.MOD_ID + ".eula.text" + pageIndex);
        UCUtils.drawSplitString(ms, font, text, k + 25, b0 + 15, WORDWRAP, TextFormatting.GRAY.getColor());
        ms.popPose();
        super.render(ms, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {

        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        if (button == 0) {
            for (Widget wig : buttons) {
                if (wig.visible && wig.isHovered()) {
                    wig.onClick(mouseX, mouseY);
                    updateButtons();
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen() {

        return false;
    }

    private class GuiButtonEula extends Button {

        final boolean agree;

        public GuiButtonEula(int x, int y, boolean agree, IPressable pressedAction) {

            super(x, y, 16, 16, StringTextComponent.EMPTY, pressedAction);
            this.agree = agree;
        }

        @Override
        public void renderButton(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {

            if (visible) {
                boolean mouseOver = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                minecraft.textureManager.bind(RES);
                int u = 175;
                int v = 34;

                if (mouseOver) v+= 17;
                if (agree) u += 17;

                blit(ms, this.x, this.y, u, v, 16, 16);
            }
        }
    }

    private class GuiButtonPageChange extends Button {

        final boolean previous;

        public GuiButtonPageChange(int x, int y, boolean previous, IPressable pressedAction) {

            super(x, y, 16, 16, StringTextComponent.EMPTY, pressedAction);
            this.previous = previous;
        }

        @Override
        public void renderButton(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {

            if (visible) {
                boolean mouseOver = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                minecraft.textureManager.bind(RES);
                int u = 175;
                int v = 0;

                if (mouseOver) v += 17;
                if (previous) u += 17;

                blit(ms, this.x, this.y, u, v, 16, 16);
            }
        }
    }
}
