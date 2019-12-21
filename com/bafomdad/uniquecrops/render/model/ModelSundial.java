package com.bafomdad.uniquecrops.render.model;

import com.bafomdad.uniquecrops.blocks.tiles.TileSundial;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSundial extends ModelBase {

	ModelRenderer Shape1;
	ModelRenderer Shape2;
	ModelRenderer Shape3;
	ModelRenderer Shape4;
	ModelRenderer Shape5;
	ModelRenderer Shape6;
	ModelRenderer Dial;
	public ModelRenderer DialRedstone;
  
	public ModelSundial() {
	  
		textureWidth = 64;
		textureHeight = 32;

		Shape1 = new ModelRenderer(this, 0, 0);
		Shape1.addBox(-4F, 0F, -4F, 8, 2, 8);
		Shape1.setRotationPoint(0F, 0F, 0F);
		Shape1.setTextureSize(64, 32);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Shape2 = new ModelRenderer(this, 0, 0);
		Shape2.addBox(-1F, -1F, 0F, 2, 2, 8);
		Shape2.setRotationPoint(-4F, -0.5F, -4F);
		Shape2.setTextureSize(64, 32);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0F);
		Shape3 = new ModelRenderer(this, 0, 0);
		Shape3.addBox(-1F, -1.5F, 0F, 2, 2, 8);
		Shape3.setRotationPoint(4F, 0F, -4F);
		Shape3.setTextureSize(64, 32);
		Shape3.mirror = true;
		setRotation(Shape3, 0F, 0F, 0F);
		Shape4 = new ModelRenderer(this, 0, 0);
		Shape4.addBox(0F, -1F, -1F, 8, 2, 2);
		Shape4.setRotationPoint(-4F, 0F, 4F);
		Shape4.setTextureSize(64, 32);
		Shape4.mirror = true;
		setRotation(Shape4, 0F, 0F, 0F);
		Shape5 = new ModelRenderer(this, 0, 0);
		Shape5.addBox(0F, -1F, -1F, 8, 2, 2);
		Shape5.setRotationPoint(-4F, 0F, -4F);
		Shape5.setTextureSize(64, 32);
		Shape5.mirror = true;
		setRotation(Shape5, 0F, 0F, 0F);
		Shape6 = new ModelRenderer(this, 0, 0);
		Shape6.addBox(-1F, 0F, -1F, 2, 3, 2);
		Shape6.setRotationPoint(0F, -3F, 0F);
		Shape6.setTextureSize(64, 32);
		Shape6.mirror = true;
		setRotation(Shape6, 0F, 0.7853982F, 0F);
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

	public void render(TileSundial te, float f5) {

		Shape1.render(f5);
		Shape2.render(f5);
		Shape3.render(f5);
		Shape4.render(f5);
		Shape5.render(f5);
		Shape6.render(f5);
		Dial.rotateAngleY = te.rotation;
		Dial.render(f5);
		DialRedstone.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {

		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
