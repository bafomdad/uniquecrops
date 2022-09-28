package com.bafomdad.uniquecrops.render.tile;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.tiles.TileItero;
import com.mojang.blaze3d.vertex.*;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import org.lwjgl.opengl.GL11;

public class RenderItero implements BlockEntityRenderer<TileItero> {

    private final BlockRenderDispatcher renderDispatcher;
    private static final ResourceLocation RES = new ResourceLocation(UniqueCrops.MOD_ID, "textures/models/sunglow.png");

    public RenderItero(BlockEntityRendererProvider.Context ctx) {

        this.renderDispatcher = ctx.getBlockRenderDispatcher();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void render(TileItero te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {

        if (!te.showingDemo()) return;

        ms.pushPose();
        ms.translate(0.5, 0.1, 0.5);

        RenderSystem.setShaderTexture(0, RES);
        Tesselator tess = Tesselator.getInstance();
//        RenderSystem.disableAlphaTest();
//        RenderSystem.disableLighting();
        RenderSystem.enableBlend();

        for  (int i = 0; i < TileItero.PLATES.length; i++) {
            BlockPos platePos = te.getBlockPos().offset(TileItero.PLATES[i]);
            BlockState state = te.getLevel().getBlockState(platePos);
            if (state.getBlock() == Blocks.STONE_PRESSURE_PLATE && state.getValue(PressurePlateBlock.POWERED)) {
                ms.pushPose();
                ms.translate(TileItero.PLATES[i].getX(), 0, TileItero.PLATES[i].getZ());
                this.renderLight(ms, tess, i);
                ms.popPose();
                break;
            }
        }
        RenderSystem.disableBlend();
//        RenderSystem.enableAlphaTest();
//        RenderSystem.enableLighting();
        ms.popPose();
    }

    private void renderLight(PoseStack ms, Tesselator tess, int color) {

        for (int j = 0; j < 4; j++) {
            ms.pushPose();
            switch(j) {
                case 0: ms.translate(0, 0, 0.375F); break;
                case 1: ms.translate(0.375F, 0, 0); break;
                case 2: ms.translate(0, 0, -0.375F); break;
                case 3: ms.translate(-0.375F, 0, 0); break;
            }
            ms.mulPose(Vector3f.YP.rotationDegrees(j * 90.0F));
            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            float power = 2.5F;
            float phase = 0.1F;

            BufferBuilder buff = tess.getBuilder();
            buff.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            RenderSystem.setShaderColor(Math.max(phase * 2.0F, 1.0F), Math.max(phase * 2.0F, 1.0F), Math.max(phase * 2.0F, 1.0F), 0.5F * (1.0F - phase));
            switch(color) {
                case 0: RenderSystem.setShaderColor(255.0F, 0F, 0F, 1.0F); break;
                case 1: RenderSystem.setShaderColor(0F, 255.0F, 0F, 1.0F); break;
                case 2: RenderSystem.setShaderColor(0F, 0F, 255.0F, 1.0F); break;
                case 3: RenderSystem.setShaderColor(255.0F, 255.0F, 0F, 1.0F); break;
            }
            float w = 1.0F;
            float h = 40.0F * phase * power;

            Matrix4f mat = ms.last().pose();
            buff.vertex(mat, -0.5F * w, -0.25F, 0.0F).uv(0.0F, 1.0F).endVertex();
            buff.vertex(mat,0.5F * w, -0.25F, 0.0F).uv(1.0F, 1.0F).endVertex();
            buff.vertex(mat,0.5F, 0.75F * h, 0.0F).uv(1.0F, 0.0F).endVertex();
            buff.vertex(mat, -0.5F, 0.75F * h, 0.0F).uv(0.0F, 0.0F).endVertex();

            tess.end();
            ms.popPose();
        }
    }
}
