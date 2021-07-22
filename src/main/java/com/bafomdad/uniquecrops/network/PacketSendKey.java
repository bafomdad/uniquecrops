package com.bafomdad.uniquecrops.network;

import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSendKey {

    public PacketSendKey() {}

    public static void encode(PacketSendKey msg, PacketBuffer buf) {

    }

    public static PacketSendKey decode(PacketBuffer buf) {

        return new PacketSendKey();
    }

    public static void handle(PacketSendKey msg, Supplier<NetworkEvent.Context> ctx) {

        ctx.get().enqueueWork(() -> {

            PlayerEntity player = ctx.get().getSender();
            ItemStack glasses = player.inventory.armorInventory.get(3);
            if (glasses.getItem() == UCItems.GLASSES_PIXELS.get())
                NBTUtils.setBoolean(glasses, "isActive", !NBTUtils.getBoolean(glasses, "isActive", false));
        });
        ctx.get().setPacketHandled(true);
    }
}
