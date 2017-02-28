package com.bafomdad.uniquecrops.core;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class UCDyePlantStitch extends TextureAtlasSprite {

	public UCDyePlantStitch(String spriteName) {
		
		super(spriteName);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateAnimation() {
		
		World world = Minecraft.getMinecraft().theWorld;
		if (world != null) {
			long time = world.getWorldTime() % 24000L;
			this.frameCounter = (int)(time / 1500);
		}
		TextureUtil.uploadTextureMipmap((int[][])this.framesTextureData.get(this.frameCounter), this.width, this.height, this.originX, this.originY, false, false);
	}
	
	@Override
	public boolean hasAnimationMetadata() {
		
		return true;
	}
}
