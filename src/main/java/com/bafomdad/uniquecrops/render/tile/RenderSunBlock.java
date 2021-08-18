package com.bafomdad.uniquecrops.render.tile;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.tiles.TileSunBlock;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import org.lwjgl.opengl.GL11;

public class RenderSunBlock extends TileEntityRenderer<TileSunBlock> {

    private static final ResourceLocation TEX = new ResourceLocation(UniqueCrops.MOD_ID, "textures/models/sunglow.png");

    public RenderSunBlock(TileEntityRendererDispatcher manager) {

        super(manager);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void render(TileSunBlock tile, float partialTicks, MatrixStack ms, IRenderTypeBuffer buffer, int light, int overlay) {

        ms.pushPose();
        ms.translate(0.5F, 0.1F, 0.5F);

        Minecraft.getInstance().textureManager.bind(TEX);
        Tessellator tess = Tessellator.getInstance();
        RenderSystem.disableAlphaTest();
        RenderSystem.disableLighting();
        RenderSystem.enableBlend();

        float phase = (float)(Util.getMillis() % 2000L) / 2000.0F;
        float power = tile.powerlevel * 1.0F / tile.MAX_POWER;
        if (tile.powerlevel > 0) {
            for (int i = 0; i < 7; i++) {
                ms.pushPose();
                ms.translate((float)Math.cos(2.356194490192345D * i) * 0.5F * power, 0.0F, (float)Math.sin(2.356194490192345D * i) * 0.5F * power);
                float playerView = Minecraft.getInstance().gameRenderer.getMainCamera().getYRot();
                ms.mulPose(Vector3f.YP.rotationDegrees(180.0F - playerView));
                RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

                BufferBuilder buff = tess.getBuilder();
                buff.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
                RenderSystem.color4f(Math.max(phase * 2.0F, 1.0F), Math.max(phase * 2.0F, 1.0F), Math.max(phase * 2.0F, 1.0F), 0.5F * (1.0F - phase));
                float w = 1.0F;
                float h = 40.0F * phase * power;

                Matrix4f mat = ms.last().pose();
                buff.vertex(mat, -0.5F * w, -0.25F, 0.0F).uv(0.0F, 1.0F).endVertex();
                buff.vertex(mat,0.5F * w, -0.25F, 0.0F).uv(1.0F, 1.0F).endVertex();
                buff.vertex(mat,0.5F, 0.75F * h, 0.0F).uv(1.0F, 0.0F).endVertex();
                buff.vertex(mat, -0.5F, 0.75F * h, 0.0F).uv(0.0F, 0.0F).endVertex();

                tess.end();
                phase = (float)(phase + 0.125D);
                if (phase > 1.0F)
                    phase -= 1.0F;
                ms.popPose();
            }
        }
        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableLighting();

        ms.popPose();
    }
}
