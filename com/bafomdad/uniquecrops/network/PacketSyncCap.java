package com.bafomdad.uniquecrops.network;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.capabilities.CPCapability;
import com.bafomdad.uniquecrops.capabilities.CPProvider;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncCap implements IMessage {
	
	private NBTTagCompound tag = new NBTTagCompound();
	
	public PacketSyncCap() {}
	
	public PacketSyncCap(NBTTagCompound tag) {
		
		this.tag = tag;
	}

	@Override
	public void fromBytes(ByteBuf buf) {

		tag = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {

		ByteBufUtils.writeTag(buf, tag);
	}
	
	public static class Handler implements IMessageHandler<PacketSyncCap, IMessage> {
		
		@Override
		public IMessage onMessage(final PacketSyncCap message, final MessageContext ctx) {
			
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(new Runnable() {
				
				@Override
				public void run() {
					
					handle(message, ctx);
				}
			});
			return null;
		}
		
		private void handle(PacketSyncCap message, MessageContext ctx) {
			
			if (ctx.side.isClient()) {
				EntityPlayer player = UniqueCrops.proxy.getPlayer();
				CPCapability cap = player.getHeldItemMainhand().getCapability(CPProvider.CROP_POWER, null);
				if (cap != null)
					cap.deserializeNBT(message.tag);
			}
		}
	}
}
