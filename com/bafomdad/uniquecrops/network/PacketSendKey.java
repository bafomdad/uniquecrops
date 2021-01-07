package com.bafomdad.uniquecrops.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.init.UCItems;

public class PacketSendKey implements IMessage {
	
	public PacketSendKey() {}

	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) {}
	
	public static class Handler implements IMessageHandler<PacketSendKey, IMessage> {

		@Override
		public IMessage onMessage(final PacketSendKey message, final MessageContext ctx) {

			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(new Runnable() {

				@Override
				public void run() {
					
					handle(message, ctx);
				}
				
			});
			return null;
		}
		
		private void handle(PacketSendKey message, MessageContext ctx) {
			
			EntityPlayerMP player = ctx.getServerHandler().player;
			ItemStack glasses = player.inventory.armorInventory.get(3);
			if (!glasses.isEmpty() && glasses.getItem() == UCItems.pixelGlasses)
				NBTUtils.setBoolean(glasses, "isActive", !glasses.getTagCompound().getBoolean("isActive"));
		}
	}
}
