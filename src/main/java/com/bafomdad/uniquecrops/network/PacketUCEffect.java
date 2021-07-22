package com.bafomdad.uniquecrops.network;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.enums.EnumParticle;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class PacketUCEffect {

    EnumParticle type;
    double x, y, z;
    int loopSize;

    public PacketUCEffect() {}

    public PacketUCEffect(EnumParticle type, double x, double y, double z, int loopSize) {

        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.loopSize = loopSize;
    }

    public static void encode(PacketUCEffect packet, PacketBuffer buf) {

        buf.writeShort(packet.type.ordinal());
        buf.writeDouble(packet.x);
        buf.writeDouble(packet.y);
        buf.writeDouble(packet.z);
        buf.writeInt(packet.loopSize);
    }

    public static PacketUCEffect decode(PacketBuffer buf) {

        EnumParticle type = EnumParticle.values()[buf.readShort()];
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        int loopSize = buf.readInt();

        return new PacketUCEffect(type, x, y, z, loopSize);
    }

    public static void handle(PacketUCEffect msg, Supplier<NetworkEvent.Context> ctx) {

        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(() -> {
                PlayerEntity player = UniqueCrops.proxy.getPlayer();
                if (msg.loopSize > 0)
                    for (int i = 0; i < msg.loopSize; i++)
                        player.world.addParticle(msg.type.getType(), (msg.x + 0.5D) + player.world.rand.nextFloat(), msg.y, (msg.z + 0.5D) + player.world.rand.nextFloat(), 0, 0, 0);
                else
                    player.world.addParticle(msg.type.getType(), msg.x + 0.5D, msg.y, msg.z + 0.5D, 0, 0, 0);
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
