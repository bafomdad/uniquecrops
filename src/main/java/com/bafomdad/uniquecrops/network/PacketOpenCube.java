package com.bafomdad.uniquecrops.network;

import com.bafomdad.uniquecrops.UniqueCrops;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketOpenCube {

    final int id;

    public PacketOpenCube(int id) {

        this.id = id;
    }

    public static void encode(PacketOpenCube packet, FriendlyByteBuf buf) {

        buf.writeInt(packet.id);
    }

    public static PacketOpenCube decode(FriendlyByteBuf buf) {

        int id = buf.readInt();
        return new PacketOpenCube(id);
    }

    public static void handle(PacketOpenCube packet, Supplier<NetworkEvent.Context> ctx) {

        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(() -> {
                Player player = UniqueCrops.proxy.getPlayer();
                Entity entity = player.level.getEntity(packet.id);
                if (entity instanceof Player && packet.id == player.getId()) {
                    UniqueCrops.proxy.openCube();
                }
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
