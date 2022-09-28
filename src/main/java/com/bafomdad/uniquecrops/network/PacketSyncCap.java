package com.bafomdad.uniquecrops.network;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.capabilities.CPProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSyncCap {

    final CompoundTag tag;

    public PacketSyncCap(CompoundTag tag) {

        this.tag = tag;
    }

    public static void encode(PacketSyncCap msg, FriendlyByteBuf buf) {

        buf.writeNbt(msg.tag);
    }

    public static PacketSyncCap decode(FriendlyByteBuf buf) {

        return new PacketSyncCap(buf.readNbt());
    }

    public static void handle(PacketSyncCap msg, Supplier<NetworkEvent.Context> ctx) {

        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(() -> {
                Player player = UniqueCrops.proxy.getPlayer();
                player.getMainHandItem().getCapability(CPProvider.CROP_POWER, null).ifPresent(crop ->
                        crop.deserializeNBT(msg.tag));
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
