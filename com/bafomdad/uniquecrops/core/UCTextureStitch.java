package com.bafomdad.uniquecrops.core;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class UCTextureStitch extends TextureAtlasSprite {

	public UCTextureStitch(String str) {
		
		super(str);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateAnimation() {
		
		EntityPlayerSP p = Minecraft.getMinecraft().thePlayer;
		if (p != null)
		{
			if ((p.inventory.armorInventory[3] != null && p.inventory.armorInventory[3].getItem() == UCItems.glasses3D) || p.capabilities.isCreativeMode)
				this.frameCounter = 0;
			else
				this.frameCounter = 1;
		}
		TextureUtil.uploadTextureMipmap((int[][])this.framesTextureData.get(this.frameCounter), this.width, this.height, this.originX, this.originY, false, false);
	}
	
	@Override
	public boolean hasAnimationMetadata() {
		
		return true;
	}
}
