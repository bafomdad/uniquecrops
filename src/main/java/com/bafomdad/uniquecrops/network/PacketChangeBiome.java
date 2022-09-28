package com.bafomdad.uniquecrops.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketChangeBiome {

    private final BlockPos pos;
    private final ResourceLocation biomeId;

    public PacketChangeBiome(BlockPos pos, ResourceLocation id) {

        this.pos = pos;
        this.biomeId = id;
    }

    public void encode(FriendlyByteBuf buf) {

        buf.writeInt(pos.getX());
        buf.writeInt(pos.getZ());
        buf.writeResourceLocation(biomeId);
    }

    public static PacketChangeBiome decode(FriendlyByteBuf buf) {

        BlockPos pos = new BlockPos(buf.readInt(), 0, buf.readInt());
        ResourceLocation biomeId = buf.readResourceLocation();

        return new PacketChangeBiome(pos, biomeId);
    }

    public static void handle(PacketChangeBiome msg, Supplier<NetworkEvent.Context> ctx) {

        ctx.get().enqueueWork(() -> {
            ClientLevel world = Minecraft.getInstance().level;
            LevelChunk chunkAt = (LevelChunk)world.getChunk(msg.pos);

            ResourceKey<Biome> key = ResourceKey.create(Registry.BIOME_REGISTRY, msg.biomeId);
            Holder<Biome> biome = world.registryAccess().ownedRegistryOrThrow(Registry.BIOME_REGISTRY).getHolderOrThrow(key);

            int minY = QuartPos.fromBlock(world.getMinBuildHeight());
            int maxY = minY + QuartPos.fromBlock(world.getHeight()) - 1;

            int x = QuartPos.fromBlock(msg.pos.getX());
            int z = QuartPos.fromBlock(msg.pos.getZ());

            for (LevelChunkSection section : chunkAt.getSections()) {
                for (int dy = 0; dy < 16; dy += 4) {
                    int y = Mth.clamp(QuartPos.fromBlock(section.bottomBlockY() + dy), minY, maxY);
                    section.getBiomes().set(x & 3, y & 3, z & 3, biome);
                    SectionPos pos = SectionPos.of(msg.pos.getX() >> 4, (section.bottomBlockY() >> 4) + dy, msg.pos.getZ() >> 4);
                    world.setSectionDirtyWithNeighbors(pos.x(), pos.y(), pos.z());
                }
            }
            world.onChunkLoaded(new ChunkPos(msg.pos));
        });
        ctx.get().setPacketHandled(true);
    }
}
