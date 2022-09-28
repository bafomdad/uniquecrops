package com.bafomdad.uniquecrops.network;

import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.RubiksCubeItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketColorfulCube {

    private int rotation;
    private boolean teleport;

    public PacketColorfulCube(int rotation, boolean teleport) {

        this.rotation = rotation;
        this.teleport = teleport;
    }

    public static void encode(PacketColorfulCube msg, FriendlyByteBuf buf) {

        buf.writeInt(msg.rotation);
        buf.writeBoolean(msg.teleport);
    }

    public static PacketColorfulCube decode(FriendlyByteBuf buf) {

        int rotation = buf.readInt();
        boolean teleport = buf.readBoolean();
        return new PacketColorfulCube(rotation, teleport);
    }

    public static void handle(PacketColorfulCube msg, Supplier<NetworkEvent.Context> ctx) {

        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            ((RubiksCubeItem)UCItems.RUBIKS_CUBE.get()).teleportToPosition(player, msg.rotation, msg.teleport);
        });
    }
}
