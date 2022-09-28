package com.bafomdad.uniquecrops.render.tile;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.tiles.TileSunBlock;
import com.bafomdad.uniquecrops.render.CustomRenderType;
import com.mojang.blaze3d.vertex.*;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.Util;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

public class RenderSunBlock implements BlockEntityRenderer<TileSunBlock> {

    public static final ResourceLocation RES = new ResourceLocation(UniqueCrops.MOD_ID, "textures/models/sunglow.png");
    private final BlockRenderDispatcher renderDispatcher;

    public RenderSunBlock(BlockEntityRendererProvider.Context ctx) {

        this.renderDispatcher = ctx.getBlockRenderDispatcher();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void render(TileSunBlock tile, float partialTicks, PoseStack ms, @NotNull MultiBufferSource buffer, int light, int overlay) {

        ms.pushPose();
        ms.translate(0.5F, 0.1F, 0.5F);

        Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(RES);
        RenderSystem.enableBlend();

        float phase = (float)(Util.getMillis() % 2000L) / 2000.0F;
        float power = tile.powerlevel * 1.0F / TileSunBlock.MAX_POWER;
        if (tile.powerlevel > 1) {
            for (int i = 0; i < 7; i++) {
                ms.pushPose();
                ms.translate((float)Math.cos(2.356194490192345D * i) * 0.5F * power, 0.0F, (float)Math.sin(2.356194490192345D * i) * 0.5F * power);
                float playerView = Minecraft.getInstance().gameRenderer.getMainCamera().getYRot();
                ms.mulPose(Vector3f.YP.rotationDegrees(180.0F - playerView));
                RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

                float r = Math.max(phase, 1.0F);
                float g = r;
                float b = g;
                float a = 0.5F * (1.0F - phase);

                float w = 1.0F;
                float h = 40.0F * phase * power;

                VertexConsumer buff = buffer.getBuffer(CustomRenderType.CUSTOM_BEAM.apply(RES, true));
                Matrix4f mat = ms.last().pose();
                buff.vertex(mat, -0.5F * w, -0.25F, 0.0F).color(r, g, b, a).uv(0, 1).uv2(15728880).normal(1, 0, 0).endVertex();
                buff.vertex(mat,0.5F * w, -0.25F, 0.0F).color(r, g, b, a).uv(1, 1).uv2(15728880).normal(1, 0, 0).endVertex();
                buff.vertex(mat,0.5F, 0.75F * h, 0.0F).color(r, g, b, a).uv(1, 0).uv2(15728880).normal(1, 0, 0).endVertex();
                buff.vertex(mat, -0.5F, 0.75F * h, 0.0F).color(r, g, b, a).uv(0, 0).uv2(15728880).normal(1, 0, 0).endVertex();

                phase = (float)(phase + 0.125D);
                if (phase > 1.0F)
                    phase -= 1.0F;
                ms.popPose();
            }
        }
        RenderSystem.disableBlend();
        ms.popPose();
    }
}
