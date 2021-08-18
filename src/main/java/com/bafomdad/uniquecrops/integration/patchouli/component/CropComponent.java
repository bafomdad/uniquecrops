package com.bafomdad.uniquecrops.integration.patchouli.component;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.integration.patchouli.PatchouliUtils;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.model.data.EmptyModelData;
import vazkii.patchouli.api.IComponentRenderContext;
import vazkii.patchouli.api.ICustomComponent;
import vazkii.patchouli.api.IVariable;

import java.util.function.UnaryOperator;

public class CropComponent implements ICustomComponent {

    final ResourceLocation RES = new ResourceLocation(UniqueCrops.MOD_ID, "textures/gui/croppage.png");
    private transient int x, y;
    private transient BlockState state;

    IVariable blockstate;

    @Override
    public void build(int cx, int cy, int pageNum) {

        this.x = cx;
        this.y = cy;
    }

    @Override
    public void render(MatrixStack ms, IComponentRenderContext ctx, float pticks, int mouseX, int mouseY) {

        Minecraft mc = Minecraft.getInstance();
        FontRenderer font = mc.font;
        mc.textureManager.bind(RES);
        ITextComponent name = state.getBlock().getName();
        ms.pushPose();
        ms.scale(0.9F, 0.9F, 0.9F);
        ctx.getGui().blit(ms, x / 2 - 25, y / 2 - 60, 0, 0, 175, 228);
        ms.popPose();
        font.drawShadow(ms, name, x + 102 / 2 - font.width(name) / 2, y + 41, 0);
        ms.pushPose();
        ms.translate(25, 58, 100);
        ms.mulPose(Vector3f.XP.rotationDegrees(145.0F));
        ms.mulPose(Vector3f.YP.rotationDegrees(45.0F));
        ms.scale(45, 45, 45);
        BlockRendererDispatcher brd = mc.getBlockRenderer();
        IRenderTypeBuffer.Impl renderBuffer = mc.renderBuffers().bufferSource();
        brd.renderBlock(state, ms, renderBuffer, 15728880, OverlayTexture.NO_OVERLAY, EmptyModelData.INSTANCE);
        renderBuffer.endBatch();
        ms.popPose();
    }

    @Override
    public void onVariablesAvailable(UnaryOperator<IVariable> lookup) {

        IVariable blockVar = lookup.apply(blockstate);
        state = PatchouliUtils.deserialize(blockVar.asString());
    }
}
