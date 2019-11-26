package com.bafomdad.uniquecrops.potions;

import com.bafomdad.uniquecrops.UniqueCrops;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class CustomPotion extends Potion {
	
	private ResourceLocation icon;

	public CustomPotion(String name, boolean isBadEffect, int potionColor) {
		
		super(isBadEffect, potionColor);
		this.setIcon(new ResourceLocation(UniqueCrops.MOD_ID, "textures/potions/" + name + ".png"));
		this.setPotionName("potion." + name);
	}
	
	public void setIcon(ResourceLocation res) {
		
		this.icon = res;
	}
	
	public ResourceLocation getIcon() {
		
		return icon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
		
		drawGuiTexture(getIcon(), x + 6, y + 6, 18 - 2);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
		
		if (mc.gameSettings.showDebugInfo == false)
			drawGuiTexture(getIcon(), x + 4, y + 3, 18 - 2);
	}
	
	@SideOnly(Side.CLIENT)
	public void drawGuiTexture(ResourceLocation res, int x, int y, int dim) {
		
		if (res == null) return;
		
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(res);
		Gui.drawModalRectWithCustomSizedTexture(x, y, 0F, 0F, dim, dim, dim, dim);
	}
}
