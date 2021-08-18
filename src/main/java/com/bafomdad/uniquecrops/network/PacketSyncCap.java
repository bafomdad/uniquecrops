package com.bafomdad.uniquecrops.network;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.capabilities.CPCapability;
import com.bafomdad.uniquecrops.capabilities.CPProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSyncCap {

    final CompoundNBT tag;

    public PacketSyncCap(CompoundNBT tag) {

        this.tag = tag;
    }

    public static void encode(PacketSyncCap msg, PacketBuffer buf) {

        buf.writeNbt(msg.tag);
    }

    public static PacketSyncCap decode(PacketBuffer buf) {

        return new PacketSyncCap(buf.readNbt());
    }

    public static void handle(PacketSyncCap msg, Supplier<NetworkEvent.Context> ctx) {

        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(() -> {
                PlayerEntity player = UniqueCrops.proxy.getPlayer();
                player.getMainHandItem().getCapability(CPProvider.CROP_POWER, null).ifPresent(crop ->
                        crop.deserializeNBT(msg.tag));
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
