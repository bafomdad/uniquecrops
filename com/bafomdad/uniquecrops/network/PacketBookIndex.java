package com.bafomdad.uniquecrops.network;

import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.enums.EnumItems;
import com.bafomdad.uniquecrops.init.UCItems;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
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

			EntityPlayerMP player = ctx.getServerHandler().player;
			ItemStack book = player.getHeldItemMainhand();
			if (!book.isEmpty() && (book.getItem() == UCItems.bookMultiblock || (book.getItem() == UCItems.generic && book.getItemDamage() == EnumItems.GUIDE.ordinal()))) {
				NBTUtils.setInt(book, "savedIndex", message.index);
			}
			return null;
		}
	}
}
