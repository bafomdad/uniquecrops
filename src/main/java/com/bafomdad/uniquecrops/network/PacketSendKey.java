package com.bafomdad.uniquecrops.network;

import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSendKey {

    public PacketSendKey() {}

    public static void encode(PacketSendKey msg, FriendlyByteBuf buf) {

    }

    public static PacketSendKey decode(FriendlyByteBuf buf) {

        return new PacketSendKey();
    }

    public static void handle(PacketSendKey msg, Supplier<NetworkEvent.Context> ctx) {

        ctx.get().enqueueWork(() -> {

            Player player = ctx.get().getSender();
            ItemStack glasses = player.getInventory().armor.get(3);
            if (glasses.getItem() == UCItems.GLASSES_PIXELS.get())
                NBTUtils.setBoolean(glasses, "isActive", !NBTUtils.getBoolean(glasses, "isActive", false));
        });
        ctx.get().setPacketHandled(true);
    }
}
