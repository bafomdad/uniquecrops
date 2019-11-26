package com.bafomdad.uniquecrops.render;

import org.lwjgl.opengl.GL11;

import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.ItemThunderpants;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class UCBipedLayerRenderer implements LayerRenderer<EntityLivingBase> {
	
	private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
	private final ModelBiped model;
	
	public UCBipedLayerRenderer() {
		
		this.model = new ModelBiped(1.0F);
	}
	
	@Override
	public void doRenderLayer(EntityLivingBase elb, float limbSwing, float limbSwingAmount, float partialTicks, float age, float yaw, float pitch, float scale) {

		if (!elb.getItemStackFromSlot(EntityEquipmentSlot.LEGS).isEmpty()) {
			ItemStack thunderpants = elb.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
			if (thunderpants.getItem() == UCItems.thunderPantz) {
				
				GlStateManager.pushMatrix();
	            boolean flag = elb.isInvisible();
	            GlStateManager.depthMask(!flag);
	            Minecraft.getMinecraft().renderEngine.bindTexture(LIGHTNING_TEXTURE);
	            GlStateManager.matrixMode(5890);
	            GlStateManager.loadIdentity();
	            float f = (float)elb.ticksExisted + partialTicks;
	            GlStateManager.translate(f * 0.01F, f * 0.01F, 0.0F);
	            GlStateManager.matrixMode(5888);
	            GlStateManager.enableBlend();
	            float f1 = 0.5F;
	            GlStateManager.color(0.5F, 0.5F, 0.5F, 1.0F);
	            float toScale = ((ItemThunderpants)thunderpants.getItem()).getCharge(thunderpants) / 16.0F;
	            GlStateManager.scale(toScale, toScale, toScale);
	            GlStateManager.disableLighting();
	            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
	            //ModelBiped model = ((ItemThunderpants)thunderpants.getItem()).getArmorModel(elb, thunderpants, EntityEquipmentSlot.LEGS, new ModelBiped(1.0F));
	            model.bipedBody.isHidden = true;
	            model.bipedHead.isHidden = true;
	            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
	            GlStateManager.translate(0.0F, -(elb.height / 2) + 0.1F, 0.0F);
	            model.render(elb, limbSwing, limbSwingAmount, age, yaw, pitch, scale);
	            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
	            GlStateManager.matrixMode(5890);
	            GlStateManager.loadIdentity();
	            GlStateManager.matrixMode(5888);
	            GlStateManager.enableLighting();
	            GlStateManager.disableBlend();
	            GlStateManager.depthMask(flag);
	            GlStateManager.popMatrix();
			}
		}
	}

	@Override
	public boolean shouldCombineTextures() {

		return false;
	}
}
