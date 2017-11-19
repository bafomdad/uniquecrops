package com.bafomdad.uniquecrops.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketBookIndex implements IMessage {
	
	private int index;
	
	public PacketBookIndex() {}
	
	public PacketBookIndex(int index) {
		
		this.index = index;
	}

	@Override
	public void fromBytes(ByteBuf buf) {

		index = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {

		buf.writeInt(index);
	}
	
	public static class Handler implements IMessageHandler<PacketBookIndex, IMessage> {

		@Override
		public IMessage onMessage(final PacketBookIndex message, final MessageContext ctx) {

			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(new Runnable() {

				@Override
				public void run() {
					
					handle(message, ctx);
				}
				
			});
			return null;
		}
		
		public void handle(PacketBookIndex message, MessageContext ctx) {
			
			
		}
	}
}
