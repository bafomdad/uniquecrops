package com.bafomdad.uniquecrops.integration.patchouli.component;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.integration.patchouli.PatchouliUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;
import net.minecraft.network.chat.Component;
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
    public void render(PoseStack ms, IComponentRenderContext ctx, float pticks, int mouseX, int mouseY) {

        Minecraft mc = Minecraft.getInstance();
        Font font = mc.font;
        RenderSystem.setShaderTexture(0, RES);
        Component name = state.getBlock().getName();
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
        BlockRenderDispatcher brd = mc.getBlockRenderer();
        MultiBufferSource.BufferSource renderBuffer = mc.renderBuffers().bufferSource();
        int color = mc.getBlockColors().getColor(state, null, null, 0);
        float r = (float)(color >> 16 & 255) / 255.0F;
        float g = (float)(color >> 8 & 255) / 255.0F;
        float b = (float)(color & 255) / 255.0F;
        BakedModel model = brd.getBlockModel(state);
        VertexConsumer vertex = renderBuffer.getBuffer(RenderType.cutout());
        brd.getModelRenderer().renderModel(ms.last(), vertex, state, model, r, g, b, 0xF000F0, OverlayTexture.NO_OVERLAY);
        renderBuffer.endBatch();
        ms.popPose();
    }

    @Override
    public void onVariablesAvailable(UnaryOperator<IVariable> lookup) {

        IVariable blockVar = lookup.apply(blockstate);
        state = PatchouliUtils.deserialize(blockVar.asString());
    }
}
