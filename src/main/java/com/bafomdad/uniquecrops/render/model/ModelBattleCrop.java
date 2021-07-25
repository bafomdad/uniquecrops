package com.bafomdad.uniquecrops.render.model;

import com.bafomdad.uniquecrops.entities.BattleCropEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sun.javafx.geom.Vec3d;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ModelBattleCrop extends BipedModel<BattleCropEntity> {

    public ModelRenderer crop1;
    public ModelRenderer crop2;
    public ModelRenderer bipedHead;
    public ModelRenderer bipedRightLeg;
    public ModelRenderer bipedLeftLeg;
    public ModelRenderer bipedRightArm;
    public ModelRenderer bipedLeftArm;

    public ModelBattleCrop() {

        super(1.0F);

        this.textureWidth = 32;
        this.textureHeight = 32;
        this.bipedHead = new ModelRenderer(this, 0, 16);
        this.bipedHead.setRotationPoint(0.0F, 14.0F, -2.9F);
        this.bipedHead.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 0, 0.0F);
        this.bipedLeftLeg = new ModelRenderer(this, 16, 16);
        this.bipedLeftLeg.setRotationPoint(1.5F, 18.0F, 0.0F);
        this.bipedLeftLeg.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1, 0.0F);
        this.crop2 = new ModelRenderer(this, 0, 0);
        this.crop2.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.crop2.addBox(-8.0F, -8.0F, 0.0F, 16, 16, 0, 0.0F);
        this.setRotateAngle(crop2, 0.0F, -0.7853981633974483F, 0.0F);
        this.bipedLeftArm = new ModelRenderer(this, 16, 16);
        this.bipedLeftArm.setRotationPoint(1.5F, 14.0F, 0.0F);
        this.bipedLeftArm.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
        this.setRotateAngle(bipedLeftArm, 0.0F, 0.0F, -0.7853981633974483F);
        this.bipedRightArm = new ModelRenderer(this, 16, 16);
        this.bipedRightArm.setRotationPoint(-1.5F, 14.0F, 0.0F);
        this.bipedRightArm.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
        this.setRotateAngle(bipedRightArm, 0.0F, 0.0F, 0.7853981633974483F);
        this.bipedRightLeg = new ModelRenderer(this, 16, 16);
        this.bipedRightLeg.setRotationPoint(-1.5F, 18.0F, 0.0F);
        this.bipedRightLeg.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1, 0.0F);
        this.crop1 = new ModelRenderer(this, 0, 0);
        this.crop1.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.crop1.addBox(-8.0F, -8.0F, 0.0F, 16, 16, 0, 0.0F);
        this.setRotateAngle(crop1, 0.0F, 0.7853981633974483F, 0.0F);
    }

    @Override
    public void render(MatrixStack ms, IVertexBuilder buffer, int light, int overlay, float red, float green, float blue, float alpha) {

        this.bipedHead.render(ms, buffer, light, overlay);
        this.bipedLeftLeg.render(ms, buffer, light, overlay);
        this.crop2.render(ms, buffer, light, overlay);
        this.bipedLeftArm.render(ms, buffer, light, overlay);
        this.bipedRightArm.render(ms, buffer, light, overlay);
        this.bipedRightLeg.render(ms, buffer, light, overlay);
        this.crop1.render(ms, buffer, light, overlay);
    }

    @Override
    public void setRotationAngles(BattleCropEntity entity, float limbSwing, float limbSwingAmount, float age, float yawn, float pitch) {

        float speed = (float)((new Vec3d(entity.getMotion().x, 0, entity.getMotion().z)).length() * 0.25F);
        float rad = (float)Math.sin(Math.toRadians(age % 360) * 24F);

        this.bipedLeftLeg.rotateAngleX = speed * 60F * rad;
        this.bipedLeftArm.rotateAngleX = speed * 60F * rad;
        this.bipedRightLeg.rotateAngleX = speed * 60F * rad;
        this.bipedRightArm.rotateAngleX = speed * 60F * rad;
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {

        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}