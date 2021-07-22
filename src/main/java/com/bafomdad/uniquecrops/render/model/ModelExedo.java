package com.bafomdad.uniquecrops.render.model;

import com.bafomdad.uniquecrops.blocks.tiles.TileExedo;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

import java.util.Random;

public class ModelExedo extends Model {

    public ModelRenderer stalk;
    public ModelRenderer stalk2;
    public ModelRenderer stalk3;
    public ModelRenderer leaf1;
    public ModelRenderer leaf2;
    public ModelRenderer stamen;
    public ModelRenderer petal1;
    public ModelRenderer petal2;
    public ModelRenderer petal3;
    public ModelRenderer petal4;

    public ModelExedo() {

        super(RenderType::getEntityCutout);
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.petal3 = new ModelRenderer(this, 0, 0);
        this.petal3.setRotationPoint(0.5F, 16.0F, -0.5F);
        this.petal3.addBox(-8.0F, 0.0F, 0.0F, 16, 3, 16, 0.0F);
        this.setRotateAngle(petal3, 0.7853981633974483F, 2.356194490192345F, 0.0F);
        this.petal2 = new ModelRenderer(this, 0, 0);
        this.petal2.setRotationPoint(-0.5F, 16.0F, 0.5F);
        this.petal2.addBox(-8.0F, 0.0F, 0.0F, 16, 3, 16, 0.0F);
        this.setRotateAngle(petal2, 0.7853981633974483F, -0.7853981633974483F, 0.0F);
        this.petal1 = new ModelRenderer(this, 0, 0);
        this.petal1.setRotationPoint(-0.5F, 16.0F, -0.5F);
        this.petal1.addBox(-8.0F, 0.0F, 0.0F, 16, 3, 16, 0.0F);
        this.setRotateAngle(petal1, 0.7853981633974483F, -2.356194490192345F, 0.0F);
        this.leaf2 = new ModelRenderer(this, 16, 6);
        this.leaf2.setRotationPoint(-1.2F, 22.0F, -1.2F);
        this.leaf2.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3, 0.0F);
        this.setRotateAngle(leaf2, 0.7853981633974483F, 3.141592653589793F, 0.7853981633974483F);
        this.stalk2 = new ModelRenderer(this, 16, 4);
        this.stalk2.setRotationPoint(0.0F, 18.0F, 0.0F);
        this.stalk2.addBox(-1.5F, 0.0F, -1.5F, 3, 2, 3, 0.0F);
        this.leaf1 = new ModelRenderer(this, 16, 6);
        this.leaf1.setRotationPoint(1.2F, 22.0F, 1.2F);
        this.leaf1.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3, 0.0F);
        this.setRotateAngle(leaf1, 0.7853981633974483F, 0.0F, -0.7853981633974483F);
        this.stamen = new ModelRenderer(this, 0, 32);
        this.stamen.setRotationPoint(0.0F, 14.5F, 0.0F);
        this.stamen.addBox(-8.0F, -4.0F, -8.0F, 16, 10, 16, 0.0F);
        this.stalk = new ModelRenderer(this, 16, 5);
        this.stalk.setRotationPoint(0.0F, 20.0F, 0.0F);
        this.stalk.addBox(-1.5F, 0.0F, -1.5F, 3, 5, 3, 0.0F);
        this.stalk3 = new ModelRenderer(this, 16, 5);
        this.stalk3.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.stalk3.addBox(-1.5F, 0.0F, -1.5F, 3, 2, 3, 0.0F);
        this.petal4 = new ModelRenderer(this, 0, 0);
        this.petal4.setRotationPoint(0.5F, 16.0F, 0.5F);
        this.petal4.addBox(-8.0F, 0.0F, 0.0F, 16, 3, 16, 0.0F);
        this.setRotateAngle(petal4, 0.7853981633974483F, 0.7853981633974483F, 0.0F);
    }

    @Override
    public void render(MatrixStack ms, IVertexBuilder buffer, int light, int overlay, float red, float green, float blue, float alpha) {

        //NO-OP...hopefully
    }

    public void renderWithWiggle(TileExedo te, MatrixStack ms, IVertexBuilder buffer, int light, int overlay) {

        Random rand = te.getWorld().rand;
        boolean flag = te.isWiggling;

        ms.push();
        ms.translate(flag ? Math.sin(rand.nextInt(100)) * 0.0125F : 0, 0, flag ? Math.sin(rand.nextInt(100)) * 0.0125F : 0);
        ms.scale(1.5F, 1.5F, 1.5F);
        this.stalk.render(ms, buffer, light, overlay);
        this.leaf1.render(ms, buffer, light, overlay);
        this.leaf2.render(ms, buffer, light, overlay);
        ms.pop();

        ms.push();
        ms.translate(flag ? Math.sin(rand.nextInt(100)) * 0.0125F : 0, 0, flag ? Math.sin(rand.nextInt(100)) * 0.0125F : 0);
        ms.scale(1.5F, 1.5F, 1.5F);
        this.stalk2.render(ms, buffer, light, overlay);
        ms.pop();

        ms.push();
        ms.translate(flag ? Math.sin(rand.nextInt(100)) * 0.0125F : 0, 0, flag ? Math.sin(rand.nextInt(100)) * 0.0125F : 0);
        ms.scale(1.5F, 1.5F, 1.5F);
        this.stalk3.render(ms, buffer, light, overlay);
        ms.pop();

        ms.push();
        ms.translate(flag ? Math.sin(rand.nextInt(100)) * 0.0125F : 0, 0.575, flag ? Math.sin(rand.nextInt(100)) * 0.0125F : 0);
        ms.scale(0.8F, 0.8F, 0.8F);
        this.petal4.render(ms, buffer, light, overlay);
        this.petal3.render(ms, buffer, light, overlay);
        this.petal2.render(ms, buffer, light, overlay);
        this.petal1.render(ms, buffer, light, overlay);
        ms.translate(0, 0.3, 0);
        ms.scale(0.5F, 0.5F, 0.5F);
        this.stamen.render(ms, buffer, light, overlay);
        ms.pop();
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {

        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
