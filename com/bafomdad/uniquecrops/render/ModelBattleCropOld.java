package com.bafomdad.uniquecrops.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBattleCropOld extends ModelBiped {
    
	public ModelRenderer crop1;
    public ModelRenderer crop2;
    public ModelRenderer face;
    public ModelRenderer leg_right;
    public ModelRenderer leg_left;
    public ModelRenderer arm_right;
    public ModelRenderer arm_left;

    public ModelBattleCropOld() {
    	
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.crop2 = new ModelRenderer(this, 0, 0);
        this.crop2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.crop2.addBox(-8.0F, -8.0F, 0.0F, 16, 16, 0, 0.0F);
        this.setRotateAngle(crop2, 0.0F, -0.7853981633974483F, 0.0F);
        this.arm_left = new ModelRenderer(this, 16, 16);
        this.arm_left.setRotationPoint(1.5F, 4.0F, 0.0F);
        this.arm_left.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
        this.setRotateAngle(arm_left, 0.0F, 0.0F, -0.7853981633974483F);
        this.face = new ModelRenderer(this, 0, 16);
        this.face.setRotationPoint(0.0F, 4.0F, -2.9F);
        this.face.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 0, 0.0F);
        this.leg_right = new ModelRenderer(this, 16, 16);
        this.leg_right.setRotationPoint(-1.5F, 8.0F, 0.0F);
        this.leg_right.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1, 0.0F);
        this.crop1 = new ModelRenderer(this, 0, 0);
        this.crop1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.crop1.addBox(-8.0F, -8.0F, 0.0F, 16, 16, 0, 0.0F);
        this.setRotateAngle(crop1, 0.0F, 0.7853981633974483F, 0.0F);
        this.leg_left = new ModelRenderer(this, 16, 16);
        this.leg_left.setRotationPoint(1.5F, 8.0F, 0.0F);
        this.leg_left.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1, 0.0F);
        this.arm_right = new ModelRenderer(this, 16, 16);
        this.arm_right.setRotationPoint(-1.5F, 4.0F, 0.0F);
        this.arm_right.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
        this.setRotateAngle(arm_right, 0.0F, 0.0F, 0.7853981633974483F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
       
    	this.crop2.render(f5);
        this.arm_left.render(f5);
        this.face.render(f5);
        this.leg_right.render(f5);
        this.crop1.render(f5);
        this.leg_left.render(f5);
        this.arm_right.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      
    	modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
