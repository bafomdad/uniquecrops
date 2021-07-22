package com.bafomdad.uniquecrops.render.tile;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.tiles.TileSundial;
import com.bafomdad.uniquecrops.render.model.ModelSundial;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class RenderSundial extends TileEntityRenderer<TileSundial> {

    final ModelSundial model;
    final ResourceLocation RES = new ResourceLocation(UniqueCrops.MOD_ID, "textures/block/sundial.png");

    public RenderSundial(TileEntityRendererDispatcher manager) {

        super(manager);
        this.model = new ModelSundial();
    }

    @Override
    public void render(TileSundial te, float partialTicks, MatrixStack ms, IRenderTypeBuffer buff, int light, int overlay) {

        ms.push();
        ms.translate(0.5F, 0.1F, 0.5F);
        ms.rotate(Vector3f.XP.rotationDegrees(180.0F));
        Minecraft.getInstance().textureManager.bindTexture(RES);

        model.DialRedstone.showModel = te.savedTime < 0;
        model.DialRedstone.rotateAngleY = te.savedRotation;
        model.Dial.rotateAngleY = te.rotation;

        IVertexBuilder buffer = buff.getBuffer(model.getRenderType(RES));
        model.render(ms, buffer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
        ms.pop();
    }
}
