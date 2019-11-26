package com.bafomdad.uniquecrops.render.model;

import java.util.Random;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;

import com.bafomdad.uniquecrops.blocks.tiles.TileExedo;

public class ModelExedo extends ModelBase {
	
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
    public ModelRenderer jaw1;
    public ModelRenderer jaw2;
    public ModelRenderer tooth1;
    public ModelRenderer tooth2;
    public ModelRenderer tooth3;
    public ModelRenderer tooth4;
    public ModelRenderer tooth5;
    public ModelRenderer tooth6;
    public ModelRenderer tooth7;
    public ModelRenderer tooth8;
    public ModelRenderer tooth9;
    public ModelRenderer tooth10;
    public ModelRenderer tooth11;
    public ModelRenderer tooth1_1;
    public ModelRenderer tooth2_1;
    public ModelRenderer tooth3_1;
    public ModelRenderer tooth4_1;
    public ModelRenderer tooth5_1;
    public ModelRenderer tooth6_1;
    public ModelRenderer tooth7_1;
    public ModelRenderer tooth8_1;
    public ModelRenderer tooth9_1;
    public ModelRenderer tooth10_1;
    public ModelRenderer tooth11_1;

    public ModelExedo() {
    	
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.petal3 = new ModelRenderer(this, 0, 0);
        this.petal3.setRotationPoint(0.5F, 16.0F, -0.5F);
        this.petal3.addBox(-8.0F, 0.0F, 0.0F, 16, 3, 16, 0.0F);
        this.setRotateAngle(petal3, 0.7853981633974483F, 2.356194490192345F, 0.0F);
        this.tooth7_1 = new ModelRenderer(this, 0, 0);
        this.tooth7_1.setRotationPoint(5.0F, 12.0F, 7.0F);
        this.tooth7_1.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.tooth6 = new ModelRenderer(this, 0, 0);
        this.tooth6.setRotationPoint(5.0F, 12.0F, 10.0F);
        this.tooth6.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.tooth9_1 = new ModelRenderer(this, 0, 0);
        this.tooth9_1.setRotationPoint(-7.0F, 12.0F, 10.0F);
        this.tooth9_1.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.tooth5 = new ModelRenderer(this, 0, 0);
        this.tooth5.setRotationPoint(-7.0F, 12.0F, 13.0F);
        this.tooth5.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.petal2 = new ModelRenderer(this, 0, 0);
        this.petal2.setRotationPoint(-0.5F, 16.0F, 0.5F);
        this.petal2.addBox(-8.0F, 0.0F, 0.0F, 16, 3, 16, 0.0F);
        this.setRotateAngle(petal2, 0.7853981633974483F, -0.7853981633974483F, 0.0F);
        this.tooth9 = new ModelRenderer(this, 0, 0);
        this.tooth9.setRotationPoint(-7.0F, 12.0F, 10.0F);
        this.tooth9.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.petal1 = new ModelRenderer(this, 0, 0);
        this.petal1.setRotationPoint(-0.5F, 16.0F, -0.5F);
        this.petal1.addBox(-8.0F, 0.0F, 0.0F, 16, 3, 16, 0.0F);
        this.setRotateAngle(petal1, 0.7853981633974483F, -2.356194490192345F, 0.0F);
        this.tooth2 = new ModelRenderer(this, 0, 0);
        this.tooth2.setRotationPoint(2.0F, 12.0F, 13.0F);
        this.tooth2.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.tooth1 = new ModelRenderer(this, 0, 0);
        this.tooth1.setRotationPoint(5.0F, 12.0F, 13.0F);
        this.tooth1.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.leaf2 = new ModelRenderer(this, 16, 6);
        this.leaf2.setRotationPoint(-1.2F, 22.0F, -1.2F);
        this.leaf2.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3, 0.0F);
        this.setRotateAngle(leaf2, 0.7853981633974483F, 3.141592653589793F, 0.7853981633974483F);
        this.tooth4_1 = new ModelRenderer(this, 0, 0);
        this.tooth4_1.setRotationPoint(-4.0F, 12.0F, 13.0F);
        this.tooth4_1.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.tooth10_1 = new ModelRenderer(this, 0, 0);
        this.tooth10_1.setRotationPoint(-7.0F, 12.0F, 7.0F);
        this.tooth10_1.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.tooth6_1 = new ModelRenderer(this, 0, 0);
        this.tooth6_1.setRotationPoint(5.0F, 12.0F, 10.0F);
        this.tooth6_1.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.stalk2 = new ModelRenderer(this, 16, 4);
        this.stalk2.setRotationPoint(0.0F, 18.0F, 0.0F);
        this.stalk2.addBox(-1.5F, 0.0F, -1.5F, 3, 2, 3, 0.0F);
        this.tooth7 = new ModelRenderer(this, 0, 0);
        this.tooth7.setRotationPoint(5.0F, 12.0F, 7.0F);
        this.tooth7.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.tooth2_1 = new ModelRenderer(this, 0, 0);
        this.tooth2_1.setRotationPoint(2.0F, 12.0F, 13.0F);
        this.tooth2_1.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.tooth8_1 = new ModelRenderer(this, 0, 0);
        this.tooth8_1.setRotationPoint(5.0F, 12.0F, 4.0F);
        this.tooth8_1.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.tooth10 = new ModelRenderer(this, 0, 0);
        this.tooth10.setRotationPoint(-7.0F, 12.0F, 7.0F);
        this.tooth10.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.tooth11 = new ModelRenderer(this, 0, 0);
        this.tooth11.setRotationPoint(-7.0F, 12.0F, 4.0F);
        this.tooth11.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.tooth11_1 = new ModelRenderer(this, 0, 0);
        this.tooth11_1.setRotationPoint(-7.0F, 12.0F, 4.0F);
        this.tooth11_1.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.tooth1_1 = new ModelRenderer(this, 0, 0);
        this.tooth1_1.setRotationPoint(5.0F, 12.0F, 13.0F);
        this.tooth1_1.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.jaw2 = new ModelRenderer(this, 0, 32);
        this.jaw2.setRotationPoint(0.0F, 36.0F, 0.0F);
        this.jaw2.addBox(-8.0F, 0.0F, 0.0F, 16, 12, 16, 0.0F);
        this.setRotateAngle(jaw2, 3.141592653589793F, 0.0F, 0.0F);
        this.tooth4 = new ModelRenderer(this, 0, 0);
        this.tooth4.setRotationPoint(-4.0F, 12.0F, 13.0F);
        this.tooth4.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.tooth3_1 = new ModelRenderer(this, 0, 0);
        this.tooth3_1.setRotationPoint(-1.0F, 12.0F, 13.0F);
        this.tooth3_1.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
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
        this.tooth8 = new ModelRenderer(this, 0, 0);
        this.tooth8.setRotationPoint(5.0F, 12.0F, 4.0F);
        this.tooth8.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.jaw1 = new ModelRenderer(this, 0, 32);
        this.jaw1.setRotationPoint(0.0F, 36.0F, 0.0F);
        this.jaw1.addBox(-8.0F, 0.0F, 0.0F, 16, 12, 16, 0.0F);
        this.setRotateAngle(jaw1, 3.141592653589793F, -3.141592653589793F, 0.0F);
        this.tooth3 = new ModelRenderer(this, 0, 0);
        this.tooth3.setRotationPoint(-1.0F, 12.0F, 13.0F);
        this.tooth3.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.petal4 = new ModelRenderer(this, 0, 0);
        this.petal4.setRotationPoint(0.5F, 16.0F, 0.5F);
        this.petal4.addBox(-8.0F, 0.0F, 0.0F, 16, 3, 16, 0.0F);
        this.setRotateAngle(petal4, 0.7853981633974483F, 0.7853981633974483F, 0.0F);
        this.tooth5_1 = new ModelRenderer(this, 0, 0);
        this.tooth5_1.setRotationPoint(-7.0F, 12.0F, 13.0F);
        this.tooth5_1.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.jaw2.addChild(this.tooth7_1);
        this.jaw1.addChild(this.tooth6);
        this.jaw2.addChild(this.tooth9_1);
        this.jaw1.addChild(this.tooth5);
        this.jaw1.addChild(this.tooth9);
        this.jaw1.addChild(this.tooth2);
        this.jaw1.addChild(this.tooth1);
        this.jaw2.addChild(this.tooth4_1);
        this.jaw2.addChild(this.tooth10_1);
        this.jaw2.addChild(this.tooth6_1);
        this.jaw1.addChild(this.tooth7);
        this.jaw2.addChild(this.tooth2_1);
        this.jaw2.addChild(this.tooth8_1);
        this.jaw1.addChild(this.tooth10);
        this.jaw1.addChild(this.tooth11);
        this.jaw2.addChild(this.tooth11_1);
        this.jaw2.addChild(this.tooth1_1);
        this.jaw1.addChild(this.tooth4);
        this.jaw2.addChild(this.tooth3_1);
        this.jaw1.addChild(this.tooth8);
        this.jaw1.addChild(this.tooth3);
        this.jaw2.addChild(this.tooth5_1);
    }

    public void render(TileExedo te, float f5) {
    	
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.petal3.offsetX, this.petal3.offsetY, this.petal3.offsetZ);
        GlStateManager.translate(this.petal3.rotationPointX * f5, this.petal3.rotationPointY * f5, this.petal3.rotationPointZ * f5);
        GlStateManager.scale(0.3D, 0.3D, 0.3D);
        GlStateManager.translate(-this.petal3.offsetX, -this.petal3.offsetY, -this.petal3.offsetZ);
        GlStateManager.translate(-this.petal3.rotationPointX * f5, -this.petal3.rotationPointY * f5, -this.petal3.rotationPointZ * f5);
        this.petal3.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.petal2.offsetX, this.petal2.offsetY, this.petal2.offsetZ);
        GlStateManager.translate(this.petal2.rotationPointX * f5, this.petal2.rotationPointY * f5, this.petal2.rotationPointZ * f5);
        GlStateManager.scale(0.3D, 0.3D, 0.3D);
        GlStateManager.translate(-this.petal2.offsetX, -this.petal2.offsetY, -this.petal2.offsetZ);
        GlStateManager.translate(-this.petal2.rotationPointX * f5, -this.petal2.rotationPointY * f5, -this.petal2.rotationPointZ * f5);
        this.petal2.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.petal1.offsetX, this.petal1.offsetY, this.petal1.offsetZ);
        GlStateManager.translate(this.petal1.rotationPointX * f5, this.petal1.rotationPointY * f5, this.petal1.rotationPointZ * f5);
        GlStateManager.scale(0.3D, 0.3D, 0.3D);
        GlStateManager.translate(-this.petal1.offsetX, -this.petal1.offsetY, -this.petal1.offsetZ);
        GlStateManager.translate(-this.petal1.rotationPointX * f5, -this.petal1.rotationPointY * f5, -this.petal1.rotationPointZ * f5);
        this.petal1.render(f5);
        GlStateManager.popMatrix();
        this.leaf2.render(f5);
        this.stalk2.render(f5);
        this.leaf1.render(f5);
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.stamen.offsetX, this.stamen.offsetY, this.stamen.offsetZ);
        GlStateManager.translate(this.stamen.rotationPointX * f5, this.stamen.rotationPointY * f5, this.stamen.rotationPointZ * f5);
        GlStateManager.scale(0.2D, 0.2D, 0.2D);
        GlStateManager.translate(-this.stamen.offsetX, -this.stamen.offsetY, -this.stamen.offsetZ);
        GlStateManager.translate(-this.stamen.rotationPointX * f5, -this.stamen.rotationPointY * f5, -this.stamen.rotationPointZ * f5);
        this.stamen.render(f5);
        GlStateManager.popMatrix();
        this.stalk.render(f5);
        this.stalk3.render(f5);
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.petal4.offsetX, this.petal4.offsetY, this.petal4.offsetZ);
        GlStateManager.translate(this.petal4.rotationPointX * f5, this.petal4.rotationPointY * f5, this.petal4.rotationPointZ * f5);
        GlStateManager.scale(0.3D, 0.3D, 0.3D);
        GlStateManager.translate(-this.petal4.offsetX, -this.petal4.offsetY, -this.petal4.offsetZ);
        GlStateManager.translate(-this.petal4.rotationPointX * f5, -this.petal4.rotationPointY * f5, -this.petal4.rotationPointZ * f5);
        this.petal4.render(f5);
        GlStateManager.popMatrix();
        
        /* called in renderChomp instead
        this.jaw1.render(f5);
        this.jaw2.render(f5);
         */
    }
    
    public void renderWiggle(TileExedo te, float f5) {
    	
    	Random rand = te.getWorld().rand;
    	
    	// bottom part of plant
        GlStateManager.pushMatrix();
        GlStateManager.translate(Math.sin(rand.nextInt(100)) * 0.0125F, 0, Math.sin(rand.nextInt(100)) * 0.0125F);
        this.stalk.render(f5);
        this.leaf1.render(f5);
        this.leaf2.render(f5);
        GlStateManager.popMatrix();
        
        // middle part of plant
    	GlStateManager.pushMatrix();
    	GlStateManager.translate(Math.sin(rand.nextInt(100)) * 0.0125F, 0, Math.sin(rand.nextInt(100)) * 0.0125F);
        this.stalk2.render(f5);
        GlStateManager.popMatrix();
        
        // top part of plant
        GlStateManager.pushMatrix();
        GlStateManager.translate(Math.sin(rand.nextInt(100)) * 0.0125F, 0, Math.sin(rand.nextInt(100)) * 0.0125F);
        this.stalk3.render(f5);
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.petal3.offsetX, this.petal3.offsetY, this.petal3.offsetZ);
        GlStateManager.translate(this.petal3.rotationPointX * f5, this.petal3.rotationPointY * f5, this.petal3.rotationPointZ * f5);
        GlStateManager.scale(0.3D, 0.3D, 0.3D);
        GlStateManager.translate(-this.petal3.offsetX, -this.petal3.offsetY, -this.petal3.offsetZ);
        GlStateManager.translate(-this.petal3.rotationPointX * f5, -this.petal3.rotationPointY * f5, -this.petal3.rotationPointZ * f5);
        this.petal3.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.petal2.offsetX, this.petal2.offsetY, this.petal2.offsetZ);
        GlStateManager.translate(this.petal2.rotationPointX * f5, this.petal2.rotationPointY * f5, this.petal2.rotationPointZ * f5);
        GlStateManager.scale(0.3D, 0.3D, 0.3D);
        GlStateManager.translate(-this.petal2.offsetX, -this.petal2.offsetY, -this.petal2.offsetZ);
        GlStateManager.translate(-this.petal2.rotationPointX * f5, -this.petal2.rotationPointY * f5, -this.petal2.rotationPointZ * f5);
        this.petal2.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.petal1.offsetX, this.petal1.offsetY, this.petal1.offsetZ);
        GlStateManager.translate(this.petal1.rotationPointX * f5, this.petal1.rotationPointY * f5, this.petal1.rotationPointZ * f5);
        GlStateManager.scale(0.3D, 0.3D, 0.3D);
        GlStateManager.translate(-this.petal1.offsetX, -this.petal1.offsetY, -this.petal1.offsetZ);
        GlStateManager.translate(-this.petal1.rotationPointX * f5, -this.petal1.rotationPointY * f5, -this.petal1.rotationPointZ * f5);
        this.petal1.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.stamen.offsetX, this.stamen.offsetY, this.stamen.offsetZ);
        GlStateManager.translate(this.stamen.rotationPointX * f5, this.stamen.rotationPointY * f5, this.stamen.rotationPointZ * f5);
        GlStateManager.scale(0.2D, 0.2D, 0.2D);
        GlStateManager.translate(-this.stamen.offsetX, -this.stamen.offsetY, -this.stamen.offsetZ);
        GlStateManager.translate(-this.stamen.rotationPointX * f5, -this.stamen.rotationPointY * f5, -this.stamen.rotationPointZ * f5);
        this.stamen.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.petal4.offsetX, this.petal4.offsetY, this.petal4.offsetZ);
        GlStateManager.translate(this.petal4.rotationPointX * f5, this.petal4.rotationPointY * f5, this.petal4.rotationPointZ * f5);
        GlStateManager.scale(0.3D, 0.3D, 0.3D);
        GlStateManager.translate(-this.petal4.offsetX, -this.petal4.offsetY, -this.petal4.offsetZ);
        GlStateManager.translate(-this.petal4.rotationPointX * f5, -this.petal4.rotationPointY * f5, -this.petal4.rotationPointZ * f5);
        this.petal4.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
    }
    
    public void renderChomp(TileExedo te, float f5) {
    	
    	GlStateManager.pushMatrix();
    	double width = te.ent.width;
    	double height = te.ent.height;
    	double maxScale = (width > height) ? width * 2.0D : height * 2.0D;
    	
    	GlStateManager.scale(maxScale, maxScale, maxScale);
    	GlStateManager.translate(0, -0.0625, 0);

    	this.jaw1.rotateAngleX = 90 + -((float)(te.timeAfterWiggle + f5) * 0.125F) / 0.5F;
    	this.jaw2.rotateAngleX = 90 + -((float)(te.timeAfterWiggle + f5) * 0.125F) / 0.5F;
    	this.jaw1.render(f5);
    	this.jaw2.render(f5);

    	GlStateManager.popMatrix();
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
    	
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
