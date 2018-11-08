package com.bafomdad.uniquecrops.core;

import net.minecraftforge.fml.common.event.FMLInterModComms.IMCMessage;

import com.bafomdad.uniquecrops.items.baubles.EmblemScarab;
import com.google.common.collect.ImmutableList;

public class IMCHandler {

	public static void processMessages(ImmutableList<IMCMessage> messageList) {
		
		for (IMCMessage message : messageList) {
			if (message != null && message.key != null && message.key.equals(UCStrings.BLACKLIST_EFFECT) && message.isStringMessage()) {
				String value = message.getStringValue();
				EmblemScarab.addPotionEffectToBlacklist(value);
			}
		}
	}
}
