package com.bafomdad.uniquecrops.network;

import com.bafomdad.uniquecrops.UniqueCrops;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

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
    }

    public static void sendToNearbyPlayers(World world, BlockPos pos, Object toSend) {

        if (world instanceof ServerWorld) {
            ServerWorld ws = ((ServerWorld)world);

            ws.getChunkProvider().chunkManager.getTrackingPlayers(new ChunkPos(pos), false)
                    .filter(p -> p.getDistanceSq(pos.getX(), pos.getY(), pos.getZ()) < 64 * 64)
                    .forEach(p -> INSTANCE.send(PacketDistributor.PLAYER.with(() -> p), toSend));
        }
    }

    public static void sendTo(ServerPlayerEntity playerMP, Object toSend) {

        INSTANCE.sendTo(toSend, playerMP.connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
    }
}
