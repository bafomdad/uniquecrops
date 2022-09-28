package com.bafomdad.uniquecrops.render.tile;

import com.bafomdad.uniquecrops.blocks.tiles.TileArtisia;
import com.bafomdad.uniquecrops.blocks.tiles.TileLacusia;
import com.bafomdad.uniquecrops.blocks.tiles.TileWeatherflesia;
import com.bafomdad.uniquecrops.events.UCTickHandler;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;

import java.util.Random;

public class RenderItemTile {

    public static class Weatherflesia implements BlockEntityRenderer<TileWeatherflesia> {

        private final BlockRenderDispatcher renderDispatcher;

        public Weatherflesia(BlockEntityRendererProvider.Context ctx) {

            this.renderDispatcher = ctx.getBlockRenderDispatcher();
        }

        @Override
        public void render(TileWeatherflesia tile, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {

            BlockState state = tile.getLevel().getBlockState(tile.getBlockPos());
            if (state != null && state.getBlock() == UCBlocks.WEATHERFLESIA.get()) {
                ItemStack stack = tile.getBrush();
                if (stack.isEmpty()) return;

                double time = UCTickHandler.ticksInGame + UCTickHandler.partialTicks;
                int multiplier = stack.getMaxDamage() - stack.getDamageValue();
                float scale = (multiplier / 300.0F) * 0.5F;
                double wave = Math.sin(time * 0.2) / 32F;
                float f = (float)(Math.max(Math.sin(time * 0.025F), 0.5));
                float f1 = 0.0F;

                if (f > 0.8F)
                    f1 = (f - 0.8F) / 0.2F;

                ms.pushPose();
                RenderSystem.disableDepthTest();
                ms.translate(0.5D, 0.85 + wave, 0.5D);
                renderItem(stack, ms, light, overlay, buffer);
                RenderSystem.enableDepthTest();
                ms.popPose();

                Random random = new Random(432L);
                VertexConsumer ivertexbuilder2 = buffer.getBuffer(RenderType.lightning());
                ms.pushPose();
                ms.translate(0.5D, 0.85, 0.5D);
                ms.scale(scale, scale, scale);
                ms.mulPose(Vector3f.XP.rotationDegrees((float)time * 4F));
                ms.mulPose(Vector3f.YP.rotationDegrees((float)time * 4F));

                for(int i = 0; (float)i < (f + f * f) / 2.0F * 60.0F; ++i) {
                    ms.mulPose(Vector3f.XP.rotationDegrees(random.nextFloat() * 360.0F));
                    ms.mulPose(Vector3f.YP.rotationDegrees(random.nextFloat() * 360.0F));
                    ms.mulPose(Vector3f.ZP.rotationDegrees(random.nextFloat() * 360.0F));
                    ms.mulPose(Vector3f.XP.rotationDegrees(random.nextFloat() * 360.0F));
                    ms.mulPose(Vector3f.YP.rotationDegrees(random.nextFloat() * 360.0F));
                    ms.mulPose(Vector3f.ZP.rotationDegrees(random.nextFloat() * 360.0F + f1 * 90.0F));
                    float f3 = random.nextFloat() * 20.0F + 5.0F + f1 * 10.0F;
                    float f4 = random.nextFloat() * 2.0F + 1.0F + f1 * 2.0F;
                    Matrix4f matrix4f = ms.last().pose();
                    int j = (int)(255.0F * (1.0F - f1));
                    func_229061_a_(ivertexbuilder2, matrix4f, j);
                    func_229060_a_(ivertexbuilder2, matrix4f, f3, f4);
                    func_229062_b_(ivertexbuilder2, matrix4f, f3, f4);
                    func_229061_a_(ivertexbuilder2, matrix4f, j);
                    func_229062_b_(ivertexbuilder2, matrix4f, f3, f4);
                    func_229063_c_(ivertexbuilder2, matrix4f, f3, f4);
                    func_229061_a_(ivertexbuilder2, matrix4f, j);
                    func_229063_c_(ivertexbuilder2, matrix4f, f3, f4);
                    func_229060_a_(ivertexbuilder2, matrix4f, f3, f4);
                }
                ms.popPose();
            }
        }
    }

    public static class Artisia implements BlockEntityRenderer<TileArtisia> {

        private final BlockRenderDispatcher renderDispatcher;

        public Artisia(BlockEntityRendererProvider.Context ctx) {

            this.renderDispatcher = ctx.getBlockRenderDispatcher();
        }

        @Override
        public void render(TileArtisia tile, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {

            if (!tile.getItem().isEmpty()) {
                ms.pushPose();
                ms.translate(0.5F, 1.25F, 0.5F);
                renderItem(tile.getItem(), ms, light, overlay, buffer);
                ms.popPose();
            }
        }
    }

    public static class Lacusia implements BlockEntityRenderer<TileLacusia> {

        private final BlockRenderDispatcher renderDispatcher;

        public Lacusia(BlockEntityRendererProvider.Context ctx) {

            this.renderDispatcher = ctx.getBlockRenderDispatcher();
        }

        @Override
        public void render(TileLacusia tile, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {

            BlockState state = tile.getLevel().getBlockState(tile.getBlockPos());
            if (state.getBlock() == UCBlocks.LACUSIA_CROP.get()) {
                ItemStack stack = tile.getItem();
                if (!stack.isEmpty()) {
                    ms.pushPose();
                    ms.translate(0.5, 0.65, 0.5);
                    renderItem(stack, ms, light, overlay, buffer);
                    ms.popPose();
                }
            }
        }
    }

    public static void renderItem(ItemStack stack, PoseStack ms, int light, int overlay, MultiBufferSource buffer) {

        float toScale = (stack.getItem() instanceof BlockItem && !(stack.getItem() instanceof ItemNameBlockItem)) ? 0.25F : 0.4375F;
        ms.scale(toScale, toScale, toScale);
        float playerView = Minecraft.getInstance().gameRenderer.getMainCamera().getYRot();
        ms.mulPose(Vector3f.YP.rotationDegrees(180.0F - playerView));
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.NONE, light, overlay, ms, buffer, 0);
    }

    private static final float field_229057_l_ = (float)(Math.sqrt(3.0D) / 2.0D);

    private static void func_229061_a_(VertexConsumer p_229061_0_, Matrix4f p_229061_1_, int p_229061_2_) {
        p_229061_0_.vertex(p_229061_1_, 0.0F, 0.0F, 0.0F).color(255, 255, 255, p_229061_2_).endVertex();
        p_229061_0_.vertex(p_229061_1_, 0.0F, 0.0F, 0.0F).color(255, 255, 255, p_229061_2_).endVertex();
    }

    private static void func_229060_a_(VertexConsumer p_229060_0_, Matrix4f p_229060_1_, float p_229060_2_, float p_229060_3_) {
        p_229060_0_.vertex(p_229060_1_, -field_229057_l_ * p_229060_3_, p_229060_2_, -0.5F * p_229060_3_).color(255, 0, 255, 0).endVertex();
    }

    private static void func_229062_b_(VertexConsumer p_229062_0_, Matrix4f p_229062_1_, float p_229062_2_, float p_229062_3_) {
        p_229062_0_.vertex(p_229062_1_, field_229057_l_ * p_229062_3_, p_229062_2_, -0.5F * p_229062_3_).color(255, 0, 255, 0).endVertex();
    }

    private static void func_229063_c_(VertexConsumer p_229063_0_, Matrix4f p_229063_1_, float p_229063_2_, float p_229063_3_) {
        p_229063_0_.vertex(p_229063_1_, 0.0F, p_229063_2_, 1.0F * p_229063_3_).color(255, 0, 255, 0).endVertex();
    }
}
