package com.bafomdad.uniquecrops.core;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class UCProtectionHandler {

    private static UCProtectionHandler INSTANCE;

    private ConcurrentHashMap<String, Set<ChunkPos>> saveInfo = new ConcurrentHashMap<>();

    private UCProtectionHandler() {}

    public static UCProtectionHandler getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new UCProtectionHandler();
        }
        return INSTANCE;
    }

    public Set<ChunkPos> getChunkInfo(RegistryKey<World> world) {

        String str = world.getLocation().toString();
        if (saveInfo.get(str) == null) {
            Set<ChunkPos> save = new HashSet<>();
            saveInfo.put(str, save);
            saveInfo.get(str).add(new ChunkPos(0, 0));
        }
        return saveInfo.get(str);
    }

    public void addChunk(RegistryKey<World> world, ChunkPos pos, boolean dirty) {

        if (!getChunkInfo(world).contains(pos))
            getChunkInfo(world).add(pos);
        if (dirty)
            UCWorldData.getInstance(world).setDirty(true);
    }

    public void removeChunk(RegistryKey<World> world, ChunkPos pos, boolean dirty) {

        getChunkInfo(world).remove(pos);
        if (dirty)
            UCWorldData.getInstance(world).setDirty(true);
    }

    public synchronized Set<ChunkPos> getSavedChunks(World world, ChunkPos pos) {

        if (world == null) return null;

        Set<ChunkPos> info = getChunkInfo(world.getDimensionKey());
        if (info.contains(pos) && info.size() > 0)
            return info;

        return null;
    }

    public void clearQueue(RegistryKey<World> world) {

        getChunkInfo(world).clear();
    }

    public Set<String> getUnsavedDims() {

        return saveInfo.keySet();
    }
}
