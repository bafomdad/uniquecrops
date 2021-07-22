package com.bafomdad.uniquecrops.network;

import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.RubiksCubeItem;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketColorfulCube {

    private int rotation;
    private boolean teleport;

    public PacketColorfulCube(int rotation, boolean teleport) {

        this.rotation = rotation;
        this.teleport = teleport;
    }

    public static void encode(PacketColorfulCube msg, PacketBuffer buf) {

        buf.writeInt(msg.rotation);
        buf.writeBoolean(msg.teleport);
    }

    public static PacketColorfulCube decode(PacketBuffer buf) {

        int rotation = buf.readInt();
        boolean teleport = buf.readBoolean();
        return new PacketColorfulCube(rotation, teleport);
    }

    public static void handle(PacketColorfulCube msg, Supplier<NetworkEvent.Context> ctx) {

        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            ((RubiksCubeItem)UCItems.RUBIKS_CUBE.get()).teleportToPosition(player, msg.rotation, msg.teleport);
        });
    }
}
