package com.bafomdad.uniquecrops.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelCubeyThingy extends ModelBase {

	final ModelRenderer cube;
	
	public ModelCubeyThingy() {
		
		cube = new ModelRenderer(this, 0, 0);
		cube.addBox(0F, 0F, 0F, 1, 1, 1);
		cube.setRotationPoint(0F, 0F, 0F);
		cube.setTextureSize(16, 16);
	}
	
	public void renderCube() {
		
		cube.render(1F / 16F);
	}
}
