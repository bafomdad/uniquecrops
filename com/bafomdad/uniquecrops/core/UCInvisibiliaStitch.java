package com.bafomdad.uniquecrops.core;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.bafomdad.uniquecrops.init.UCItems;

public class UCInvisibiliaStitch extends TextureAtlasSprite {

	public UCInvisibiliaStitch(String str) {
		
		super(str);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateAnimation() {
		
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		if (p != null) {
			if ((!p.inventory.armorInventory.get(3).isEmpty() && p.inventory.armorInventory.get(3).getItem() == UCItems.glasses3D) || p.capabilities.isCreativeMode)
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
