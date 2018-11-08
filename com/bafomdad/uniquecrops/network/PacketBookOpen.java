package com.bafomdad.uniquecrops.network;

import com.bafomdad.uniquecrops.UniqueCrops;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketBookOpen implements IMessage {
	
	int id;
	
	public PacketBookOpen() {}
	
	public PacketBookOpen(EntityPlayer player) {
		
		this.id = player.getEntityId();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		
		id = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
		buf.writeInt(id);
	}
	
	public static class Handler implements IMessageHandler<PacketBookOpen, IMessage> {

		@Override
		public IMessage onMessage(PacketBookOpen message, MessageContext ctx) {

			if (ctx.side.isClient()) {
				EntityPlayer player = UniqueCrops.proxy.getPlayer();
				Entity entity = player.world.getEntityByID(message.id);
				if (entity instanceof EntityPlayer && message.id == player.getEntityId()) {
					UniqueCrops.proxy.openEulaBook((EntityPlayer)entity);
				}
			}
			return null;
		}
	}
}
