package com.bafomdad.uniquecrops.render.tile;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.tiles.TileExedo;
import com.bafomdad.uniquecrops.render.model.ModelExedo;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class RenderExedo extends TileEntityRenderer<TileExedo> {

    final ModelExedo model = new ModelExedo();
    static final ResourceLocation TEX = new ResourceLocation(UniqueCrops.MOD_ID, "textures/models/model_exedo.png");

    public RenderExedo(TileEntityRendererDispatcher manager) {

        super(manager);
    }

    @Override
    public void render(TileExedo te, float partialTicks, MatrixStack ms, IRenderTypeBuffer buffer, int light, int overlay) {

        float f = 0.5F;
        ms.push();
        ms.translate(0.5, 1.2, 0.5);
        ms.scale(f, f, f);
        ms.rotate(Vector3f.XP.rotationDegrees(180.0F));
        model.renderWithWiggle(te, ms, buffer.getBuffer(model.getRenderType(TEX)), light, overlay);
        ms.pop();
    }
}
