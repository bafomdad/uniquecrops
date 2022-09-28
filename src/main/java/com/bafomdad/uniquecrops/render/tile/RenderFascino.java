package com.bafomdad.uniquecrops.render.tile;

import com.bafomdad.uniquecrops.blocks.tiles.TileFascino;
import com.bafomdad.uniquecrops.events.UCTickHandler;
import com.bafomdad.uniquecrops.render.model.ModelCubeyThingy;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;

public class RenderFascino implements BlockEntityRenderer<TileFascino> {

    private static final ResourceLocation RES = new ResourceLocation("textures/entity/enchanting_table_book.png");
    final BookModel book;
    final ModelCubeyThingy cube;
    private final BlockRenderDispatcher renderDispatcher;

    public RenderFascino(BlockEntityRendererProvider.Context ctx) {

        cube = new ModelCubeyThingy(ctx.bakeLayer(ModelCubeyThingy.LAYER_LOCATION));
        this.book = new BookModel(ctx.bakeLayer(ModelLayers.BOOK));
        this.renderDispatcher = ctx.getBlockRenderDispatcher();
    }

    @Override
    public void render(TileFascino tile, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {

        ms.pushPose();
        ms.translate(0.5, 0.1, 0.5);
        RenderSystem.setShaderTexture(0, RES);

        double time = UCTickHandler.ticksInGame + UCTickHandler.partialTicks;
        int cubes = 2;
        float cubeOffset = 360 / cubes;
        float modifier = 6F;
        float rotationMod = 0.2F;
        float radiusBase = 0.35F;
        float radiusMod = 0.05F;

        double wave = Math.sin(time * 0.2) / 32F;

        ms.pushPose();
        ms.mulPose(Vector3f.YP.rotationDegrees((float)time * 4F));
        ms.translate(-0.25, wave + 0.65, 0);

        this.book.setupAnim(0, 0, 0, 0.005F);
        this.book.render(ms, buffer.getBuffer(book.renderType(RES)), 0xF000F0, overlay, 1F, 1F, 1F, 1F);
        ms.popPose();

        if (tile != null) {
            ms.pushPose();
            int items = 0;
            for (int i = 0; i < tile.getInventory().getSlots(); i++) {
                ItemStack stack = tile.getInventory().getStackInSlot(i);
                if (!stack.isEmpty())
                    items++;
                else break;
            }
            if (items > 0) {
                float offsetPerItem = 180F / items;
                float totalAngle = 0F;
                float[] angles = new float[tile.getInventory().getSlots()];
                ms.translate(0, 0.75, 0);
                float playerView = Minecraft.getInstance().gameRenderer.getMainCamera().getYRot();
                ms.mulPose(Vector3f.YP.rotationDegrees((180F - playerView) + 89F));
                ms.mulPose(Vector3f.XP.rotationDegrees(90F));
                ms.mulPose(Vector3f.XP.rotationDegrees(90.0F / items));
                ms.mulPose(Vector3f.ZP.rotationDegrees(90F));
                for (int l = 0; l < items; l++) {
                    angles[l] = totalAngle += offsetPerItem;
                    ms.pushPose();
                    ms.mulPose(Vector3f.YP.rotationDegrees(angles[l]));
                    ms.translate(0.75 + wave, 0, 0);
                    ms.mulPose(Vector3f.YP.rotationDegrees(-angles[l]));
                    ms.mulPose(Vector3f.YP.rotationDegrees((90.0F / items) + 90.0F));
                    ItemStack stack = tile.getInventory().getStackInSlot(l);
                    ms.mulPose(Vector3f.ZP.rotationDegrees(-90F));
                    ms.pushPose();
                    ms.mulPose(Vector3f.YP.rotationDegrees(90F));
                    Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.GROUND, 0xF000F0, overlay, ms, buffer, 0);
                    ms.popPose();
                    ms.popPose();
                }
            }
            ms.popPose();
        }
        RenderSystem.setShaderTexture(0, RES);
        for (int i = 0; i < 8; i++) {
            ms.mulPose(Vector3f.YP.rotationDegrees((float)time * 4F));
            for (int m = 0; m < cubes; m++) {
                float offset = cubeOffset * m;
                float deg = (int)(time / rotationMod % 360F + offset);
                float rad = deg * (float)Math.PI / 180.0F;
                float radiusX = (float)(radiusBase + radiusMod * Math.sin(time / modifier));
                float radiusZ = (float)(radiusBase + radiusMod * Math.cos(time / modifier));
                float x1 = (float)(radiusX * Math.cos(rad));
                float z1 = (float)(radiusZ * Math.sin(rad));
                ms.pushPose();
                ms.translate(x1, i * 0.1, z1);
                this.cube.renderToBuffer(ms, buffer.getBuffer(book.renderType(RES)), 0xF000F0, overlay, 1F, 1F, 1F, 1F);
                ms.popPose();
            }
        }
        ms.popPose();
    }
}
