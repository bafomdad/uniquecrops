package com.bafomdad.uniquecrops.events;

import java.util.Map;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import com.bafomdad.uniquecrops.core.UCTextureStitch;

public class UCEventHandlerClient {
	
	private static final String[] FIELD = new String[] { "mapRegisteredSprites", "field_110574_e", "bwd" };
	
	@SubscribeEvent
	public void loadTextures(TextureStitchEvent.Pre event) {
		
		try {
			Map<String, TextureAtlasSprite> mapRegisteredSprites = ReflectionHelper.getPrivateValue(TextureMap.class, event.getMap(), FIELD);
			for (int i = 1; i < 6; i++)
				mapRegisteredSprites.put("uniquecrops:blocks/invisibilia" + i, new UCTextureStitch("uniquecrops:blocks/invisibilia" + i));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
