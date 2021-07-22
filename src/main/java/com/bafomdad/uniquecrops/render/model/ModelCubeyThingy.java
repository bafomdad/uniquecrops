package com.bafomdad.uniquecrops.render.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ModelCubeyThingy extends Model {

    final ModelRenderer cube;

    public ModelCubeyThingy() {

        super(RenderType::getEntityCutout);
        cube = new ModelRenderer(this, 0, 0);
        cube.addBox(0F, 0F, 0F, 1, 1, 1);
        cube.setRotationPoint(0F, 0F, 0F);
        cube.setTextureSize(16, 16);
    }

    @Override
    public void render(MatrixStack ms, IVertexBuilder buffer, int light, int overlay, float red, float green, float blue, float alpha) {

        cube.render(ms, buffer, light, overlay);
    }
}
