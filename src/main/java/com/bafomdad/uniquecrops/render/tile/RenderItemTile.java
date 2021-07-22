package com.bafomdad.uniquecrops.render.tile;

import com.bafomdad.uniquecrops.blocks.tiles.TileArtisia;
import com.bafomdad.uniquecrops.blocks.tiles.TileLacusia;
import com.bafomdad.uniquecrops.blocks.tiles.TileWeatherflesia;
import com.bafomdad.uniquecrops.events.UCTickHandler;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;

import java.util.Random;

public class RenderItemTile {

    public static class Weatherflesia extends TileEntityRenderer<TileWeatherflesia> {

        public Weatherflesia(TileEntityRendererDispatcher manager) {

            super(manager);
        }

        @Override
        public void render(TileWeatherflesia tile, float partialTicks, MatrixStack ms, IRenderTypeBuffer buffer, int light, int overlay) {

            BlockState state = tile.getWorld().getBlockState(tile.getPos());
            if (state != null && state.getBlock() == UCBlocks.WEATHERFLESIA.get()) {
                ItemStack stack = tile.getBrush();
                if (stack.isEmpty()) return;

                double time = UCTickHandler.ticksInGame + UCTickHandler.partialTicks;
                int multiplier = stack.getMaxDamage() - stack.getDamage();
                float scale = (multiplier / 300.0F) * 0.5F;
                double wave = Math.sin(time * 0.2) / 32F;
                float f = (float)(Math.max(Math.sin(time * 0.025F), 0.5));
                float f1 = 0.0F;

                if (f > 0.8F)
                    f1 = (f - 0.8F) / 0.2F;

                ms.push();
                RenderSystem.disableDepthTest();
                ms.translate(0.5D, 0.85 + wave, 0.5D);
                renderItem(stack, ms, light, overlay, buffer);
                RenderSystem.enableDepthTest();
                ms.pop();

                Random random = new Random(432L);
                IVertexBuilder ivertexbuilder2 = buffer.getBuffer(RenderType.getLightning());
                ms.push();
                ms.translate(0.5D, 0.85, 0.5D);
                ms.scale(scale, scale, scale);
                ms.rotate(Vector3f.XP.rotationDegrees((float)time * 4F));
                ms.rotate(Vector3f.YP.rotationDegrees((float)time * 4F));

                for(int i = 0; (float)i < (f + f * f) / 2.0F * 60.0F; ++i) {
                    ms.rotate(Vector3f.XP.rotationDegrees(random.nextFloat() * 360.0F));
                    ms.rotate(Vector3f.YP.rotationDegrees(random.nextFloat() * 360.0F));
                    ms.rotate(Vector3f.ZP.rotationDegrees(random.nextFloat() * 360.0F));
                    ms.rotate(Vector3f.XP.rotationDegrees(random.nextFloat() * 360.0F));
                    ms.rotate(Vector3f.YP.rotationDegrees(random.nextFloat() * 360.0F));
                    ms.rotate(Vector3f.ZP.rotationDegrees(random.nextFloat() * 360.0F + f1 * 90.0F));
                    float f3 = random.nextFloat() * 20.0F + 5.0F + f1 * 10.0F;
                    float f4 = random.nextFloat() * 2.0F + 1.0F + f1 * 2.0F;
                    Matrix4f matrix4f = ms.getLast().getMatrix();
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
                ms.pop();
            }
        }
    }

    public static class Artisia extends TileEntityRenderer<TileArtisia> {

        public Artisia(TileEntityRendererDispatcher manager) {

            super(manager);
        }

        @Override
        public void render(TileArtisia tile, float partialTicks, MatrixStack ms, IRenderTypeBuffer buffer, int light, int overlay) {

            if (!tile.getItem().isEmpty()) {
                ms.push();
                ms.translate(0.5F, 1.25F, 0.5F);
                renderItem(tile.getItem(), ms, light, overlay, buffer);
                ms.pop();
            }
        }
    }

    public static class Lacusia extends TileEntityRenderer<TileLacusia> {

        public Lacusia(TileEntityRendererDispatcher manager) {

            super(manager);
        }

        @Override
        public void render(TileLacusia tile, float partialTicks, MatrixStack ms, IRenderTypeBuffer buffer, int light, int overlay) {

            BlockState state = tile.getWorld().getBlockState(tile.getPos());
            if (state.getBlock() == UCBlocks.LACUSIA_CROP.get()) {
                ItemStack stack = tile.getItem();
                if (!stack.isEmpty()) {
                    ms.push();
                    ms.translate(0.5, 0.65, 0.5);
                    renderItem(stack, ms, light, overlay, buffer);
                    ms.pop();
                }
            }
        }
    }

    public static void renderItem(ItemStack stack, MatrixStack ms, int light, int overlay, IRenderTypeBuffer buffer) {

        float toScale = (stack.getItem() instanceof BlockItem && !(stack.getItem() instanceof BlockNamedItem)) ? 0.25F : 0.4375F;
        ms.scale(toScale, toScale, toScale);
        float playerView = Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getYaw();
        ms.rotate(Vector3f.YP.rotationDegrees(180.0F - playerView));
        Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.NONE, light, overlay, ms, buffer);
    }

    private static final float field_229057_l_ = (float)(Math.sqrt(3.0D) / 2.0D);

    private static void func_229061_a_(IVertexBuilder p_229061_0_, Matrix4f p_229061_1_, int p_229061_2_) {
        p_229061_0_.pos(p_229061_1_, 0.0F, 0.0F, 0.0F).color(255, 255, 255, p_229061_2_).endVertex();
        p_229061_0_.pos(p_229061_1_, 0.0F, 0.0F, 0.0F).color(255, 255, 255, p_229061_2_).endVertex();
    }

    private static void func_229060_a_(IVertexBuilder p_229060_0_, Matrix4f p_229060_1_, float p_229060_2_, float p_229060_3_) {
        p_229060_0_.pos(p_229060_1_, -field_229057_l_ * p_229060_3_, p_229060_2_, -0.5F * p_229060_3_).color(255, 0, 255, 0).endVertex();
    }

    private static void func_229062_b_(IVertexBuilder p_229062_0_, Matrix4f p_229062_1_, float p_229062_2_, float p_229062_3_) {
        p_229062_0_.pos(p_229062_1_, field_229057_l_ * p_229062_3_, p_229062_2_, -0.5F * p_229062_3_).color(255, 0, 255, 0).endVertex();
    }

    private static void func_229063_c_(IVertexBuilder p_229063_0_, Matrix4f p_229063_1_, float p_229063_2_, float p_229063_3_) {
        p_229063_0_.pos(p_229063_1_, 0.0F, p_229063_2_, 1.0F * p_229063_3_).color(255, 0, 255, 0).endVertex();
    }
}
