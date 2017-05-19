package com.bafomdad.uniquecrops.init;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class UCKeys {

	public static KeyBinding pixelKey;
	
	public static void init() {
		
		pixelKey = new KeyBinding("key.uniquecrops", Keyboard.KEY_V, "key.categories.miscellaneous");
		ClientRegistry.registerKeyBinding(pixelKey);
	}
}
