package com.bafomdad.uniquecrops.render.model;

import com.bafomdad.uniquecrops.entities.BattleCropEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.vector.Vector3d;

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

        this.texWidth = 32;
        this.texHeight = 32;
        this.bipedHead = new ModelRenderer(this, 0, 16);
        this.bipedHead.setPos(0.0F, 14.0F, -2.9F);
        this.bipedHead.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 0, 0.0F);
        this.bipedLeftLeg = new ModelRenderer(this, 16, 16);
        this.bipedLeftLeg.setPos(1.5F, 18.0F, 0.0F);
        this.bipedLeftLeg.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1, 0.0F);
        this.crop2 = new ModelRenderer(this, 0, 0);
        this.crop2.setPos(0.0F, 10.0F, 0.0F);
        this.crop2.addBox(-8.0F, -8.0F, 0.0F, 16, 16, 0, 0.0F);
        this.setRotateAngle(crop2, 0.0F, -0.7853981633974483F, 0.0F);
        this.bipedLeftArm = new ModelRenderer(this, 16, 16);
        this.bipedLeftArm.setPos(1.5F, 14.0F, 0.0F);
        this.bipedLeftArm.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
        this.setRotateAngle(bipedLeftArm, 0.0F, 0.0F, -0.7853981633974483F);
        this.bipedRightArm = new ModelRenderer(this, 16, 16);
        this.bipedRightArm.setPos(-1.5F, 14.0F, 0.0F);
        this.bipedRightArm.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
        this.setRotateAngle(bipedRightArm, 0.0F, 0.0F, 0.7853981633974483F);
        this.bipedRightLeg = new ModelRenderer(this, 16, 16);
        this.bipedRightLeg.setPos(-1.5F, 18.0F, 0.0F);
        this.bipedRightLeg.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1, 0.0F);
        this.crop1 = new ModelRenderer(this, 0, 0);
        this.crop1.setPos(0.0F, 10.0F, 0.0F);
        this.crop1.addBox(-8.0F, -8.0F, 0.0F, 16, 16, 0, 0.0F);
        this.setRotateAngle(crop1, 0.0F, 0.7853981633974483F, 0.0F);
    }

    @Override
    public void renderToBuffer(MatrixStack ms, IVertexBuilder buffer, int light, int overlay, float red, float green, float blue, float alpha) {

        this.bipedHead.render(ms, buffer, light, overlay);
        this.bipedLeftLeg.render(ms, buffer, light, overlay);
        this.crop2.render(ms, buffer, light, overlay);
        this.bipedLeftArm.render(ms, buffer, light, overlay);
        this.bipedRightArm.render(ms, buffer, light, overlay);
        this.bipedRightLeg.render(ms, buffer, light, overlay);
        this.crop1.render(ms, buffer, light, overlay);
    }

    @Override
    public void setupAnim(BattleCropEntity entity, float limbSwing, float limbSwingAmount, float age, float yawn, float pitch) {

        float speed = (float)((new Vector3d(entity.getDeltaMovement().x, 0, entity.getDeltaMovement().z)).length() * 0.25F);
        float rad = (float)Math.sin(Math.toRadians(age % 360) * 24F);

        this.bipedLeftLeg.xRot = speed * 60F * rad;
        this.bipedLeftArm.xRot = speed * 60F * rad;
        this.bipedRightLeg.xRot = speed * 60F * rad;
        this.bipedRightArm.xRot = speed * 60F * rad;
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {

        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
