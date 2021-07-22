package com.bafomdad.uniquecrops.render.tile;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.tiles.TileItero;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import org.lwjgl.opengl.GL11;

public class RenderItero extends TileEntityRenderer<TileItero> {

    private static final ResourceLocation TEX = new ResourceLocation(UniqueCrops.MOD_ID, "textures/models/sunglow.png");

    public RenderItero(TileEntityRendererDispatcher manager) {

        super(manager);
    }

    @Override
    public void render(TileItero te, float partialTicks, MatrixStack ms, IRenderTypeBuffer buffer, int light, int overlay) {

        if (!te.showingDemo()) return;

        ms.push();
        ms.translate(0.5, 0.1, 0.5);

        Minecraft.getInstance().textureManager.bindTexture(TEX);
        Tessellator tess = Tessellator.getInstance();
        RenderSystem.disableAlphaTest();
        RenderSystem.disableLighting();
        RenderSystem.enableBlend();

        for  (int i = 0; i < TileItero.PLATES.length; i++) {
            BlockPos platePos = te.getPos().add(TileItero.PLATES[i]);
            BlockState state = te.getWorld().getBlockState(platePos);
            if (state.getBlock() == Blocks.STONE_PRESSURE_PLATE && state.get(PressurePlateBlock.POWERED)) {
                ms.push();
                ms.translate(TileItero.PLATES[i].getX(), 0, TileItero.PLATES[i].getZ());
                this.renderLight(ms, tess, i);
                ms.pop();
                break;
            }
        }
        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableLighting();
        ms.pop();
    }

    private void renderLight(MatrixStack ms, Tessellator tess, int color) {

        for (int j = 0; j < 4; j++) {
            ms.push();
            switch(j) {
                case 0: ms.translate(0, 0, 0.375F); break;
                case 1: ms.translate(0.375F, 0, 0); break;
                case 2: ms.translate(0, 0, -0.375F); break;
                case 3: ms.translate(-0.375F, 0, 0); break;
            }
            ms.rotate(Vector3f.YP.rotationDegrees(j * 90.0F));
            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            float power = 2.5F;
            float phase = 0.1F;

            BufferBuilder buff = tess.getBuffer();
            buff.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
            RenderSystem.color4f(Math.max(phase * 2.0F, 1.0F), Math.max(phase * 2.0F, 1.0F), Math.max(phase * 2.0F, 1.0F), 0.5F * (1.0F - phase));
            switch(color) {
                case 0: RenderSystem.color3f(255.0F, 0F, 0F); break;
                case 1: RenderSystem.color3f(0F, 255.0F, 0F); break;
                case 2: RenderSystem.color3f(0F, 0F, 255.0F); break;
                case 3: RenderSystem.color3f(255.0F, 255.0F, 0F); break;
            }
            float w = 1.0F;
            float h = 40.0F * phase * power;

            Matrix4f mat = ms.getLast().getMatrix();
            buff.pos(mat, -0.5F * w, -0.25F, 0.0F).tex(0.0F, 1.0F).endVertex();
            buff.pos(mat,0.5F * w, -0.25F, 0.0F).tex(1.0F, 1.0F).endVertex();
            buff.pos(mat,0.5F, 0.75F * h, 0.0F).tex(1.0F, 0.0F).endVertex();
            buff.pos(mat, -0.5F, 0.75F * h, 0.0F).tex(0.0F, 0.0F).endVertex();

            tess.draw();
            ms.pop();
        }
    }
}
