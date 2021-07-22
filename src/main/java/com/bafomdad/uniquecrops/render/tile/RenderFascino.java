package com.bafomdad.uniquecrops.render.tile;

import com.bafomdad.uniquecrops.blocks.tiles.TileFascino;
import com.bafomdad.uniquecrops.events.UCTickHandler;
import com.bafomdad.uniquecrops.render.model.ModelCubeyThingy;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.model.BookModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class RenderFascino extends TileEntityRenderer<TileFascino> {

    private static final ResourceLocation TEXTURE_BOOK = new ResourceLocation("textures/entity/enchanting_table_book.png");
    final BookModel book = new BookModel();
    final ModelCubeyThingy cube = new ModelCubeyThingy();

    public RenderFascino(TileEntityRendererDispatcher manager) {

        super(manager);
    }

    @Override
    public void render(TileFascino tile, float partialTicks, MatrixStack ms, IRenderTypeBuffer buffer, int light, int overlay) {

        ms.push();
        ms.translate(0.5, 0.1, 0.5);
        Minecraft.getInstance().textureManager.bindTexture(TEXTURE_BOOK);

        double time = UCTickHandler.ticksInGame + UCTickHandler.partialTicks;
        int cubes = 2;
        float cubeOffset = 360 / cubes;
        float modifier = 6F;
        float rotationMod = 0.2F;
        float radiusBase = 0.35F;
        float radiusMod = 0.05F;

        double wave = Math.sin(time * 0.2) / 32F;

        ms.push();
        ms.rotate(Vector3f.YP.rotationDegrees((float)time * 4F));
        ms.translate(-0.25, wave + 0.65, 0);

        this.book.setBookState(0, 0, 0, 0.005F);
        this.book.render(ms, buffer.getBuffer(book.getRenderType(TEXTURE_BOOK)), light, overlay, 1F, 1F, 1F, 1F);
        ms.pop();

        if (tile != null) {
            ms.push();
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
                float playerView = Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getYaw();
                ms.rotate(Vector3f.YP.rotationDegrees((180F - playerView) + 89F));
                ms.rotate(Vector3f.XP.rotationDegrees(90F));
                ms.rotate(Vector3f.XP.rotationDegrees(90.0F / items));
                ms.rotate(Vector3f.ZP.rotationDegrees(90F));
                for (int l = 0; l < items; l++) {
                    angles[l] = totalAngle += offsetPerItem;
                    ms.push();
                    ms.rotate(Vector3f.YP.rotationDegrees(angles[l]));
                    ms.translate(0.75 + wave, 0, 0);
                    ms.rotate(Vector3f.YP.rotationDegrees(-angles[l]));
                    ms.rotate(Vector3f.YP.rotationDegrees((90.0F / items) + 90.0F));
                    ItemStack stack = tile.getInventory().getStackInSlot(l);
                    ms.rotate(Vector3f.ZP.rotationDegrees(-90F));
                    ms.push();
                    ms.rotate(Vector3f.YP.rotationDegrees(90F));
                    Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.GROUND, light, overlay, ms, buffer);
                    ms.pop();
                    ms.pop();
                }
            }
            ms.pop();
        }
        Minecraft.getInstance().textureManager.bindTexture(TEXTURE_BOOK);
        for (int i = 0; i < 8; i++) {
            ms.rotate(Vector3f.YP.rotationDegrees((float)time * 4F));
            for (int m = 0; m < cubes; m++) {
                float offset = cubeOffset * m;
                float deg = (int)(time / rotationMod % 360F + offset);
                float rad = deg * (float)Math.PI / 180.0F;
                float radiusX = (float)(radiusBase + radiusMod * Math.sin(time / modifier));
                float radiusZ = (float)(radiusBase + radiusMod * Math.cos(time / modifier));
                float x1 = (float)(radiusX * Math.cos(rad));
                float z1 = (float)(radiusZ * Math.sin(rad));
                ms.push();
                ms.translate(x1, i * 0.1, z1);
                this.cube.render(ms, buffer.getBuffer(book.getRenderType(TEXTURE_BOOK)), light, overlay, 1F, 1F, 1F, 1F);
                ms.pop();
            }
        }
        ms.pop();
    }
}
