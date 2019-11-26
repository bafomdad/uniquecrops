package com.bafomdad.uniquecrops.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class ModelBattleCrop extends ModelBiped {
   
	public ModelRenderer crop1;
    public ModelRenderer crop2;
    public ModelRenderer bipedHead;
    public ModelRenderer bipedRightLeg;
    public ModelRenderer bipedLeftLeg;
    public ModelRenderer bipedRightArm;
    public ModelRenderer bipedLeftArm;

    public ModelBattleCrop() {
       
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
    public void render(Entity entity, float f, float f1, float age, float f3, float f4, float f5) { 
        
    	float speed = (float)((new Vec3d(entity.motionX, 0, entity.motionZ)).length() * 0.25F);
    	
    	this.bipedHead.render(f5);
    	GlStateManager.rotate(speed*60f*(float) Math.sin(Math.toRadians(age % 360)*24F), 1, 0, 0);
        this.bipedLeftLeg.render(f5);
        this.crop2.render(f5);
        GlStateManager.rotate(speed * 60F * (float)Math.sin(Math.toRadians(age % 360) * 24F), 1, 0, 0);
        this.bipedLeftArm.render(f5);
        GlStateManager.rotate(speed * -120F * (float)Math.sin(Math.toRadians(age % 360) * 24F), 1, 0, 0);
        this.bipedRightArm.render(f5);
        GlStateManager.rotate(-120f*speed*(float) Math.sin(Math.toRadians(age % 360)*24F), 1, 0, 0);
        this.bipedRightLeg.render(f5);
        this.crop1.render(f5);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
       
    	modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
