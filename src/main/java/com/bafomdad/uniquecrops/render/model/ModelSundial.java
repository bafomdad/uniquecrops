package com.bafomdad.uniquecrops.render.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ModelSundial extends Model {

    public ModelRenderer Dial;
    public ModelRenderer DialRedstone;

    public ModelSundial() {

        super(RenderType::getEntityCutout);
        textureWidth = 64;
        textureHeight = 32;

        Dial = new ModelRenderer(this, 16, 16);
        Dial.addBox(-1F, 0F, 0F, 2, 1, 3);
        Dial.setRotationPoint(0F, -1F, 0F);
        Dial.setTextureSize(64, 32);
        Dial.mirror = true;
        setRotation(Dial, 0F, 0F, 0F);
        DialRedstone = new ModelRenderer(this, 32, 16);
        DialRedstone.addBox(-1F, 0F, 0F, 2, 1, 3);
        DialRedstone.setRotationPoint(0F, -0.5F, 0F);
        DialRedstone.setTextureSize(64, 32);
        DialRedstone.mirror = true;
        setRotation(DialRedstone, 0F, 0F, 0F);
    }

    @Override
    public void render(MatrixStack ms, IVertexBuilder buffer, int light, int overlay, float red, float green, float blue, float alpha) {

        Dial.render(ms, buffer, light, overlay);
        DialRedstone.render(ms, buffer, light, overlay);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {

        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
