package com.bafomdad.uniquecrops.network;

import com.bafomdad.uniquecrops.UniqueCrops;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketOpenCube {

    final int id;

    public PacketOpenCube(int id) {

        this.id = id;
    }

    public static void encode(PacketOpenCube packet, PacketBuffer buf) {

        buf.writeInt(packet.id);
    }

    public static PacketOpenCube decode(PacketBuffer buf) {

        int id = buf.readInt();
        return new PacketOpenCube(id);
    }

    public static void handle(PacketOpenCube packet, Supplier<NetworkEvent.Context> ctx) {

        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(() -> {
                PlayerEntity player = UniqueCrops.proxy.getPlayer();
                Entity entity = player.world.getEntityByID(packet.id);
                if (entity instanceof PlayerEntity && packet.id == player.getEntityId()) {
                    UniqueCrops.proxy.openCube();
                }
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
