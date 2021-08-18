package com.bafomdad.uniquecrops.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketChangeBiome {

    private final BlockPos pos;
    private final ResourceLocation biomeId;

    public PacketChangeBiome(BlockPos pos, ResourceLocation id) {

        this.pos = pos;
        this.biomeId = id;
    }

    public void encode(PacketBuffer buf) {

        buf.writeInt(pos.getX());
        buf.writeInt(pos.getZ());
        buf.writeResourceLocation(biomeId);
    }

    public static PacketChangeBiome decode(PacketBuffer buf) {

        BlockPos pos = new BlockPos(buf.readInt(), 0, buf.readInt());
        ResourceLocation biomeId = buf.readResourceLocation();
        return new PacketChangeBiome(pos, biomeId);
    }

    public static void handle(PacketChangeBiome msg, Supplier<NetworkEvent .Context> ctx) {

        ctx.get().enqueueWork(() -> {

            final int WIDTH_BITS = (int)Math.round(Math.log(16.0D) / Math.log(2.0D)) - 2;
            final int HEIGHT_BITS = (int)Math.round(Math.log(256.0D) / Math.log(2.0D)) - 2;
            final int HORIZONTAL_MASK = (1 << WIDTH_BITS) - 1;
            final int VERTICAL_MASK = (1 << HEIGHT_BITS) - 1;

            ClientWorld world = Minecraft.getInstance().level;
            Chunk chunkAt = (Chunk)world.getChunk(msg.pos);
            Biome targetBiome = world.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).get(msg.biomeId);

            for (int dy = 0; dy < 255; dy += 4) {
                int x = (msg.pos.getX() >> 2) & HORIZONTAL_MASK;
                int y = MathHelper.clamp(dy >> 2, 0, VERTICAL_MASK);
                int z = (msg.pos.getZ() >> 2) & HORIZONTAL_MASK;
                chunkAt.getBiomes().biomes[y << WIDTH_BITS + WIDTH_BITS | z << WIDTH_BITS | x] = targetBiome;
            }
            world.onChunkLoaded(msg.pos.getX() >> 4, msg.pos.getZ() >> 4);
            for (int k = 0; k < 16; ++k)
                world.setSectionDirtyWithNeighbors(msg.pos.getX() >> 4, k, msg.pos.getZ() >> 4);
        });
        ctx.get().setPacketHandled(true);
    }
}
