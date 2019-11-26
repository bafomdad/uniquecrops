package com.bafomdad.uniquecrops.render.model;

import com.bafomdad.uniquecrops.blocks.tiles.TileSucco;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelFakeCrop extends ModelBase {

	ModelRenderer Shape1;
	ModelRenderer Shape2;

	public ModelFakeCrop() {
		
		textureWidth = 16;
		textureHeight = 16;

		Shape1 = new ModelRenderer(this, 0, 0);
		Shape1.addBox(-8F, -8F, 0F, 16, 16, 0);
		Shape1.setRotationPoint(0F, 0F, 0F);
		Shape1.setTextureSize(16, 16);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, -0.7853982F, 0F);
		Shape2 = new ModelRenderer(this, 0, 0);
		Shape2.addBox(-8F, -8F, 0F, 16, 16, 0);
		Shape2.setRotationPoint(0F, 0F, 0F);
		Shape2.setTextureSize(16, 16);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0.7853982F, 0F);
	}

	public void render(TileSucco te, float f5) {
		
		Shape1.render(f5);
		Shape2.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
