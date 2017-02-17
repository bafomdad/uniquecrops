package com.bafomdad.uniquecrops.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUCEffect implements IMessage {
	
	EnumParticleTypes type;
	private double x;
	private double y;
	private double z;
	private int loopSize;
	
	public PacketUCEffect() {}
	
	public PacketUCEffect(EnumParticleTypes type, double x, double y, double z, int loopSize) {
	
		this.type = type;
		this.x = x;
		this.y = y;
		this.z = z;
		this.loopSize = loopSize;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
	
		type = EnumParticleTypes.values()[buf.readShort()];
		x = buf.readDouble();
		y = buf.readDouble();
		z = buf.readDouble();
		loopSize = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
		buf.writeShort(type.ordinal());
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
		buf.writeInt(loopSize);
	}

	public static class Handler implements IMessageHandler<PacketUCEffect, IMessage> {

		@Override
		public IMessage onMessage(PacketUCEffect message, MessageContext ctx) {

			World world = Minecraft.getMinecraft().theWorld;
			double x = message.x + 0.5D;
			double y = message.y;
			double z = message.z + 0.5D;
			
			if (message.loopSize > 0) {
				for (int i = 0; i < message.loopSize; i++)
					world.spawnParticle(message.type, x + world.rand.nextFloat(), y, z + world.rand.nextFloat(), 0, 0, 0);
			}
			else
				world.spawnParticle(message.type, x, y, z, 0, 0, 0);
			
			return null;
		}
	}
}
