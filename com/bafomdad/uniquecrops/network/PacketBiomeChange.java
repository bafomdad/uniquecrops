package com.bafomdad.uniquecrops.network;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.UCUtils;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketBiomeChange implements IMessage {
	
	private int x, y, z;
	private short biome;
	
	public PacketBiomeChange() {}
	
	public PacketBiomeChange(int x, int y, int z, short biome) {
	
		this.x = x;
		this.y = y;
		this.z = z;
		this.biome = biome;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {

		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		this.biome = buf.readShort();
	}

	@Override
	public void toBytes(ByteBuf buf) {

		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
		buf.writeShort(this.biome);
	}

	public static class Handler implements IMessageHandler<PacketBiomeChange, IMessage> {

		@Override
		public IMessage onMessage(PacketBiomeChange message, MessageContext ctx) {

			UCUtils.setBiome(message.biome, UniqueCrops.proxy.getClientWorld(), new BlockPos(message.x, message.y, message.z));
			return null;
		}
	}
}
