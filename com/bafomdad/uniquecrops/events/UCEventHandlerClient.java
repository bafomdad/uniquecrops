package com.bafomdad.uniquecrops.events;

import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import com.bafomdad.uniquecrops.core.UCDyePlantStitch;
import com.bafomdad.uniquecrops.core.UCInvisibiliaStitch;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.init.UCKeys;
import com.bafomdad.uniquecrops.network.PacketSendKey;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

public class UCEventHandlerClient {
	
	private static final String[] FIELD = new String[] { "mapRegisteredSprites", "field_110574_e", "bwd" };
	
	@SubscribeEvent
	public void loadTextures(TextureStitchEvent.Pre event) {
		
		try {
			Map<String, TextureAtlasSprite> mapRegisteredSprites = ReflectionHelper.getPrivateValue(TextureMap.class, event.getMap(), FIELD);
			for (int i = 1; i < 6; i++)
				mapRegisteredSprites.put("uniquecrops:blocks/invisibilia" + i, new UCInvisibiliaStitch("uniquecrops:blocks/invisibilia" + i));
			mapRegisteredSprites.put("uniquecrops:blocks/dyeplant5", new UCDyePlantStitch("uniquecrops:blocks/dyeplant5"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SubscribeEvent
	public void handleKeyPressed(InputEvent.KeyInputEvent event) {
		
		if (UCKeys.pixelKey.isPressed()) {
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			if (player instanceof FakePlayer) return;
			if (player.inventory.armorItemInSlot(3) == null) return;
			if (player.inventory.armorItemInSlot(3).getItem() != UCItems.pixelglasses) return;
			
			UCPacketHandler.INSTANCE.sendToServer(new PacketSendKey());
			return;
		}
	}
}
