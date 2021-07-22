package com.bafomdad.uniquecrops.network;

import com.bafomdad.uniquecrops.UniqueCrops;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketOpenBook {

    final int id;

    public PacketOpenBook(int id) {

        this.id = id;
    }

    public static void encode(PacketOpenBook packet, PacketBuffer buf) {

        buf.writeInt(packet.id);
    }

    public static PacketOpenBook decode(PacketBuffer buf) {

        int id = buf.readInt();
        return new PacketOpenBook(id);
    }

    public static void handle(PacketOpenBook packet, Supplier<NetworkEvent.Context> ctx) {

        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(() -> {
                PlayerEntity player = UniqueCrops.proxy.getPlayer();
                Entity entity = player.world.getEntityByID(packet.id);
                if (entity instanceof PlayerEntity && packet.id == player.getEntityId()) {
                    UniqueCrops.proxy.openBook();
                }
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
