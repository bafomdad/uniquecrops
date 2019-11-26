package com.bafomdad.uniquecrops.network;

import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.ItemColorfulCube;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketColorfulCube implements IMessage {
	
	private int rotation;
	private boolean teleport;
	
	public PacketColorfulCube() {}
	
	public PacketColorfulCube(int rotation, boolean teleport) {
		
		this.rotation = rotation;
		this.teleport = teleport;
	}

	@Override
	public void fromBytes(ByteBuf buf) {

		this.rotation = buf.readInt();
		this.teleport = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {

		buf.writeInt(this.rotation);
		buf.writeBoolean(this.teleport);
	}
	
	public static class Handler implements IMessageHandler<PacketColorfulCube, IMessage> {

		@Override
		public IMessage onMessage(PacketColorfulCube message, MessageContext ctx) {

			EntityPlayerMP player = ctx.getServerHandler().player;
			player.server.addScheduledTask(() -> ((ItemColorfulCube)UCItems.rubiksCube).teleportToPosition(player, message.rotation, message.teleport));
			return null;
		}
	}
}
