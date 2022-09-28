package com.bafomdad.uniquecrops.network;

import com.bafomdad.uniquecrops.UniqueCrops;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public final class UCPacketHandler {

    private static final String PROTOCOL = "9";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(UniqueCrops.MOD_ID, "chan"), () -> PROTOCOL, PROTOCOL::equals, PROTOCOL::equals);

    public static void init() {
        int id = 0;
        INSTANCE.registerMessage(id++, PacketSyncCap.class, PacketSyncCap::encode, PacketSyncCap::decode, PacketSyncCap::handle);
        INSTANCE.registerMessage(id++, PacketChangeBiome.class, PacketChangeBiome::encode, PacketChangeBiome::decode, PacketChangeBiome::handle);
        INSTANCE.registerMessage(id++, PacketSendKey.class, PacketSendKey::encode, PacketSendKey::decode, PacketSendKey::handle);
        INSTANCE.registerMessage(id++, PacketColorfulCube.class, PacketColorfulCube::encode, PacketColorfulCube::decode, PacketColorfulCube::handle);
        INSTANCE.registerMessage(id++, PacketUCEffect.class, PacketUCEffect::encode, PacketUCEffect::decode, PacketUCEffect::handle);
        INSTANCE.registerMessage(id++, PacketOpenBook.class, PacketOpenBook::encode, PacketOpenBook::decode, PacketOpenBook::handle);
        INSTANCE.registerMessage(id++, PacketOpenCube.class, PacketOpenCube::encode, PacketOpenCube::decode, PacketOpenCube::handle);
    }

    public static void sendToNearbyPlayers(Level world, BlockPos pos, Object toSend) {

        if (world instanceof ServerLevel) {
            ServerLevel ws = ((ServerLevel)world);

            ws.getChunkSource().chunkMap.getPlayers(new ChunkPos(pos), false)
                    .stream()
                    .filter(p -> p.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) < 64 * 64)
                    .forEach(p -> INSTANCE.send(PacketDistributor.PLAYER.with(() -> p), toSend));
        }
    }

    public static void sendTo(ServerPlayer playerMP, Object toSend) {

        INSTANCE.sendTo(toSend, playerMP.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
    }
}
