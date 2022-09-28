package com.bafomdad.uniquecrops.render.tile;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.blocks.tiles.TileSucco;
import com.bafomdad.uniquecrops.render.CustomRenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.stream.IntStream;

public class RenderSucco implements BlockEntityRenderer<TileSucco> {

    private final BlockRenderDispatcher renderDispatcher;
    private int[] textureSkipper = IntStream.of(1, 2, 2, 3, 3, 4, 4, 5).toArray();

    public RenderSucco(BlockEntityRendererProvider.Context ctx) {

        this.renderDispatcher = ctx.getBlockRenderDispatcher();
    }

    @Override
    public void render(TileSucco te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {

        int age = te.getBlockState().getValue(BaseCropsBlock.AGE);
        ResourceLocation res = new ResourceLocation(UniqueCrops.MOD_ID, "textures/block/vampire" + textureSkipper[age] + ".png");
        final VertexConsumer buff = buffer.getBuffer(CustomRenderType.CUSTOM_BEAM.apply(res, true));

        ms.pushPose();

        float r = 1.0F, g = r, b = g;
        float moon = DimensionType.MOON_BRIGHTNESS_PER_PHASE[Minecraft.getInstance().level.getMoonPhase()];
        Matrix4f mat = ms.last().pose();

        ms.translate(0.5, 0.45, 0.5);
        ms.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
        this.quad(buff, mat, 0.5F, 0.5F, 0, moon);
        ms.mulPose(Vector3f.XP.rotationDegrees(180.0F));
        this.quad(buff, mat, 0.5F, 0.5F, 0, moon);

        ms.popPose();
    }

    private void quad(VertexConsumer buff, Matrix4f mat, float x, float y, float z, float a) {

        buff.vertex(mat, -x, -y, z).color(1.0F, 1.0F, 1.0F, a).uv(0, 1).uv2(15728880).normal(1, 0, 0).endVertex();
        buff.vertex(mat, -x, y, z).color(1.0F, 1.0F, 1.0F, a).uv(1, 1).uv2(15728880).normal(1, 0, 0).endVertex();
        buff.vertex(mat, x, y, z).color(1.0F, 1.0F, 1.0F, a).uv(1, 0).uv2(15728880).normal(1, 0, 0).endVertex();
        buff.vertex(mat, x, -y, z).color(1.0F, 1.0F, 1.0F, a).uv(0, 0).uv2(15728880).normal(1, 0, 0).endVertex();

        buff.vertex(mat, -x, -y + 0.5F, z + 0.5F).color(1.0F, 1.0F, 1.0F, a).uv(0, 1).uv2(15728880).normal(1, 0, 0).endVertex(); // bottom z
        buff.vertex(mat, -x, y - 0.5F, z - 0.5F).color(1.0F, 1.0F, 1.0F, a).uv(1, 1).uv2(15728880).normal(1, 0, 0).endVertex();
        buff.vertex(mat, x, y - 0.5F, z - 0.5F).color(1.0F, 1.0F, 1.0F, a).uv(1, 0).uv2(15728880).normal(1, 0, 0).endVertex();
        buff.vertex(mat, x, -y + 0.5F, z + 0.5F).color(1.0F, 1.0F, 1.0F, a).uv(0, 0).uv2(15728880).normal(1, 0, 0).endVertex(); // top z
    }
}
